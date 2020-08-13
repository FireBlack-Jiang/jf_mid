package merchant.engineer;

import merchant.sign.SignatureAndVerification;
import merchant.utils.Base64Util;
import merchant.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MerchantEngineer {
    private Logger logger = LoggerFactory.getLogger(MerchantEngineer.class);
    private SignatureAndVerification signatureAndVerification=new SignatureAndVerification();
    public String SendMsg(String reqUrl,String reqJson) {
        String res = "";
        logger.info("[农行缴费中心引擎]加密前发送的请求报文：" + reqJson);
        //加签名
        String signatrue = signatureAndVerification.signWhithsha1withrsa(Base64Util.encodeData(reqJson));
        logger.info("[农行缴费中心引擎]报文signatrue：" + signatrue);
        //加签名
        String reqStr = signatrue + "||" + Base64Util.encodeData(reqJson);
        logger.info("[农行缴费中心引擎]发送的报文：" + reqStr);
        //发送签名信息获取返回签名信息
        String responseStr = HttpClientUtils.doPostStr(reqUrl, reqStr);
        logger.info("[农行缴费中心引擎]接收到的报文：" + responseStr);
        return responseStr;
    }
}