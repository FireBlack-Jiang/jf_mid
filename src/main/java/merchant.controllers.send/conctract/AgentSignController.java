package merchant.controllers.send.conctract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.contract.AgentSignReqRequest;
import merchant.beans.contract.AgentSignReqResponse;
import merchant.beans.contract.AgentSignResendRequest;
import merchant.beans.contract.AgentSignResendResponse;
import merchant.beans.contract.AgentSignSubmitRequest;
import merchant.beans.contract.AgentSignSubmitResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 授权支付签约申请
 * 
 * @author sdf
 * 
 */
@Controller
@RequestMapping("/contract")
public class AgentSignController {
	private Logger logger = LoggerFactory.getLogger(AgentSignController.class);

	@Autowired
	private SignatureAndVerification signatureAndVerification;
	// 请求路径，在conf.properties文件中读取
	@Value(value = "${Bridge_URL_Contract}")
	private String reqUrl;


	@Value(value = "${Bridge_TransCode_AgentSignReq}")
	private String agentSignReq;
	@Value(value = "${Bridge_TransCode_AgentSignSubmit}")
	private String agentSignSubmit;
	@Value(value = "${Bridge_TransCode_AgentSignResend}")
	private String agentSignResend;

	/**
	 * 授权支付签约申请
	 * @param map
	 * @param branchCode
	 * @param epayCode
	 * @param merchantId
	 * @param input1
	 * @param input2
	 * @param input3
	 * @param input4
	 * @param input5
	 * @param orderNo
	 * @param userId
	 * @param userName
	 * @param certificateNo
	 * @param certificateType
	 * @param cardNo
	 * @param cardType
	 * @param mobileNo
	 * @param invaidDate
	 * @param cardDueDate
	 * @param cVV2
	 * @param trVersion
	 * @return
	 */
	@RequestMapping(value = "/agentSignReq.do", method = RequestMethod.POST)
	public String agentSignReq(Map<String, Object> map, String branchCode,
			String epayCode, String merchantId, String input1, String input2,
			String input3, String input4, String input5, String orderNo,
			String userId, String userName, String certificateNo,
			String certificateType, String cardNo, String cardType,
			String mobileNo, String invaidDate, String cardDueDate,
			String cVV2, String trVersion) {

		merchantId = merchantId.trim();
		// 创建发送请求对象
		AgentSignReqRequest agentSignReqRequest = new AgentSignReqRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentSignReqRequest.setFormat("json");
		// 交易码，固定
		agentSignReqRequest.getMessage().getHead().setTransCode(agentSignReq);
		// 上行下送标志，固定
		agentSignReqRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentSignReqRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + epayCode);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentSignReqRequest.getMessage().getHead().setTimestamp(stamp);

