package merchant.controllers.receive;

import com.alibaba.fastjson.JSON;
import merchant.sign.SignatureAndVerification;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FucResponse {
    private Logger logger = LoggerFactory.getLogger(FucResponse.class);
    @Autowired
    private SignatureAndVerification signatureAndVerification;
    public boolean Res(HttpServletResponse httpResponse,String responseJson)
    {
        try {
        // 加签名
        String signatrue = signatureAndVerification
                .signWhithsha1withrsa(responseJson);
        logger.info("signatrue" + responseJson);
        logger.info("responseJson加密前:" + responseJson);
        responseJson = signatrue + "||"
                + new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
        logger.info("responseJson加密后:" + responseJson);
        httpResponse.setCharacterEncoding("utf-8");
        httpResponse.setContentType("text/plain");

            httpResponse.getWriter().write(responseJson);
        } catch (IOException e) {
            logger.error("[响应缴费中心]失败",e);
            return false;
        }
        return true;
    }
}
