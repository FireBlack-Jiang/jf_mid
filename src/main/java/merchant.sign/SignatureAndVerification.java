package merchant.sign;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import merchant.utils.HttpClientUtils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * 验签和加签工具类
 * @author yzz
 *
 */
@Component
public class SignatureAndVerification {
	@Value("${pfxPath}")
	private  String PFXPATH;
	@Value("${cerPath}")
	private  String CERPATH="certificate\\TrustPay.cer";
	@Value("${keystore_password}")
	private  String KEYSTORE_PASSWORD="abcd1234";
	@Value("${keystore_alias}")
	private  String KEYSTORE_ALIAS="abcd1234";
    public  static String  rootPath=System.getProperty("user.dir")+File.separator+"resources"+File.separator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 加签名
	 * @param dataString
	 * @return
	 */
	public  String signWhithsha1withrsa(String dataString) {
		String signatureString = null;
		String filePath =rootPath+ PFXPATH;
		logger.info("签名路径："+filePath);
		try {


			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(filePath);
			char[] nPassword = null;
			if ((KEYSTORE_PASSWORD == null)
					|| KEYSTORE_PASSWORD.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = KEYSTORE_PASSWORD.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			logger.info("keystore type=" + ks.getType());
			Enumeration<String> enums = ks.aliases();
			String keyAlias = null;
			if (enums.hasMoreElements()) 
			{
				keyAlias = (String) enums.nextElement();
//				System.out.println("alias=[" + keyAlias + "]");
			}
//			System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			java.security.cert.Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
//			System.out.println("cert class = " + cert.getClass().getName());
//			System.out.println("cert = " + cert);
//			System.out.println("public key = " + pubkey);
//			System.out.println("private key = " + prikey);
			// SHA1withRSA算法进行签名
			Signature sign = Signature.getInstance("SHA1withRSA");
			sign.initSign(prikey);
			byte[] data = dataString.getBytes("utf-8");
			byte[] dataBase= Base64.encodeBase64(data);
			// 更新用于签名的数据
			sign.update(dataBase);
			byte[] signature = sign.sign();
			signatureString = new String(Base64.encodeBase64(signature));
			logger.info("--------signature is : " + signatureString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("签名异常",e);
		}
		return signatureString;
	}

	/**
	 * 读取cer并验证公钥签名
	 */
	public  boolean read_cer_and_verify_sign(String requsetBody, String signature) {

		X509Certificate cert = null;
		String filePath=rootPath+CERPATH;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) cf
					.generateCertificate(new FileInputStream(new File(
							filePath)));
			PublicKey publicKey = cert.getPublicKey();
			String publicKeyString = new String(Base64.encodeBase64(publicKey
					.getEncoded()));
			logger.info("-----------------公钥--------------------");
			logger.info(publicKeyString);
			logger.info("-----------------公钥--------------------");
			Signature verifySign = Signature.getInstance("SHA1withRSA");
			verifySign.initVerify(publicKey);
			// 用于验签的数据
			verifySign.update(requsetBody.getBytes("utf-8"));
			boolean flag = verifySign.verify(com.alibaba.fastjson.util.Base64
					.decodeFast(signature));// 验签由第三方做
			logger.info("verifySign is " + flag);
			return flag;
		} catch (Exception e) {
			logger.error("验签异常",e);
			return false;
		}

	}
	
	/**
	 * 接收报文返回requsetBody和使用base64解析后的requsetBody以及缴费中心传送的签名
	 */

	public Map<String,String> requestBodyOfBase64(HttpServletRequest request){
		Map<String,String> requestMap=new HashMap<String,String>();
		// 接收报文
		String requestContent=null;
		try {
			requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (logger.isWarnEnabled()) {
			logger.info("收到的报文：{}", requestContent);
		}
		String signatureString = requestContent.substring(0,
				requestContent.indexOf("||"));
		logger.info("报文的签名:{}", signatureString);
		String requsetBody = requestContent.substring(signatureString
				.length() + 2);
		logger.info("报文的内容:{}", requsetBody);
		String requsetBodyOfDecoded = new String(
				com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
		logger.info("-----解析完成后的requsetBody-------" + requsetBodyOfDecoded);
		//使用base64解析完成后的requsetBody
		requestMap.put("requsetBodyOfDecoded",requsetBodyOfDecoded);
		//解析前的requsetBody
		requestMap.put("requsetBody",requsetBody);
		//获取缴费中心传送过来的签名
		requestMap.put("signatureString",signatureString);
		return requestMap;
		
	} 
	
}
