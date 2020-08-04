package merchant.controllers.send.conctract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.contract.AgentPayRequest;
import merchant.beans.contract.AgentPayResponse;
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
 * 授权缴费单笔扣款
 * 
 * @author sdf
 * 
 */
@Controller
@RequestMapping("/contract")
public class AgentPayController {
	private Logger logger = LoggerFactory.getLogger(AgentPayController.class);

	@Autowired
	private SignatureAndVerification signatureAndVerification;
	// 请求路径，在conf.properties文件中读取
	@Value(value = "${Bridge_URL_AgentPay}")
	private String reqPayUrl;

	@Value(value = "${Bridge_TransCode_AgentPay}")
	private String agentPay;

	/**
	 * 授权缴费单笔扣款
	 * @param map
	 * @param branchCode
	 * @param epayCode
	 * @param merchantId
	 * @param input1
	 * @param input2
	 * @param input3
	 * @param input4
	 * @param input5
	 * @param billNo
	 * @param agentSignNo
	 * @param amount
	 * @param receiveAccount
	 * @param splitAccTemplate
	 * @param trVersion
	 * @return
	 */
	@RequestMapping(value = "/agentPay.do", method = RequestMethod.POST)
	public String agentPay(Map<String, Object> map, String branchCode,
			String epayCode, String merchantId, String input1, String input2,
			String input3, String input4, String input5, String billNo,
			String agentSignNo, String amount, String receiveAccount,
			String splitAccTemplate, String trVersion) {

		merchantId = merchantId.trim();
		// 创建发送请求对象
		AgentPayRequest agentPayRequest = new AgentPayRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentPayRequest.setFormat("json");
		// 交易码，固定
		agentPayRequest.getMessage().getHead().setTransCode(agentPay);
		// 上行下送标志，固定
		agentPayRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentPayRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + merchantId);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentPayRequest.getMessage().getHead().setTimestamp(stamp);

		// 缴费项目编号
		agentPayRequest.getMessage().getInfo().setEpayCode(epayCode);
		// 缴费项目配置的主商户在商E付系统的编号
		agentPayRequest.getMessage().getInfo().setMerchantId(merchantId);
		// 输入要素1
		agentPayRequest.getMessage().getInfo().setInput1(input1);
		// 输入要素2
		agentPayRequest.getMessage().getInfo().setInput2(input2);
		// 输入要素3
		agentPayRequest.getMessage().getInfo().setInput3(input3);
		// 输入要素4
		agentPayRequest.getMessage().getInfo().setInput4(input4);
		// 输入要素5
		agentPayRequest.getMessage().getInfo().setInput5(input5);
		// 账单编号
		agentPayRequest.getMessage().getInfo().setBillNo(billNo);
		// 签约编号
		agentPayRequest.getMessage().getInfo().setAgentSignNo(agentSignNo);
		// 缴费金额
		agentPayRequest.getMessage().getInfo().setAmount(amount);

		// 收款方账号
		agentPayRequest.getMessage().getInfo()
				.setReceiveAccount(receiveAccount);

		// 分账交易模板号
		agentPayRequest.getMessage().getInfo()
				.setSplitAccTemplate(splitAccTemplate);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentPayRequest);
		// 对需要发送的json字符串进行签名
		String signatrue = signatureAndVerification
				.signWhithsha1withrsa(reqJson);
		logger.info("加密前发送的报文：" + reqJson);
		// 加签名
		String reqStr = signatrue + "||" + Base64Util.encodeData(reqJson);
		logger.info("发送的报文：" + reqStr);

		// 发送签名信息获取返回签名信息
		String responseStr = HttpClientUtils.doPostStr(reqPayUrl, reqStr);
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
				AgentPayResponse agentPayResponse = JSON.parseObject(respJson,
						new TypeReference<AgentPayResponse>() {
						});

				AgentPayResponse.Message.Head head = agentPayResponse
						.getMessage().getHead();
				AgentPayResponse.Message.Info info = agentPayResponse
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
				map.put("amount",
						info.getAmount() == null ? "" : info.getAmount());
				map.put("status",
						info.getStatus() == null ? "" : info.getStatus());
				map.put("billNo",
						info.getBillNo() == null ? "" : info.getBillNo());
				map.put("traceNo",
						info.getTraceNo() == null ? "" : info.getTraceNo());
				map.put("merchantId",
						info.getMerchantId() == null ? "" : info
								.getMerchantId());

			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}

		}

		return "agentPayResult.jsp";

	}

}
