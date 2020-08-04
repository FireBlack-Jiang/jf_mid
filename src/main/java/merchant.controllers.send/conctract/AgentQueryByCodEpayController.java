package merchant.controllers.send.conctract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import merchant.beans.contract.AgentQueryByCodEpayRequest;
import merchant.beans.contract.AgentQueryByCodEpayResponse;
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
 * 授权缴费签约/解约结果查询
 * 
 * @author sdf
 * 
 */
@Controller
@RequestMapping("/contract")
public class AgentQueryByCodEpayController {
	private Logger logger = LoggerFactory
			.getLogger(AgentQueryByCodEpayController.class);

	@Autowired
	private SignatureAndVerification signatureAndVerification;
	// 请求路径，在conf.properties文件中读取
	@Value(value = "${Bridge_URL_Contract}")
	private String reqUrl;

	@Value(value = "${Bridge_TransCode_AgentQueryByCodEpay}")
	private String agentQueryByCodEpay;

	/**
	 * 授权缴费签约/解约结果查询
	 * @param map
	 * @param merchantId
	 * @param epayCode
	 * @param agentSignStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/agentQueryByCodEpay.do", method = RequestMethod.POST)
	public String agentSignQuery(Map<String, Object> map, String merchantId,
			String epayCode, String agentSignStatus, String pageNo,
			String pageSize) {

		epayCode = epayCode.trim();
		// 创建发送请求对象
		AgentQueryByCodEpayRequest agentSignGetByCodEpayRequest = new AgentQueryByCodEpayRequest();
		// 以当前时间为时间戳
		String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());

		// 发送报文的格式，以json格式传送
		agentSignGetByCodEpayRequest.setFormat("json");
		// 交易码，固定
		agentSignGetByCodEpayRequest.getMessage().getHead()
				.setTransCode(agentQueryByCodEpay);
		// 上行下送标志，固定
		agentSignGetByCodEpayRequest.getMessage().getHead().setTransFlag("01");
		// 缴费中心交易序列号
		agentSignGetByCodEpayRequest.getMessage().getHead()
				.setTransSeqNum("BRIDGE" + stamp + epayCode);
		// 时间戳格式 yyyyMMddHHmmssSSS，
		agentSignGetByCodEpayRequest.getMessage().getHead().setTimestamp(stamp);

		// 商户编号
		agentSignGetByCodEpayRequest.getMessage().getInfo()
				.setMerchantId(merchantId);
		// 缴费项目编号
		agentSignGetByCodEpayRequest.getMessage().getInfo()
				.setEpayCode(epayCode);
		// 查询状态
		agentSignGetByCodEpayRequest.getMessage().getInfo()
				.setAgentSignStatus(agentSignStatus);
		// 页码
		agentSignGetByCodEpayRequest.getMessage().getInfo().setPageNo(pageNo);
		// 页大小
		agentSignGetByCodEpayRequest.getMessage().getInfo()
				.setPageSize(pageSize);

		// 封装完成，将数据转为json格式，然后加密加签名
		String reqJson = JSON.toJSONString(agentSignGetByCodEpayRequest);
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
				AgentQueryByCodEpayResponse agentSignGetByCodEpayResponse = JSON
						.parseObject(
								respJson,
								new TypeReference<AgentQueryByCodEpayResponse>() {
								});

				AgentQueryByCodEpayResponse.Message.Head head = agentSignGetByCodEpayResponse
						.getMessage().getHead();
				AgentQueryByCodEpayResponse.Message.Info info = agentSignGetByCodEpayResponse
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
				map.put("epayCode",
						info.getEpayCode() == null ? "" : info.getEpayCode());
				map.put("merchantId",
						info.getMerchantId() == null ? "" : info
								.getMerchantId());
				map.put("hasNextPage", info.getHasNextPage() == null ? ""
						: info.getHasNextPage());
				map.put("contractList", info.getContractList() == null ? ""
						: info.getContractList());
				map.put("merchantId_request", merchantId);
				map.put("epayCode_request", epayCode);
				map.put("agentSignStatus_request", agentSignStatus);
				int prev_no =  Integer.parseInt(pageNo) - 1;
				if (prev_no >= 1 ) {
					map.put("pageNo_prev", prev_no + "");
				} else {
					map.put("pageNo_prev", "");
				}
				map.put("pageNo_next", (Integer.parseInt(pageNo) + 1) + "");
				map.put("pageSize_request", pageSize);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("returnMessage", "返回签名解析失败！");
				return "agentError.jsp";
			}

		}
		return "agentQueryByCodEpayResult.jsp";
	}

}
