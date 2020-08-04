package merchant.controllers.receive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import merchant.beans.ChargeBillRequest;
import merchant.beans.ChargeBillResponse;
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
public class ChargeBillController {
	
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
	@RequestMapping(value = "/getRequest4Sale.do", method = RequestMethod.POST)
	@ResponseBody
	public void getRequest4Sale(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			logger.info("--------进入getRequest4Sale----------------------------------");
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request).trim();
			if (logger.isWarnEnabled()) {
				logger.info("-----ChargeBillController------------收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("-----ChargeBillController------------截取报文的signatureString:", signatureString);
			String requestBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("-----ChargeBillController------------截取报文的requestBody:", requestBody);
			//如果有双引号，则截取双引号内requestBody的内容
			Pattern p=Pattern.compile("\"");
			Matcher m=p.matcher(requestBody);
			while(m.find()){
				requestBody=requestBody.replace(m.group(), "");
				logger.info("-----ChargeBillController------如果有双引号，则截取后的requestBody:", requestBody);
			}
			//requestBody是base64加密后的数据，需解析出来
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requestBody));
			logger.info("-----ChargeBillController------------解析完成后的requestBody-------" + requset);
			ChargeBillRequest chargeBillRequest = JSON.parseObject(requset,
					new TypeReference<ChargeBillRequest>() {
					});
			 /** 销账报文重发次数，通过resendTimes此字段识别销账报文是否为重发的，0表示首次、1表示重发一次，2表示重发2次，最多重发3次*/ 
			if(chargeBillRequest!=null&&"0".equals(chargeBillRequest.getMessage().getInfo().getResendTimes())){
				// 验签(验签是解析前的requestBody)
				signatureAndVerification.read_cer_and_verify_sign(requestBody,
						signatureString);
				ChargeBillResponse chargeBillResponse = new ChargeBillResponse(
						chargeBillRequest);
				ChargeBillResponse.Message respMessage = chargeBillResponse
						.getMessage();
				ChargeBillResponse.Message.Head respHead = chargeBillResponse
						.getMessage().getHead();
				ChargeBillResponse.Message.Info respInfo = chargeBillResponse
						.getMessage().getInfo();
				respHead.setTransFlag("02");
				respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
				// respHead.setChannel("MBNK");
				respHead.setChannel(chargeBillRequest.getMessage().getHead()
						.getChannel());
				// respHead.setTranCode("chargeBill");
				respHead.setTransCode(chargeBillRequest.getMessage().getHead()
						.getTransCode());
				respHead.setTransSeqNum(chargeBillRequest.getMessage().getHead()
						.getTransSeqNum());
				//测试销账返回报文中，本来是销账成功的报文，但是不要送0000成功码   (JF190510134746710555这个流水号是在Demo的returnCode设置成null的时候产生的，流水状态为6；)
				respHead.setReturnCode("0000");
				respHead.setReturnMessage("账单缴费成功");
				String epayCode = chargeBillRequest.getMessage().getInfo()
						.getEpayCode();
				String traceNo = chargeBillRequest.getMessage().getInfo()
						.getTraceNo();
				String numOpenMerchantOrder = chargeBillRequest.getMessage()
						.getInfo().getNumOpenMerchantOrder();
				respInfo.setNumOpenMerchantOrder(numOpenMerchantOrder);
				respInfo.setEpayCode(epayCode);
				respInfo.setTraceNo(traceNo);
				/**
				 * 返回码为0000时不读取本字段；
				 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
				 */
				if (!"0000".equals(respHead.getReturnCode())) {
					respInfo.setRefundFlag("true");
				}
				respMessage.setInfo(respInfo);
				respMessage.setHead(respHead);
				chargeBillResponse.setMessage(respMessage);
				responseJson = JSON.toJSONString(chargeBillResponse);
				//加签名
				String signatrue = signatureAndVerification
						.signWhithsha1withrsa(responseJson);
				logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
				responseJson = signatrue + "||"
						+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
				logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
				httpResponse.setCharacterEncoding("utf-8");
				httpResponse.setContentType("text/plain");
				httpResponse.getWriter().write(responseJson);
			  }else{
				//销账报文重发次数，通过resendTimes此字段识别销账报文是否为重发的，0表示首次、1表示重发一次，2表示重发2次，最多重发3次
				//商户端要注意销账重复通知的情况，要进行订单唯一性处理

				// 验签(验签是解析前的requestBody)
				signatureAndVerification.read_cer_and_verify_sign(requestBody,
						signatureString);
				ChargeBillResponse chargeBillResponse = new ChargeBillResponse(
						chargeBillRequest);
				ChargeBillResponse.Message respMessage = chargeBillResponse
						.getMessage();
				ChargeBillResponse.Message.Head respHead = chargeBillResponse
						.getMessage().getHead();
				ChargeBillResponse.Message.Info respInfo = chargeBillResponse
						.getMessage().getInfo();
				respHead.setTransFlag("02");
				respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
				// respHead.setChannel("MBNK");
				respHead.setChannel(chargeBillRequest.getMessage().getHead()
						.getChannel());
				// respHead.setTranCode("chargeBill");
				respHead.setTransCode(chargeBillRequest.getMessage().getHead()
						.getTransCode());
				respHead.setTransSeqNum(chargeBillRequest.getMessage().getHead()
						.getTransSeqNum());
				// 销账报文重发次数，通过resendTimes此字段识别销账报文是否为重发的，0表示首次、1表示重发一次，2表示重发2次，最多重发3次
				// 商户端要注意销账重复通知的情况，要进行订单唯一性处理
				if (chargeBillRequest != null
						&& ((Integer.parseInt(chargeBillRequest.getMessage()
								.getInfo().getResendTimes()))) % 4 == 0) {
					respHead.setReturnCode("0000");
					respHead.setReturnMessage("账单缴费成功");
					/**
					 * 返回码为0000时不读取本字段；
					 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
					 */
					if (!"0000".equals(respHead.getReturnCode())) {
						respInfo.setRefundFlag("true");
					}
					
				}else if(chargeBillRequest != null
						&& ((Integer.parseInt(chargeBillRequest.getMessage()
								.getInfo().getResendTimes()))) % 4 ==1){
					respHead.setReturnCode("1111");
					respHead.setReturnMessage("账单缴费失敗");
					/**
					 * 返回码为0000时不读取本字段；
					 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
					 */
					if (!"0000".equals(respHead.getReturnCode())) {
						respInfo.setRefundFlag("false");
					}
					
				}else if(chargeBillRequest != null
						&& ((Integer.parseInt(chargeBillRequest.getMessage()
								.getInfo().getResendTimes()))) % 4 ==2){
					respHead.setReturnCode("1111");
					respHead.setReturnMessage("账单缴费失敗");
					/**
					 * 返回码为0000时不读取本字段；
					 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
					 */
					if (!"0000".equals(respHead.getReturnCode())) {
						respInfo.setRefundFlag("true");
					}
					
					
				}else if(chargeBillRequest != null
						&& ((Integer.parseInt(chargeBillRequest.getMessage()
								.getInfo().getResendTimes()))) % 4 == 3){
					respHead.setReturnCode("JH01");
					respHead.setReturnMessage("账单缴费失敗");
					/**
					 * 返回码为0000时不读取本字段；
					 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
					 */
					if (!"0000".equals(respHead.getReturnCode())) {
						respInfo.setRefundFlag("");
					}
					
				}
				String epayCode = chargeBillRequest.getMessage().getInfo()
						.getEpayCode();
				String traceNo = chargeBillRequest.getMessage().getInfo()
						.getTraceNo();
				String numOpenMerchantOrder = chargeBillRequest.getMessage()
						.getInfo().getNumOpenMerchantOrder();
				respInfo.setNumOpenMerchantOrder(numOpenMerchantOrder);
				respInfo.setEpayCode(epayCode);
				respInfo.setTraceNo(traceNo);
				
				respMessage.setInfo(respInfo);
				respMessage.setHead(respHead);
				chargeBillResponse.setMessage(respMessage);
				responseJson = JSON.toJSONString(chargeBillResponse);
				//加签名
				String signatrue = signatureAndVerification
						.signWhithsha1withrsa(responseJson);
				logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
				responseJson = signatrue + "||"
						+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
				logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
				httpResponse.setCharacterEncoding("utf-8");
				httpResponse.setContentType("text/plain");
				httpResponse.getWriter().write(responseJson);
			
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 接收账单缴费（销账失败）接口   实时退款respInfo.setRefundFlag("true");
	 * 
	 * @param format
	 * @return
	 */
	@RequestMapping(value = "/getRequest4SaleFail.do", method = RequestMethod.POST)
	@ResponseBody
	public void getRequest4SaleFail(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			logger.info("---------------进入--getRequest4SaleFail------------------------------");
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request).trim();
			if (logger.isWarnEnabled()) {
				logger.info("-----ChargeBillController------------收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("-----ChargeBillController------------截取报文的signatureString:", signatureString);
			String requestBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("-----ChargeBillController------------截取报文的requestBody:", requestBody);
			//requestBody是base64加密后的数据，需解析出来
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requestBody));
			logger.info("-----ChargeBillController------------解析完成后的requestBody-------" + requset);
			ChargeBillRequest chargeBillRequest = JSON.parseObject(requset,
					new TypeReference<ChargeBillRequest>() {
					});
			// 验签
			signatureAndVerification.read_cer_and_verify_sign(requestBody, signatureString);
			ChargeBillResponse chargeBillResponse = new ChargeBillResponse(
					chargeBillRequest);
			ChargeBillResponse.Message respMessage = chargeBillResponse
					.getMessage();
			ChargeBillResponse.Message.Head respHead = chargeBillResponse
					.getMessage().getHead();
			ChargeBillResponse.Message.Info respInfo = chargeBillResponse
					.getMessage().getInfo();
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			// respHead.setChannel("MBNK");
			respHead.setChannel(chargeBillRequest.getMessage().getHead()
					.getChannel());
			// respHead.setTranCode("chargeBill");
			respHead.setTransCode(chargeBillRequest.getMessage().getHead()
					.getTransCode());
			respHead.setTransSeqNum(chargeBillRequest.getMessage().getHead()
					.getTransSeqNum());
			respHead.setReturnCode("1111");
			respHead.setReturnMessage("账单缴费失败");

			String epayCode = chargeBillRequest.getMessage().getInfo()
					.getEpayCode();
			String traceNo = chargeBillRequest.getMessage().getInfo()
					.getTraceNo();
			String numOpenMerchantOrder = chargeBillRequest.getMessage()
					.getInfo().getNumOpenMerchantOrder();
			respInfo.setNumOpenMerchantOrder(numOpenMerchantOrder);
			respInfo.setEpayCode(epayCode);
			respInfo.setTraceNo(traceNo);
			/**
			 * 返回码为0000时不读取本字段；
			 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
			 */
			if (!"0000".equals(respHead.getReturnCode())) {
				respInfo.setRefundFlag("true");
			}
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			chargeBillResponse.setMessage(respMessage);
			responseJson = JSON.toJSONString(chargeBillResponse);
			//加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
			responseJson = signatrue + "||"
					+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setContentType("text/plain");
			httpResponse.getWriter().write(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 接收账单缴费（销账失败）接口   不做退款respInfo.setRefundFlag("false");
	 * 
	 * @param format
	 * @return
	 */
	@RequestMapping(value = "/getRequest4SaleFailFalse.do", method = RequestMethod.POST)
	@ResponseBody
	public void getRequest4SaleFailFalse(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			logger.info("---------------进入--getRequest4SaleFail------------------------------");
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request).trim();
			if (logger.isWarnEnabled()) {
				logger.info("-----ChargeBillController------------收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("-----ChargeBillController------------截取报文的signatureString:", signatureString);
			String requestBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("-----ChargeBillController------------截取报文的requestBody:", requestBody);
			//requestBody是base64加密后的数据，需解析出来
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requestBody));
			logger.info("-----ChargeBillController------------解析完成后的requestBody-------" + requset);
			ChargeBillRequest chargeBillRequest = JSON.parseObject(requset,
					new TypeReference<ChargeBillRequest>() {
					});
			// 验签
			signatureAndVerification.read_cer_and_verify_sign(requestBody, signatureString);
			ChargeBillResponse chargeBillResponse = new ChargeBillResponse(
					chargeBillRequest);
			ChargeBillResponse.Message respMessage = chargeBillResponse
					.getMessage();
			ChargeBillResponse.Message.Head respHead = chargeBillResponse
					.getMessage().getHead();
			ChargeBillResponse.Message.Info respInfo = chargeBillResponse
					.getMessage().getInfo();
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			// respHead.setChannel("MBNK");
			respHead.setChannel(chargeBillRequest.getMessage().getHead()
					.getChannel());
			// respHead.setTranCode("chargeBill");
			respHead.setTransCode(chargeBillRequest.getMessage().getHead()
					.getTransCode());
			respHead.setTransSeqNum(chargeBillRequest.getMessage().getHead()
					.getTransSeqNum());
			String numOpenMerchantOrder = chargeBillRequest.getMessage()
					.getInfo().getNumOpenMerchantOrder();
			respInfo.setNumOpenMerchantOrder(numOpenMerchantOrder);
			respHead.setReturnCode("1111");
			respHead.setReturnMessage("账单缴费失败");

			String epayCode = chargeBillRequest.getMessage().getInfo()
					.getEpayCode();
			String traceNo = chargeBillRequest.getMessage().getInfo()
					.getTraceNo();
			respInfo.setEpayCode(epayCode);
			respInfo.setTraceNo(traceNo);
			/**
			 * 返回码为0000时不读取本字段；
			 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
			 */
			if (!"0000".equals(respHead.getReturnCode())) {
				respInfo.setRefundFlag("false");
			}
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			chargeBillResponse.setMessage(respMessage);
			responseJson = JSON.toJSONString(chargeBillResponse);
			//加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
			responseJson = signatrue + "||"
					+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setContentType("text/plain");
			httpResponse.getWriter().write(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 接收账单缴费（销账失败）接口  返回空报文
	 * 
	 * @param format
	 * @return
	 */
	@RequestMapping(value = "/getRequest4SaleFailNull.do", method = RequestMethod.POST)
	@ResponseBody
	public void getRequest4SaleFailNull(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			logger.info("---------------进入--getRequest4SaleFailNull------------------------------");
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request).trim();
			if (logger.isWarnEnabled()) {
				logger.info("-----ChargeBillController------------收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("-----ChargeBillController------------截取报文的signatureString:", signatureString);
			String requestBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("-----ChargeBillController------------截取报文的requestBody:", requestBody);
			//requestBody是base64加密后的数据，需解析出来
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requestBody));
			logger.info("-----ChargeBillController------------解析完成后的requestBody-------" + requset);
			ChargeBillRequest chargeBillRequest = JSON.parseObject(requset,
					new TypeReference<ChargeBillRequest>() {
					});
			// 验签
			signatureAndVerification.read_cer_and_verify_sign(requestBody, signatureString);
			ChargeBillResponse chargeBillResponse = new ChargeBillResponse(
					chargeBillRequest);
			ChargeBillResponse.Message respMessage = chargeBillResponse
					.getMessage();
			ChargeBillResponse.Message.Head respHead = chargeBillResponse
					.getMessage().getHead();
			ChargeBillResponse.Message.Info respInfo = chargeBillResponse
					.getMessage().getInfo();
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			// respHead.setChannel("MBNK");
			respHead.setChannel(chargeBillRequest.getMessage().getHead()
					.getChannel());
			// respHead.setTranCode("chargeBill");
			respHead.setTransCode(chargeBillRequest.getMessage().getHead()
					.getTransCode());
			respHead.setTransSeqNum(chargeBillRequest.getMessage().getHead()
					.getTransSeqNum());
			respHead.setReturnCode("1111");
			respHead.setReturnMessage("账单缴费失败");

			String epayCode = chargeBillRequest.getMessage().getInfo()
					.getEpayCode();
			String traceNo = chargeBillRequest.getMessage().getInfo()
					.getTraceNo();
			String numOpenMerchantOrder = chargeBillRequest.getMessage()
					.getInfo().getNumOpenMerchantOrder();
			respInfo.setNumOpenMerchantOrder(numOpenMerchantOrder);
			respInfo.setEpayCode(epayCode);
			respInfo.setTraceNo(traceNo);
			/**
			 * 返回码为0000时不读取本字段；
			 * 返回码非0000时，必须返回本标志位信息。返回true标志自动实时退款，返回false标志不做退款
			 */
			if (!"0000".equals(respHead.getReturnCode())) {
				respInfo.setRefundFlag("false");
			}
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			chargeBillResponse.setMessage(respMessage);
			responseJson = JSON.toJSONString(chargeBillResponse);
			//加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密前）:" + responseJson);
			responseJson = signatrue + "||"
					+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
			logger.info("-----ChargeBillController------------responseJson打印结果是（responseJson加密后）:" + responseJson);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setContentType("text/plain");
			httpResponse.getWriter().write("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
