package merchant.controllers.receive;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import merchant.beans.QueryBillRequest;
import merchant.beans.QueryBillResponse;
import merchant.sign.SignatureAndVerification;
import merchant.utils.DateUtil;
import merchant.utils.HttpClientUtils;
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
 * 该类是除了金额规则为0之外的其他金额规则的controller
 * @author yzz
 *
 */
@Controller
public class QueryBillControllerOfOtherRules {

	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SignatureAndVerification signatureAndVerification;

	/**
	 * 账单查询接口(金额规则为2的)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchBillRule2.do", method = RequestMethod.POST)
	@ResponseBody
	public void getBill4DirectJoinMerchRule2(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.info("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			QueryBillResponse response = new QueryBillResponse(queryBillRequest);
			QueryBillResponse.Message respMessage = response.getMessage();
			QueryBillResponse.Message.Head respHead = response.getMessage()
					.getHead();
			QueryBillResponse.Message.Info respInfo = response.getMessage()
					.getInfo();
			ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			ArrayList<QueryBillResponse.Message.Info.Bill.OptionDetail> respOptionDetails = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.OptionDetail>();
			QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail1 = respBill.new OptionDetail(
					"1", "流量购买30M:", "0.01");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail2 = respBill.new OptionDetail(
					"2", "流量购买50M:", "0.02");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail3 = respBill.new OptionDetail(
					"3", "流量购买100M:", "0.03");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail4 = respBill.new OptionDetail(
					"4", "流量购买300M:", "0.04");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail5 = respBill.new OptionDetail(
					"5", "流量购买1个G:", "0.05");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail6 = respBill.new OptionDetail(
					"6", "流量购买3个G:", "0.06");
			respOptionDetails.add(optionDtail1);
			respOptionDetails.add(optionDtail2);
			respOptionDetails.add(optionDtail3);
			respOptionDetails.add(optionDtail4);
			respOptionDetails.add(optionDtail5);
			respOptionDetails.add(optionDtail6);
			respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
			respBill.setBillName("凯盛家园电费缴纳");
			respBill.setOweAmt("8.00");
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
			// 测试套餐模式
			String amtRule = "2";
			respInfo.setAmtRule(amtRule);
			/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
					"unitName", "6.66", "1");*/
			respBill.setOptionDetails(respOptionDetails);
			respInfo.setTotalBillCount("1");
			respInfo.setBill(respBills);
			logger.info("----------------------账单查询成功");
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			response.setMessage(respMessage);
			responseJson = JSON.toJSONString(response);
			// 加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
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

