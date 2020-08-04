package merchant.controllers.send;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import merchant.beans.DownloadTraceRequest;
import merchant.sign.SignatureAndVerification;
import merchant.utils.Base64Util;
import merchant.utils.DateUtil;
import merchant.utils.HttpClientUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

/**
 * 商户直连文件下载Controller
 * @author marui
 * 
 */
@Controller
public class DownloadTraceController {
	
	private Logger logger = LoggerFactory.getLogger(DownloadTraceController.class);
	
	@Autowired
	private SignatureAndVerification signatureAndVerification;
	
	//请求路径，在conf.properties文件中读取
	@Value(value="${Bridge_URL_DownloadTrace}")
	private String reqUrl;
	@Value(value="${Bridge_TransCode_DownloadTrace}")
	private String downloadTrace;
		
	@RequestMapping(value = "/downloadTrace.do", method = RequestMethod.GET)
	public void fileDown(Map<String,Object> map,HttpServletResponse httpResponse,String merchantId,
			String billDate){
		//输入项避免首尾出现空格的情况
		merchantId = merchantId.trim();
		billDate = billDate.trim();
		
		//创建发送请求对象
		DownloadTraceRequest req = new DownloadTraceRequest();
		//以系统当前时间为时间戳
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//封装数据格式为json格式 固定 
		req.setFormat("json");
		// 关联交易的交易序列号需由第三方提供，且必须按照特定规则上送缴费中心，规则定义：BRIDGE前缀+当前17位时间戳timeStamp+商户号merchantId
		req.getMessage().getHead().setTransSeqNum("BRIDGE"+timeStamp+merchantId);
		//交易码，固定
		req.getMessage().getHead().setTransCode(downloadTrace);
		//上下行标志 固定
		req.getMessage().getHead().setTransFlag("01");
		//时间戳格式 yyyyMMddHHmmssSSS，
		req.getMessage().getHead().setTimeStamp(timeStamp);
		
		//对账单文件格式 固定值：CSV（大写）或GZ
		req.getMessage().getInfo().setFileType("CSV");
		//编码格式 固定值：UTF-8（大写）
		req.getMessage().getInfo().setCharset("UTF-8");
		//缴费中心商户编号，以商户为唯一维度来下载对账单
		req.getMessage().getInfo().setMerchantId(merchantId);
		//例如 ：req.getMessage().getInfo().setMerchantId("103881104410001");
		try {
			//指定对账单的下载日期  跟下载的交易流水交易日期对应,格式yyyyMMdd
			req.getMessage().getInfo().setBillDate(DateUtil.add_Day(billDate,0));
		} catch (Exception e) {
			logger.info("获取日期异常");
			e.printStackTrace();
		}
		//对账单类型billType有两个选项：
		//	ALL：暂不提供+
		//	SUCCESS：下载所有支付成功流水及所有退款成功流水，对应缴费状态为1、5、6、4，退款SUCCESS
		req.getMessage().getInfo().setBillType("SUCCESS");
		
		//将封装的请求对象转化为json格式
		String reqJson = JSON.toJSONString(req);
		logger.info("加密前发送的请求报文：" + reqJson);
		//加签名
		String signatrue = signatureAndVerification.signWhithsha1withrsa(reqJson);
		//拼接发送的报文信息
		String reqStr = signatrue+"||"+Base64Util.encodeData(reqJson);
		logger.info("发送的请求报文：" + reqStr);
		//发送请求并返回给前台,无返回值
		HttpClientUtils.doPostFile(reqUrl, reqStr,httpResponse);
		
		String doPostStr = HttpClientUtils.doPostStr(reqUrl, reqStr);
		logger.warn("直接调用方法返回的日志结果："+doPostStr);
		
	}
}