		// 缴费项目编号
		agentSignReqRequest.getMessage().getInfo().setEpayCode(epayCode);
		// 缴费项目配置的主商户在商E付系统的编号
		agentSignReqRequest.getMessage().getInfo().setMerchantId(merchantId);
		// 输入要素1
		agentSignReqRequest.getMessage().getInfo().setInput1(input1);
		// 输入要素2
		agentSignReqRequest.getMessage().getInfo().setInput2(input2);
		// 输入要素3
		agentSignReqRequest.getMessage().getInfo().setInput3(input3);
		// 输入要素4
		agentSignReqRequest.getMessage().getInfo().setInput4(input4);
		// 输入要素5
		agentSignReqRequest.getMessage().getInfo().setInput5(input5);
		// 商户交易编号
		agentSignReqRequest.getMessage().getInfo().setOrderNo(orderNo);
		// 客户姓名
		agentSignReqRequest.getMessage().getInfo().setUserName(userName);
		// 证件号
		agentSignReqRequest.getMessage().getInfo()
				.setCertificateNo(certificateNo);
		// 证件类型
		agentSignReqRequest.getMessage().getInfo()
				.setCertificateType(certificateType);
		// 签约卡号
		agentSignReqRequest.getMessage().getInfo().setCardNo(cardNo);
		// 签约卡类型
		agentSignReqRequest.getMessage().getInfo().setCardType(cardType);
		// 手机号码
		agentSignReqRequest.getMessage().getInfo().setMobileNo(mobileNo);
		// 签约有效期
		agentSignReqRequest.getMessage().getInfo().setInvaidDate(invaidDate);
		// 卡片有效期
		agentSignReqRequest.getMessage().getInfo().setCardDueDate(cardDueDate);
		// 卡片CVV2码
		agentSignReqRequest.getMessage().getInfo().setcVV2(cVV2);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentSignReqRequest);
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
				AgentSignReqResponse agentSignReqResponse = JSON.parseObject(
						respJson, new TypeReference<AgentSignReqResponse>() {
						});

				AgentSignReqResponse.Message.Head head = agentSignReqResponse
						.getMessage().getHead();
				AgentSignReqResponse.Message.Info info = agentSignReqResponse
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

				logger.info("returnMessage:" + head.getReturnMessage());

				// 返回的消息体信息
				map.put("orderNo",
						info.getOrderNo() == null ? "" : info.getOrderNo());
				map.put("merchantId",
						info.getMerchantId() == null ? "" : info
								.getMerchantId());

				map.put("trVersion_request",
						trVersion == null ? "" : trVersion.trim());
				map.put("orderNo_request",
						orderNo == null ? "" : orderNo.trim());
				map.put("epayCode_request",
						epayCode == null ? "" : epayCode.trim());
				map.put("cardNo_request", cardNo == null ? "" : cardNo.trim());

				map.put("merchantId_request", merchantId == null ? ""
						: merchantId.trim());
				map.put("input1_request", input1 == null ? "" : input1.trim());
				map.put("input2_request", input2 == null ? "" : input2.trim());
				map.put("input3_request", input3 == null ? "" : input3.trim());
				map.put("input4_request", input4 == null ? "" : input4.trim());
				map.put("input5_request", input5 == null ? "" : input5.trim());
			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}
		}
		
		if (null == map.get("returnCode") || ! "0000".equals(map.get("returnCode")) ) {
			return "agentError.jsp";
		} else {
			return "agentSignSubmit.jsp";
		}
	}

	/**
	 * 授权支付签约确认
	 * @param map
	 * @param branchCode
	 * @param orderNo
	 * @param verifyCode
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
	@RequestMapping(value = "/agentSignSubmit.do", method = RequestMethod.POST)
	public String agentSignSubmit(Map<String, Object> map, String branchCode,
			String orderNo, String verifyCode, String epayCode,
			String merchantId, String input1, String input2, String input3,
			String input4, String input5, String trVersion) {

		merchantId = merchantId.trim();
		// 创建发送请求对象
		AgentSignSubmitRequest agentSignSubmitRequest = new AgentSignSubmitRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentSignSubmitRequest.setFormat("json");
		// 交易码，固定
		agentSignSubmitRequest.getMessage().getHead()
				.setTransCode(agentSignSubmit);
		// 上行下送标志，固定
		agentSignSubmitRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentSignSubmitRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + epayCode);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentSignSubmitRequest.getMessage().getHead().setTimestamp(stamp);

		// 商户交易编号
		agentSignSubmitRequest.getMessage().getInfo().setOrderNo(orderNo);
		// 验证码
		agentSignSubmitRequest.getMessage().getInfo().setVerifyCode(verifyCode);
		// 缴费项目编号
		agentSignSubmitRequest.getMessage().getInfo().setEpayCode(epayCode);
		// 缴费项目配置的主商户在商E付系统的编号
		agentSignSubmitRequest.getMessage().getInfo().setMerchantId(merchantId);
		// 输入要素1
		agentSignSubmitRequest.getMessage().getInfo().setInput1(input1);
		// 输入要素2
		agentSignSubmitRequest.getMessage().getInfo().setInput2(input2);
		// 输入要素3
		agentSignSubmitRequest.getMessage().getInfo().setInput3(input3);
		// 输入要素4
		agentSignSubmitRequest.getMessage().getInfo().setInput4(input4);
		// 输入要素5
		agentSignSubmitRequest.getMessage().getInfo().setInput5(input5);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentSignSubmitRequest);
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
				AgentSignSubmitResponse agentSignSubmitResponse = JSON
						.parseObject(respJson,
								new TypeReference<AgentSignSubmitResponse>() {
								});

				AgentSignSubmitResponse.Message.Head head = agentSignSubmitResponse
						.getMessage().getHead();
				AgentSignSubmitResponse.Message.Info info = agentSignSubmitResponse
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
				map.put("agentSignNo",
						info.getAgentSignNo() == null ? "" : info.getAgentSignNo());
				map.put("merchantId",
						info.getMerchantId() == null ? "" : info
								.getMerchantId());

			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}

		}
		return "agentSignSubmitResult.jsp";
	}

	/**
	 * 授权缴费签约重发验证码
	 * @param map
	 * @param branchCode
	 * @param orderNo
	 * @param cardNo
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
	@RequestMapping(value = "/agentSignResend.do", method = RequestMethod.POST)
	@ResponseBody
	public String agentSignResend(Map<String, Object> map, String branchCode,
			String orderNo, String cardNo, String epayCode, String merchantId,
			String input1, String input2, String input3, String input4,
			String input5, String trVersion) {

		merchantId = merchantId.trim();
		// 创建发送请求对象
		AgentSignResendRequest agentSignResendRequest = new AgentSignResendRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentSignResendRequest.setFormat("json");
		// 交易码，固定
		agentSignResendRequest.getMessage().getHead()
				.setTransCode(agentSignResend);
		// 上行下送标志，固定
		agentSignResendRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentSignResendRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + epayCode);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentSignResendRequest.getMessage().getHead().setTimestamp(stamp);

		// 商户交易编号
		agentSignResendRequest.getMessage().getInfo().setOrderNo(orderNo);
		// 签约卡号
		agentSignResendRequest.getMessage().getInfo().setCardNo(cardNo);
		// 缴费项目编号
		agentSignResendRequest.getMessage().getInfo().setEpayCode(epayCode);
		// 缴费项目配置的主商户在商E付系统的编号
		agentSignResendRequest.getMessage().getInfo().setMerchantId(merchantId);
		// 输入要素1
		agentSignResendRequest.getMessage().getInfo().setInput1(input1);
		// 输入要素2
		agentSignResendRequest.getMessage().getInfo().setInput2(input2);
		// 输入要素3
		agentSignResendRequest.getMessage().getInfo().setInput3(input3);
		// 输入要素4
		agentSignResendRequest.getMessage().getInfo().setInput4(input4);
		// 输入要素5
		agentSignResendRequest.getMessage().getInfo().setInput5(input5);
		// 商户交易编号
		agentSignResendRequest.getMessage().getInfo().setOrderNo(orderNo);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentSignResendRequest);
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
				AgentSignResendResponse agentSignResendResponse = JSON
						.parseObject(respJson,
								new TypeReference<AgentSignResendResponse>() {
								});

				AgentSignResendResponse.Message.Head head = agentSignResendResponse
						.getMessage().getHead();
				AgentSignResendResponse.Message.Info info = agentSignResendResponse
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
				map.put("merchantId",
						info.getMerchantId() == null ? "" : info
								.getMerchantId());

			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
			}

		}
		return JSON.toJSONString(map);
	}


}