	/**
	 * 账单查询接口(金额规则为3的)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchBillRule3.do", method = RequestMethod.POST)
	@ResponseBody
	public void getBill4DirectJoinMerchRule3(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.info("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
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
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
					"欠费金额:", "50.02元");
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
			String amtRule = "3";
			respInfo.setAmtRule(amtRule);
			respBill.setOweAmt("50.02");
			respBill.setMinAmt("0.01");
			respBill.setMaxAmt("0.03");
			respBill.setBalance("50.00");
			respInfo.setTotalBillCount("1");
			respInfo.setBill(respBills);
			logger.info("----------------------账单查询成功");
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			response.setMessage(respMessage);
			responseJson = JSON.toJSONString(response);
			// 加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
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

	// ************************************单账单情景end*****************************************************************************************
	// ************************************多账单情景begin*****************************************************************************************

	/**
	 * 账单查询接口(金额规则为0的)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchMultiBill.do", method = RequestMethod.POST)
	@ResponseBody
	public void getMultipleBill4DirectJoinMerch(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.warn("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.warn("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.warn("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			QueryBillResponse response = new QueryBillResponse(queryBillRequest);
			QueryBillResponse.Message respMessage = response.getMessage();
			QueryBillResponse.Message.Head respHead = response.getMessage()
					.getHead();
			QueryBillResponse.Message.Info respInfo = response.getMessage()
					.getInfo();
			ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();

			QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
			respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo()+"3");
			respBill.setBillName("凯盛家园电费缴纳");
			respBill.setFeeAmt("0.00");
			respBill.setExpireDate("20200731");
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
			String amtRule = "0";
			respInfo.setAmtRule(amtRule);
			/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
					"unitName", "6.66", "1");*/
			respBill.setOweAmt("3.00");
			respBill.setFeeAmt("0.00");
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
					"缴费月份:", "2018年8月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
					"欠费金额:", "3.00元");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail4 = respBill.new DescDetail(
					"缴费月份:", "2018年8月份");
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
			QueryBillResponse.Message.Info.Bill respBill1 = respInfo.new Bill();
			respBill1.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo()+"1");
			respBill1.setBillName("北京市海淀区唐家岭新城T05区2号楼1单元1301");
			respBill1.setOweAmt("1.00");
			respBill1.setFeeAmt("0.00");
			respBill1.setExpireDate("20200731");
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail1 = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail11 = respBill1.new DescDetail(
					"缴费月份:", "2018年8月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail21 = respBill1.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail31 = respBill1.new DescDetail(
					"欠费金额:", "1.00元");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail41 = respBill1.new DescDetail(
					"缴费月份:", "2018年8月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail51 = respBill1.new DescDetail(
					"服务时间:", "每天0:30-23:30期间均可缴费");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail61 = respBill1.new DescDetail(
					"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
			respDescDetail1.add(descDtail11);
			respDescDetail1.add(descDtail21);
			respDescDetail1.add(descDtail31);
			respDescDetail1.add(descDtail41);
			respDescDetail1.add(descDtail51);
			respDescDetail1.add(descDtail61);
			respBill1.setDescDetails(respDescDetail1);
			QueryBillResponse.Message.Info.Bill respBill2 = respInfo.new Bill();
			respBill2.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo()+"2");
			respBill2.setBillName("北京市海淀区柳浪家园南里11号楼4单元501");
			respBill2.setOweAmt("2.00");
			respBill2.setFeeAmt("0.00");
			respBill2.setExpireDate("20200731");
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail2 = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail12 = respBill2.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail22 = respBill2.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail32 = respBill2.new DescDetail(
					"欠费金额:", "2.00元");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail42 = respBill2.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail52 = respBill2.new DescDetail(
					"服务时间:", "每天0:30-23:30期间均可缴费");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail62 = respBill2.new DescDetail(
					"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
			respDescDetail2.add(descDtail12);
			respDescDetail2.add(descDtail22);
			respDescDetail2.add(descDtail32);
			respDescDetail2.add(descDtail42);
			respDescDetail2.add(descDtail52);
			respDescDetail2.add(descDtail62);
			respBill2.setDescDetails(respDescDetail2);
			respBills.add(respBill);
			respBills.add(respBill1);
			respBills.add(respBill2);
			respInfo.setTotalBillCount("3");
			respInfo.setBill(respBills);
			// 随机返回成功或失败情况的报文
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			response.setMessage(respMessage);
			responseJson = JSON.toJSONString(response);
			// 加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
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
	
	

	/**
	 * 账单查询接口(金额规则为0的)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchMultiBillNew.do", method = RequestMethod.POST)
	@ResponseBody
	public void getDirectJoinMerchMultiBillNew(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.warn("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.warn("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.warn("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			QueryBillResponse response = new QueryBillResponse(queryBillRequest);
			QueryBillResponse.Message respMessage = response.getMessage();
			QueryBillResponse.Message.Head respHead = response.getMessage()
					.getHead();
			QueryBillResponse.Message.Info respInfo = response.getMessage()
					.getInfo();
			ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();
			for(int i=0;i<20;i++){
				QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
				respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo()+i);
				respBill.setBillName("凯盛家园电费缴纳");
				respBill.setFeeAmt("0.00");
				respBill.setExpireDate("20200731");
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
				String amtRule = "M";
				respInfo.setAmtRule(amtRule);
				/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
						"unitName", "6.66", "1");*/
				String d = "3.00";
				
				d = (double)(i * 0.01) + 3 +"";
				
				respBill.setOweAmt(d);
				respBill.setFeeAmt("0.00");
				ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = 
						new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
						"缴费月份:", "2018年8月份");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
						"供电局编号:", "4340152");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
						"欠费金额:", d );
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail4 = respBill.new DescDetail(
						"缴费月份:", "2018年8月份");
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
				respBills.add(respBill);
				
			}
			respInfo.setTotalBillCount("20");
			respInfo.setBill(respBills);
			// 随机返回成功或失败情况的报文
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");
			respHead.setTransFlag("02");
			respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
			respMessage.setInfo(respInfo);
			respMessage.setHead(respHead);
			response.setMessage(respMessage);
			responseJson = JSON.toJSONString(response);
			// 加签名
			String signatrue = signatureAndVerification
					.signWhithsha1withrsa(responseJson);
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
	
	
	/**
	 * 账单查询接口(金额规则为2的随机返回失败错误码)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchBill2Fail.do", method = RequestMethod.POST)
	@ResponseBody
	public void getBill4DirectJoinMerchRule2Fail(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.info("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
			QueryBillResponse response = new QueryBillResponse(queryBillRequest);
			QueryBillResponse.Message respMessage = response.getMessage();
			QueryBillResponse.Message.Head respHead = response.getMessage()
					.getHead();
			QueryBillResponse.Message.Info respInfo = response.getMessage()
					.getInfo();
			ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();
			ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
			ArrayList<QueryBillResponse.Message.Info.Bill.OptionDetail> respOptionDetails = 
					new ArrayList<QueryBillResponse.Message.Info.Bill.OptionDetail>();
			QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail1 = respBill.new OptionDetail(
					"1", "流量购买30M:", "3.00");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail2 = respBill.new OptionDetail(
					"2", "流量购买50M:", "5.00");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail3 = respBill.new OptionDetail(
					"3", "流量购买100M:", "10.00");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail4 = respBill.new OptionDetail(
					"4", "流量购买300M:", "20.00");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail5 = respBill.new OptionDetail(
					"5", "流量购买1个G:", "30.00");
			QueryBillResponse.Message.Info.Bill.OptionDetail optionDtail6 = respBill.new OptionDetail(
					"6", "流量购买3个G:", "50.00");
			respOptionDetails.add(optionDtail1);
			respOptionDetails.add(optionDtail2);
			respOptionDetails.add(optionDtail3);
			respOptionDetails.add(optionDtail4);
			respOptionDetails.add(optionDtail5);
			respOptionDetails.add(optionDtail6);
			respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
			respBill.setBillName("凯盛家园电费缴纳");
			respBill.setOweAmt("8.00");
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
			// 测试套餐模式
			String amtRule = "2";
			respInfo.setAmtRule(amtRule);
			/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
					"unitName", "6.66", "1");*/
			respBill.setOptionDetails(respOptionDetails);
			respInfo.setTotalBillCount("1");
			respInfo.setBill(respBills);
		/*	logger.info("----------------------账单查询成功");
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");*/
			logger.info("-------------------随机返回错误码-----");
			//错误码
	    	String[] codArr={"JH01","JH02","JH03","JH04","JH05","JH06","JH07","JH08","JH09"
	    			,"JH10","JH11","JH12","JH13","JH14","JH15","JH16","JH17"};
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

	/**
	 * 账单查询接口(金额规则为3的随机返回失败错误码)
	 * 
	 * @param queryRequest
	 * @param request
	 * @param httpResponse
	 */
	
	@RequestMapping(value = "/getDirectJoinMerchBill3Fail.do", method = RequestMethod.POST)
	@ResponseBody
	public void getBill4DirectJoinMerchRule3Fail(String queryRequest,
			HttpServletRequest request, HttpServletResponse httpResponse) {
		String responseJson = null;
		try {
			// 接收报文
			String requestContent = HttpClientUtils.getRequestBody(request)
					.trim();
			if (logger.isWarnEnabled()) {
				logger.info("收到的报文：{}", requestContent);
			}
			String signatureString = requestContent.substring(0,
					requestContent.indexOf("||"));
			logger.info("截取报文的signatureString:", signatureString);
			String requsetBody = requestContent.substring(signatureString
					.length() + 2);
			logger.info("截取报文的requsetBody:", requsetBody);
			String requset = new String(
					com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
			logger.info("-----解析完成后的requsetBody-------" + requset);
			QueryBillRequest queryBillRequest = JSON.parseObject(requset,
					new TypeReference<QueryBillRequest>() {
					});
			String traceNo = queryBillRequest.getMessage().getInfo()
					.getTraceNo();
			signatureAndVerification.read_cer_and_verify_sign(requsetBody,
					signatureString);
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
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
					"缴费月份:", "2018年6月份");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
					"供电局编号:", "4340152");
			QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
					"欠费金额:", "50.02元");
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
			String amtRule = "3";
			respInfo.setAmtRule(amtRule);
			respBill.setOweAmt("50.02");
			respBill.setMinAmt("0.01");
			respBill.setMaxAmt("0.03");
			respBill.setBalance("50.00");
			respInfo.setTotalBillCount("1");
			respInfo.setBill(respBills);
			/*logger.info("----------------------账单查询成功");
			respHead.setReturnCode("0000");
			respHead.setReturnMessage("账单查询成功，返回成功标志");*/
			logger.info("-------------------随机返回错误码-----");
			//错误码
	    	String[] codArr={"JH01","JH02","JH03","JH04","JH05","JH06","JH07","JH08","JH09"
	    			,"JH10","JH11","JH12","JH13","JH14","JH15","JH16","JH17"};
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
	
	// ************************************多账单情景合并成一个账单begin*****************************************************************************************

		/**
		 * 账单查询接口(金额规则为0的多账单合为单账单)
		 * 
		 * @param queryRequest
		 * @param request
		 * @param httpResponse
		 */
		
		@RequestMapping(value = "/getMultipleBillForOne.do", method = RequestMethod.POST)
		@ResponseBody
		public void getMultipleBillForOne(String queryRequest,
				HttpServletRequest request, HttpServletResponse httpResponse) {
			String responseJson = null;
			try {
				// 接收报文
				String requestContent = HttpClientUtils.getRequestBody(request)
						.trim();
				if (logger.isWarnEnabled()) {
					logger.warn("收到的报文：{}", requestContent);
				}
				String signatureString = requestContent.substring(0,
						requestContent.indexOf("||"));
				logger.warn("截取报文的signatureString:", signatureString);
				String requsetBody = requestContent.substring(signatureString
						.length() + 2);
				logger.warn("截取报文的requsetBody:", requsetBody);
				String requset = new String(
						com.alibaba.fastjson.util.Base64.decodeFast(requsetBody));
				logger.info("-----解析完成后的requsetBody-------" + requset);
				QueryBillRequest queryBillRequest = JSON.parseObject(requset,
						new TypeReference<QueryBillRequest>() {
						});
				String traceNo = queryBillRequest.getMessage().getInfo()
						.getTraceNo();
				signatureAndVerification.read_cer_and_verify_sign(requsetBody,
						signatureString);
				QueryBillResponse response = new QueryBillResponse(queryBillRequest);
				QueryBillResponse.Message respMessage = response.getMessage();
				QueryBillResponse.Message.Head respHead = response.getMessage()
						.getHead();
				QueryBillResponse.Message.Info respInfo = response.getMessage()
						.getInfo();
				ArrayList<QueryBillResponse.Message.Info.Bill> respBills = new ArrayList<QueryBillResponse.Message.Info.Bill>();

				QueryBillResponse.Message.Info.Bill respBill = respInfo.new Bill();
				respBill.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
				respBill.setBillName("凯盛家园电费缴纳");
				respBill.setFeeAmt("0.00");
				respBill.setExpireDate("20200731");
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
				String amtRule = "M";
				respInfo.setAmtRule(amtRule);
				/*QueryBillResponse.Message.Info.Bill.UnitDetail unitDetail = respBill.new UnitDetail(
						"unitName", "6.66", "1");*/
				respBill.setOweAmt("68.00");
				respBill.setFeeAmt("0.00");
				ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail = new 
						ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail1 = respBill.new DescDetail(
						"缴费月份:", "2018年8月份");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail2 = respBill.new DescDetail(
						"供电局编号:", "4340152");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail3 = respBill.new DescDetail(
						"欠费金额:", "68.00元");
				/*QueryBillResponse.Message.Info.Bill.DescDetail descDtail4 = respBill.new DescDetail(
						"缴费月份:", "2018年8月份");*/
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail5 = respBill.new DescDetail(
						"服务时间:", "每天0:30-23:30期间均可缴费");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail6 = respBill.new DescDetail(
						"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
				respDescDetail.add(descDtail1);
				respDescDetail.add(descDtail2);
				respDescDetail.add(descDtail3);
				//respDescDetail.add(descDtail4);
				respDescDetail.add(descDtail5);
				respDescDetail.add(descDtail6);
				respBill.setDescDetails(respDescDetail);
				QueryBillResponse.Message.Info.Bill respBill1 = respInfo.new Bill();
				respBill1.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
				respBill1.setBillName("北京市海淀区唐家岭新城T05区2号楼1单元1301");
				respBill1.setOweAmt("1.00");
				respBill1.setFeeAmt("0.00");
				respBill1.setExpireDate("20200731");
				ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail1 = 
						new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail11 = respBill1.new DescDetail(
						"缴费月份:", "2018年7月份");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail21 = respBill1.new DescDetail(
						"供电局编号:", "4340152");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail31 = respBill1.new DescDetail(
						"欠费金额:", "1.00元");
				/*QueryBillResponse.Message.Info.Bill.DescDetail descDtail41 = respBill1.new DescDetail(
						"缴费月份:", "2018年8月份");*/
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail51 = respBill1.new DescDetail(
						"服务时间:", "每天0:30-23:30期间均可缴费");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail61 = respBill1.new DescDetail(
						"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
				respDescDetail1.add(descDtail11);
				respDescDetail1.add(descDtail21);
				respDescDetail1.add(descDtail31);
				//respDescDetail1.add(descDtail41);
				respDescDetail1.add(descDtail51);
				respDescDetail1.add(descDtail61);
				respBill1.setDescDetails(respDescDetail1);
				QueryBillResponse.Message.Info.Bill respBill2 = respInfo.new Bill();
				respBill2.setBillNo(queryBillRequest.getMessage().getInfo().getTraceNo());
				respBill2.setBillName("2018年6月7月8月三个账单合并支付");
				respBill2.setOweAmt("2.00");
				respBill2.setFeeAmt("0.00");
				respBill2.setExpireDate("20200731");
				String input1=respInfo.getInput1();
				respInfo.setRemark("该账单一次返回3个子账单,且一次完成3个子账单的支付");
				//获取所有子账单的欠费金额总额并合并为一个OweAmt
				double oweAmtTotal=Double.valueOf(respBill2.getOweAmt())+Double.valueOf(respBill1.getOweAmt()) 
						+ Double.valueOf(respBill.getOweAmt());
				respBill2.setOweAmt(String.valueOf(oweAmtTotal));
				
				ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail> respDescDetail2 = 
						new ArrayList<QueryBillResponse.Message.Info.Bill.DescDetail>();
				/*QueryBillResponse.Message.Info.Bill.DescDetail descDtail12 = respBill2.new DescDetail(
						);*/
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail121 = respBill2.new DescDetail(
						"【6月缴费单】", "2018年6月份欠费账单||欠费2元||缴费号码"+input1+"||欠缴");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail22 = respBill2.new DescDetail(
						"【7月缴费单】", "2018年7月份欠费账单||欠费1元||缴费号码"+input1+"||欠缴");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail32 = respBill2.new DescDetail(
						"【8月缴费单】", "2018年8月份欠费账单||欠费68元||缴费号码"+input1+"||欠缴");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail42 = respBill2.new DescDetail(
						"【6、7、8三月欠费单合计】", "6月欠费2,7月欠费1,8月欠费68,总欠费金额1+2+68=71元");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail52 = respBill2.new DescDetail(
						"服务时间:", "每天0:30-23:30期间均可缴费");
				QueryBillResponse.Message.Info.Bill.DescDetail descDtail62 = respBill2.new DescDetail(
						"温馨提示:", "北京电力电费代缴，咨询电话：95598 该用户为：预付费用户");
				respDescDetail2.add(descDtail121);
				respDescDetail2.add(descDtail22);
				respDescDetail2.add(descDtail32);
				respDescDetail2.add(descDtail42);
				respDescDetail2.add(descDtail52);
				respDescDetail2.add(descDtail62);
				respBill2.setDescDetails(respDescDetail2);
				respBills.add(respBill2);
				respInfo.setTotalBillCount("1");
				respInfo.setBill(respBills);
				// 随机返回成功或失败情况的报文
				respHead.setReturnCode("0000");
				respHead.setReturnMessage("账单查询成功，返回成功标志");
				respHead.setTransFlag("02");
				respHead.setTimeStamp(DateUtil.get(YYYYMMDDHHMMSSSSS));
				respMessage.setInfo(respInfo);
				respMessage.setHead(respHead);
				response.setMessage(respMessage);
				responseJson = JSON.toJSONString(response);
				// 加签名
				String signatrue = signatureAndVerification
						.signWhithsha1withrsa(responseJson);
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
