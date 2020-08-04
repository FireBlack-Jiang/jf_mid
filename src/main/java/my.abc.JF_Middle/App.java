package my.abc.JF_Middle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import JF.beans.Head;
import JF.beans.Info;
import JF.beans.Message;
import JF.beans.QueryBillRequest;

import com.alibaba.fastjson.JSON;

import merchant.controllers.send.FucSend;
import merchant.sign.SignatureAndVerification;
import merchant.utils.Base64Util;
import sun.rmi.runtime.Log;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
    	SignatureAndVerification d=new SignatureAndVerification();
    	QueryBillRequest reqbean=new QueryBillRequest();
    	reqbean.setFormat("json");
    	
    	Message message= new Message();
    	Head h=new Head();
    	Info i=new Info();
    	h.setBranchCode("2110");
    	h.setChannel("EBNK");
    	String stamp=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    	h.setTimeStamp(stamp);
    	h.setTransCode("queryBill");
    	h.setTransFlag("01");
    	h.setTransSeqNum("BRIDGE"+stamp+"");
    	i.setEpayCode("JF-EPAY2018080265602");
    	i.setInput1("123456");
    	i.setMerchantId("103881104410001");
    	i.setTraceNo("JF180827105846813462");
    	i.setUserId("1637206339848118");
    	message.setHead(h);
    	message.setInfo(i);
    	reqbean.setMessage(message);
    	String requestBody =  JSON.toJSONString(reqbean);
    	FucSend f=new FucSend();
    	String res=f.SendMsg("http://localhost:8080/jf-myabc/getDirectJoinMerchBill.do", requestBody);
    	System.out.println(res);
    	
    	
    	
    	
//    	Request request = new Request.Builder()
//    	        .url("http://localhost:8080/jf-myabc/getDirectJoinMerchBill.do")
//    	        .post(RequestBody.create(mediaType, requestBody))
//    	        .build();
//    	OkHttpClient okHttpClient = new OkHttpClient();
//    	okHttpClient.newCall(request).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call arg0, IOException arg1) {
//				// TODO Auto-generated method stub
//				System.out.println(arg1.getMessage());
//			}
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				// TODO Auto-generated method stub
//				 Headers headers = response.headers();
//				 
//			        for (int i = 0; i < headers.size(); i++) 
//			        {
//			        	System.out.println(headers.name(i) + ":" + headers.value(i));
//			        }
//			        System.out.println("body:"+ response.body().string());
//			}
//    	});
    	
    }
}
