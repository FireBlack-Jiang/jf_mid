package merchant.controllers.send.conctract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.contract.AgentPayQueryRequest;
import merchant.beans.contract.AgentPayQueryResponse;
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
 * 授权缴费扣款状态查询
 * 
 * @author sdf
 * 
 */
@Controller
@RequestMapping("/contract")
public class AgentPayQueryController {
	private Logger logger = LoggerFactory.getLogger(AgentPayQueryController.class);

	@Autowired
	private SignatureAndVerification signatureAndVerification;
	// 请求路径，在conf.properties文件中读取
	@Value(value = "${Bridge_URL_AgentPayQuery}")
	private String reqPayQueryUrl;

	@Value(value = "${Bridge_TransCode_AgentPayQuery}")
	private String agentPayQuery;


	/**
	 * 授权缴费扣款状态查询
	 * @param map
	 * @param branchCode
	 * @param billNo
	 * @param merchantId
	 * @param traceNo
	 * @param trVersion
	 * @return
	 */
	@RequestMapping(value = "/agentPayQuery.do", method = RequestMethod.POST)
	public String agentPayQuery(Map<String, Object> map, String branchCode,
			String billNo, String merchantId, String traceNo, String trVersion) {

		merchantId = merchantId.trim();
		// 创建发送请求对象
		AgentPayQueryRequest agentPayQueryRequest = new AgentPayQueryRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentPayQueryRequest.setFormat("json");
		// 交易码，固定
		agentPayQueryRequest.getMessage().getHead()
				.setTransCode(agentPayQuery);
		// 上行下送标志，固定
		agentPayQueryRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentPayQueryRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + merchantId);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentPayQueryRequest.getMessage().getHead().setTimestamp(stamp);

		// 账单编号
		agentPayQueryRequest.getMessage().getInfo().setBillNo(billNo);
		// 商户编号
		agentPayQueryRequest.getMessage().getInfo().setMerchantId(merchantId);	
		// 扣款流水号
		agentPayQueryRequest.getMessage().getInfo().setTraceNo(traceNo);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentPayQueryRequest);
		// 对需要发送的json字符串进行签名
		String signatrue = signatureAndVerification
				.signWhithsha1withrsa(reqJson);
		logger.info("加密前发送的报文：" + reqJson);
		// 加签名
		String reqStr = signatrue + "||" + Base64Util.encodeData(reqJson);
		logger.info("发送的报文：" + reqStr);

		// 发送签名信息获取返回签名信息
		String responseStr = HttpClientUtils.doPostStr(reqPayQueryUrl, reqStr);
		logger.info("接收到的报文：" + responseStr);

		// TODO 如下可以对返回的报文进行处理，封装到map回显到页面，也可以做其他处理
		if (responseStr.startsWith("{\"string\":")) {
			// 如果系统发生异常则会返回"{"string":"异常信息"}"
			map.put("returnMessage", responseStr.substring(
					responseStr.indexOf(":") + 2, responseStr.indexOf("}") - 1));
		} else {
			try {
				// 截取签名信息
				String headSub = responseStr.substring(0,
						responseStr.indexOf("||"));
				logger.info("获取签名的前半部分：" + headSub);
				// 截取加密的json信息，进行解密
				String tailSub = responseStr.substring(responseStr
						.indexOf("||") + 2);
				logger.info("获取签名的后半部分：" + tailSub);
				// 对获取的信息进行验签(该方法对signWhithsha1withrsa加密和Base64Util解密的字符串可以直接进行验签)

				signatureAndVerification.read_cer_and_verify_sign(tailSub,
						headSub);

				// 解密返回报文
				String respJson = Base64Util.decodeData(tailSub);
				logger.info("获取签名解密后：" + respJson);
				// 截取签名信息中的json字符串，并转化为对象
				AgentPayQueryResponse agentPayQueryResponse = JSON.parseObject(
						respJson, new TypeReference<AgentPayQueryResponse>() {
						});

				AgentPayQueryResponse.Message.Head head = agentPayQueryResponse
						.getMessage().getHead();
				AgentPayQueryResponse.Message.Info info = agentPayQueryResponse
						.getMessage().getInfo();
				// 返回的消息头信息
				map.put("transCode",
						head.getTransCode() == null ? "" : head.getTransCode());
				map.put("transFlag",
						head.getTransFlag() == null ? "" : head.getTransFlag());
				map.put("transSeqNum", head.getTransSeqNum() == null ? ""
						: head.getTransSeqNum());
				map.put("timeStamp",
						head.getTimeStamp() == null ? "" : head.getTimeStamp());
				map.put("returnCode",
						head.getReturnCode() == null ? "" : head
								.getReturnCode());
				map.put("returnMessage", head.getReturnMessage() == null ? ""
						: head.getReturnMessage());

				// 返回的消息体信息
				map.put("status",
						info.getStatus() == null ? "" : info.getStatus());
				map.put("statusDes",
						info.getStatusDes() == null ? "" : info.getStatusDes());
				map.put("epayCode",
						info.getEpayCode() == null ? "" : info.getEpayCode());
				map.put("billNo",
						info.getBillNo() == null ? "" : info.getBillNo());

			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}

		}
		return "agentPayQueryResult.jsp";
	}
}
