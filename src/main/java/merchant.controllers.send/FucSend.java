package merchant.controllers.send;

import merchant.sign.SignatureAndVerification;
import merchant.utils.Base64Util;
import merchant.utils.HttpClientUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FucSend {

	private Logger logger = LoggerFactory.getLogger(FucSend.class);
	@Autowired
	private SignatureAndVerification signatureAndVerification;
	public String SendMsg(String reqUrl,String reqJson)
	{
		String res="";
		logger.info("加密前发送的请求报文：" + reqJson);
		//加签名
		String signatrue = signatureAndVerification.signWhithsha1withrsa(reqJson);
		logger.info("加密前发送的报文："+reqJson);
		//加签名
		String reqStr = signatrue+"||"+Base64Util.encodeData(reqJson);
		logger.info("发送的报文："+reqStr);		
		//发送签名信息获取返回签名信息
		String responseStr = HttpClientUtils.doPostStr(reqUrl, reqStr);
		logger.info("接收到的报文："+responseStr);
		return responseStr;
	}
	
}
