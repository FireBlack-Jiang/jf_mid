package merchant.controllers.send;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.RefundTraceRequest;
import merchant.beans.RefundTraceResponse;
import merchant.beans.RefundTraceResponse.Message.Head;
import merchant.beans.RefundTraceResponse.Message.Info;
import merchant.sign.SignatureAndVerification;
import merchant.utils.Base64Util;
import merchant.utils.HttpClientUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 商户直连退款Controller
 * @author marui
 */
@Controller
public class RefundTraceController {
	
	private Logger logger = LoggerFactory.getLogger(RefundTraceController.class);
	
	@Autowired
	private SignatureAndVerification signatureAndVerification;
	
	//请求路径，在conf.properties文件中读取
	@Value(value="${Bridge_URL_RefundTrace}")
	private String reqUrl;
	@Value(value="${Bridge_TransCode_RefundTrace}")
	private String refundTrace;
	
	
	@RequestMapping(value = "/refundTrace.do", method = RequestMethod.POST)
	public String refundTrace(Map<String,Object> map,String traceNo,String merchantId,String amtRefund){
		//输入项避免首尾出现空格的情况
		traceNo = traceNo.trim();
		merchantId = merchantId.trim();
		amtRefund = amtRefund.trim();
		//创建请求对象，用来封装请求报文
		RefundTraceRequest refundTraceRequest = new RefundTraceRequest();
		//制定请求报文格式为json格式
		refundTraceRequest.setFormat("json");
		//以当前系统时间为时间戳 格式yyyyMMddHHmmssSSS
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//退款交易的序列号需由第三方提供，且必须按照约定规则上送缴费中心，规则定义如下：
		//BRIDGE前缀+当前17位时间戳timeStamp + 缴费中心流水号traceNo作为后18位
		refundTraceRequest.getMessage().getHead().setTransSeqNum("BRIDGE"+stamp+traceNo);
		//交易码 refundBill  固定值
		refundTraceRequest.getMessage().getHead().setTransCode(refundTrace);
		//交易上行下送标志 固定值
		refundTraceRequest.getMessage().getHead().setTransFlag("01");
		//时间戳 格式 yyyyMMddHHmmssSSS，
		refundTraceRequest.getMessage().getHead().setTimeStamp(stamp);
		//TODO 分行4位iGoal码，用来前置分行交易,36家分行每家分行分配一个唯一的4位iGoal码，例如北京分行为2110
		refundTraceRequest.getMessage().getHead().setBranchCode("2110");
		
		//缴费中心流水号   需要进行退款的交易流水，例如：JF170512171412060778
		refundTraceRequest.getMessage().getInfo().setTraceNo(traceNo);
		//商户编号  要与证书一致  缴费中心商户编号，以商户为唯一维度来下载对账单
		//String merchantId="103881104410001";
		refundTraceRequest.getMessage().getInfo().setMerchantId(merchantId);
		//退款金额，精确到小数点后两位，目前只支持等于支付金额的退款 
		refundTraceRequest.getMessage().getInfo().setAmtRefund(amtRefund);
		/*
		 * 例如：
		 * refundTraceRequest.getMessage().getInfo().setTraceNo("JF170512171412060778");
		 * refundTraceRequest.getMessage().getInfo().setMerchantId("103881104410001");
		 * refundTraceRequest.getMessage().getInfo().setAmtRefund("2");
		 * */
		String reqJson = JSON.toJSONString(refundTraceRequest);
		logger.info("加密前报文："+reqJson);
		//加签名
		String signatrue = signatureAndVerification.signWhithsha1withrsa(reqJson);
		//拼接报文
		String reqStr = signatrue+"||"+Base64Util.encodeData(reqJson);
		
		logger.info("发送的请求报文："+reqStr);
		//发送签名信息获取返回签名信息
		String responseStr = HttpClientUtils.doPostStr(reqUrl, reqStr);
		logger.info("发送请求的地址："+reqUrl);
		logger.info("返回的响应报文："+responseStr);

		//TODO 如下可以对返回的报文进行处理，封装到map回显到页面，也可以做其他处理
		if (responseStr.startsWith("{\"string\":"))
		{
			map.put("returnMessage", responseStr.substring(responseStr.indexOf(":")+2,responseStr.indexOf("}")-1));
		}else{
			//截取签名信息
			String headSub = responseStr.substring(0, responseStr.indexOf("||"));
			logger.info("获取签名的前半部分："+headSub);
			//截取加密的json信息，进行解密
			String tailSub = responseStr.substring(responseStr.indexOf("||")+2);
			logger.info("获取签名的后半部分："+tailSub);
			
			//对获取的信息进行验签(该方法对signWhithsha1withrsa加密和Base64Util解密的字符串可以直接进行验签)
			try {
				signatureAndVerification.read_cer_and_verify_sign(tailSub,headSub);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
			}
			//解密返回报文
			String respJson = Base64Util.decodeData(tailSub);
			logger.info("获取签名解密后："+respJson);
			//截取签名信息中的json字符串，并转化为对象
			RefundTraceResponse refundTraceResponse = JSON.parseObject(respJson, new TypeReference<RefundTraceResponse>(){});
		
			Head head = refundTraceResponse.getMessage().getHead();
			Info info = refundTraceResponse.getMessage().getInfo();
			//封装返回的消息，写进前端
			map.put("transCode", head.getTransCode() == null?"":head.getTransCode());
			map.put("transFlag", head.getTransFlag() == null?"":head.getTransFlag());
			map.put("transSeqNum", head.getTransSeqNum() == null?"":head.getTransSeqNum());
			map.put("timeStamp", head.getTimeStamp() == null?"":head.getTimeStamp());
			map.put("returnCode", head.getReturnCode() == null?"":head.getReturnCode());
			map.put("returnMessage", head.getReturnMessage() == null?"":head.getReturnMessage());
			
			map.put("refundNo", info.getRefundNo() == null?"":info.getRefundNo());
			map.put("merchantId", info.getMerchantId() == null?"":info.getMerchantId());
			map.put("traceNo", info.getTraceNo() == null?"":info.getTraceNo());
			map.put("refundSettleDate", info.getRefundSettleDate() == null?"":info.getRefundSettleDate());
			map.put("amtRefund", info.getAmtRefund() == null?"":info.getAmtRefund());
			
		}
		return "/refundTraceResult.jsp";
	}
	
}
