package myabc;

import merchant.sign.SignatureAndVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication(scanBasePackages={"merchant"})
public class App 
{
    private final static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws IOException {
        logger.info("启动程序");
        ConfigurableApplicationContext ctx= SpringApplication.run(App.class, args);
        SignatureAndVerification r=  ctx.getBean(SignatureAndVerification.class);
        r.signWhithsha1withrsa("sjfiens");
    }
    @RequestMapping("/")
    String index(){
        logger.info("测试中文");
        return "hello  spring boot ";
    }

}
