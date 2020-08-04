package merchant.controllers.send.conctract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.contract.AgentUnSignRequest;
import merchant.beans.contract.AgentUnSignResponse;
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
 * 授权缴费解约
 * 
 * @author sdf
 * 
 */
@Controller
@RequestMapping("/contract")
public class AgentUnSignController {
	private Logger logger = LoggerFactory
			.getLogger(AgentUnSignController.class);

	@Autowired
	private SignatureAndVerification signatureAndVerification;
	// 请求路径，在conf.properties文件中读取
	@Value(value = "${Bridge_URL_Contract}")
	private String reqUrl;

	@Value(value = "${Bridge_TransCode_AgentUnSign}")
	private String agentUnSign;

	/**
	 * 授权缴费解约
	 * 
	 * @param map
	 * @param branchCode
	 * @param orderNo
	 * @param agentSignNo
	 * @param epayCode
	 * @param merchantId
	 * @param input1
	 * @param input2
	 * @param input3
	 * @param input4
	 * @param input5
	 * @param trVersion
	 * @return
	 */
	@RequestMapping(value = "/agentUnSign.do", method = RequestMethod.POST)
	public String agentUnSign(Map<String, Object> map, String branchCode,
			String orderNo, String agentSignNo, String epayCode,
			String merchantId, String input1, String input2, String input3,
			String input4, String input5, String trVersion) {

		epayCode = epayCode.trim();
		// 创建发送请求对象
		AgentUnSignRequest agentUnSignRequest = new AgentUnSignRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentUnSignRequest.setFormat("json");
		// 交易码，固定
		agentUnSignRequest.getMessage().getHead().setTransCode(agentUnSign);
		// 上行下送标志，固定
		agentUnSignRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentUnSignRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + epayCode);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentUnSignRequest.getMessage().getHead().setTimestamp(stamp);

		// 商户交易编号
		agentUnSignRequest.getMessage().getInfo().setOrderNo(orderNo);
		// 签约编号
		agentUnSignRequest.getMessage().getInfo().setAgentSignNo(agentSignNo);
		// 缴费项目编号
		agentUnSignRequest.getMessage().getInfo().setEpayCode(epayCode);
		// 缴费项目配置的主商户在商E付系统的编号
		agentUnSignRequest.getMessage().getInfo().setMerchantId(merchantId);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentUnSignRequest);
		// 对需要发送的json字符串进行签名
		String signatrue = signatureAndVerification
				.signWhithsha1withrsa(reqJson);
		logger.info("加密前发送的报文：" + reqJson);
		// 加签名
		String reqStr = signatrue + "||" + Base64Util.encodeData(reqJson);
		logger.info("发送的报文：" + reqStr);

		// 发送签名信息获取返回签名信息
		String responseStr = HttpClientUtils.doPostStr(reqUrl, reqStr);
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
				AgentUnSignResponse agentUnSignResponse = JSON.parseObject(
						respJson, new TypeReference<AgentUnSignResponse>() {
						});

				AgentUnSignResponse.Message.Head head = agentUnSignResponse
						.getMessage().getHead();
				AgentUnSignResponse.Message.Info info = agentUnSignResponse
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
				map.put("orderNo",
						info.getOrderNo() == null ? "" : info.getOrderNo());
			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}
		}
		return "agentUnSignResult.jsp";
	}
}
