package merchant.controllers.receive;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import merchant.beans.QueryBillRequest;
import merchant.beans.QueryBillResponse;
import merchant.sign.SignatureAndVerification;
import merchant.utils.DateUtil;
import merchant.utils.StringUtil;
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
 * 查询账单随机返回错误码并错误提示信息
 * @author yzz
 *
 */
@Controller
public class QueryBillReturnCodeController {

	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//签名工具类
	@Autowired
	private SignatureAndVerification signatureAndVerification;

	// ************************************单账单情景begin*****************************************************************************************
	/**
	 * 查询账单返回错误码并错误提示信息
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	@RequestMapping(value = "/getReturnErrCode.do", method = RequestMethod.POST)
	@ResponseBody
	public void getReturnErrCode(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		logger.info("进入QueryBillReturnCodeController随机返回错误码-------(随机返回错误码)-------");
		String responseJson = null;
		try {
			//接收报文request返回截取并返回requsetBody和使用base64解析后的requsetBody
			Map<String,String> requestMap= signatureAndVerification.requestBodyOfBase64(request);
			//使用base64解析完成后的requsetBody
			String requsetBodyOfDecoded=requestMap.get("requsetBodyOfDecoded");
			//解析前的requsetBody
			String requsetBody=requestMap.get("requsetBody");
			//获取缴费中心传送过来的签名
			String signatureString=requestMap.get("signatureString");
			QueryBillRequest queryBillRequest = JSON.parseObject(requsetBodyOfDecoded,
					new TypeReference<QueryBillRequest>() {
					});
			//交易编号
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			// 验签
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			//返回给缴费中心的响应
			QueryBillResponse response = new QueryBillResponse(queryBillRequest);
			QueryBillResponse.Message respMessage = response.getMessage();
			QueryBillResponse.Message.Head respHead = response.getMessage()
					.getHead();
			QueryBillResponse.Message.Info respInfo = response.getMessage()
					.getInfo();
			ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
			respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
			respBill.setBillName("凯盛家园电费缴纳");
			respBill.setFeeAmt("0.00");
			respBill.setExpireDate("20200731");
			respBill.setDescDetails(respDescDetail);
			respBills.add(respBill);
			String epayCode = queryBillRequest.getMessage().getInfo()
					.getEpayCode();
			respInfo.setEpayCode(epayCode);
			String merchantId = queryBillRequest.getMessage().getInfo()
					.getMerchantId();
			respInfo.setMerchantId(merchantId);
			respInfo.setTraceNo(traceNo);
			respInfo.setInput1(queryBillRequest.getMessage().getInfo()
					.getInput1());
			respInfo.setInput2(queryBillRequest.getMessage().getInfo()
					.getInput2());
			respInfo.setInput3(queryBillRequest.getMessage().getInfo()
					.getInput3());
			respInfo.setInput4(queryBillRequest.getMessage().getInfo()
					.getInput4());
			respInfo.setInput5(queryBillRequest.getMessage().getInfo()
					.getInput5());
			respInfo.setCustName("张三丰");
			respInfo.setCustAddress("北京海淀区温泉凯盛家园1区1号楼2单元999室");
			respInfo.setCacheMem("0,0.00,S,张三丰,4340152");
			respInfo.setRemark("备注信息为空");
			//金额规则字段
			String amtRule = "0";
			respInfo.setAmtRule(amtRule);
			/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
					"unitName", "6.66", "1");*/
			respBill.setOweAmt("0.01");
			respBill.setFeeAmt("0.00");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
					"欠费金额:", "0.00元");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail4 = respBill.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail5 = respBill.new DescDetail(
					"服务时间:", "每天0:30-23:30期间均可缴费");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail6 = respBill.new DescDetail(
					"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
			respDescDetail.add(descDtail1);
			respDescDetail.add(descDtail2);
			respDescDetail.add(descDtail3);
			respDescDetail.add(descDtail4);
			respDescDetail.add(descDtail5);
			respDescDetail.add(descDtail6);
			respBill.setDescDetails(respDescDetail);
			respInfo.setTotalBillCount("1");
			respInfo.setBill(respBills);
			logger.info("-------------------随机返回错误码-----");
			//错误码
	    	String[] codArr={"JH01","JH02","JH03","JH04","JH05","JH06","JH07","JH08","JH09","JH10","JH11","JH12","JH13","JH14","JH15","JH16","JH17"};
	    	//随机返回错误码
			String errCod=StringUtil.RandomErrCod(codArr);
			respHead.setReturnCode(errCod);
			respHead.setReturnMessage("账单查询失败，返回错误码:"+errCod);
			logger.info("账单查询失败，返回错误码:" + errCod);
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			response.setMessage(respMessage);
			responseJson = JSON.toJSONString(response);
			// 加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
			logger.info("signatrue" + responseJson);
			logger.info("responseJson打印结果是（responseJson加密前）:" + responseJson);
			responseJson = signatrue + "||"
					+ new String(Base64.encodeBase64(responseJson.getBytes("utf-8")));
			logger.info("responseJson打印结果是（responseJson加密后）:" + responseJson);
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.setContentType("text/plain");
			httpResponse.getWriter().write(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
