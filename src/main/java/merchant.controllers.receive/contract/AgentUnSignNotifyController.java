package merchant.controllers.receive.contract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import merchant.beans.contract.AgentSignNotifyRequest;
import merchant.beans.contract.AgentSignNotifyResponse;
import merchant.sign.SignatureAndVerification;
import merchant.utils.DateUtil;
import merchant.utils.HttpClientUtils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 接收账单缴费（销账成功）以及 接收账单缴费（销账失败）
 * @author yzz
 *
 */

@Controller
@RequestMapping("/contract")
public class AgentUnSignNotifyController {
	
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SignatureAndVerification signatureAndVerification;
	/**
	 * 接收账单缴费（销账）接口
	 * 
	 * @param format
	 * @return
	 */
	@RequestMapping(value = "/agentUnSignNotify.do", method = RequestMethod.POST)
	@ResponseBody
	public void agentSignNotify(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			logger.info("--------进入AgentUnSignNotify----------------------------------");
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request).trim();
			if (logger.isWarnEnabled()) {
				logger.info("-----AgentUnSignNotifyController------------收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("-----AgentUnSignNotifyController------------截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("-----AgentSignNotifyController------------截取报文的requsetBody:", requsetBody);
			//如果有双引号，则截取双引号内requsetBody的内容
			Pattern p=Pattern.compile("\"");
			Matcher m=p.matcher(requsetBody);
			while(m.find()){
				requsetBody=requsetBody.replace(m.group(), "");
				logger.info("-----AgentUnSignNotifyController------如果有双引号，则截取后的requsetBody:", requsetBody);
			}
			//requsetBody是base64加密后的数据，需解析出来
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----AgentUnSignNotifyController------------解析完成后的requsetBody-------" + requset);
			AgentSignNotifyRequest agentSignNotifyRequest = JSON.parseObject(requset,
					new TypeReference<AgentSignNotifyRequest>() {
					});
			// 验签(验签是解析前的requsetBody)
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			AgentSignNotifyResponse agentSignNotifyResponse = new AgentSignNotifyResponse(
					);
			AgentSignNotifyResponse.Message respMessage = agentSignNotifyResponse
					.getMessage();
			AgentSignNotifyResponse.Message.Head respHead = agentSignNotifyResponse
					.getMessage().getHead();
			AgentSignNotifyResponse.Message.Info respInfo = agentSignNotifyResponse
					.getMessage().getInfo();
			respHead.setTransFlag("01");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));

			respHead.setTransCode(agentSignNotifyRequest.getMessage().getHead()
					.getTransCode());
			respHead.setTransSeqNum(agentSignNotifyRequest.getMessage().getHead()
					.getTransSeqNum());
			respHead.setReturnCode("success");
			respHead.setReturnMessage("接收解约通知成功");
			String epayCode = agentSignNotifyRequest.getMessage().getInfo()
					.getEpayCode();
			respInfo.setEpayCode(epayCode);
			
			String  merchantId = agentSignNotifyRequest.getMessage().getInfo()
					.getMerchantId();
			respInfo.setMerchantId(merchantId);
			respInfo.setOldStatus("2");

			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			agentSignNotifyResponse.setMessage(respMessage);
			responseJson = JSON.toJSONString(agentSignNotifyResponse);
			//加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
			logger.info("-----AgentSignNotifyController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
			responseJson = signatrue + "||"
					+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
			logger.info("-----AgentSignNotifyController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setContentType("text/plain");
			httpResponse.getWriter().write(responseJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
