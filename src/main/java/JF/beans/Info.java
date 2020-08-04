package JF.beans;

import com.alibaba.fastjson.annotation.JSONField;

public class Info {
	/** 缴费项目编号*/
	@JSONField(name = "epayCode")
	private String epayCode;
	
	/** 第三方商户编号*/
	@JSONField(name = "merchantId")
	private String merchantId;
	
	/** 输入要素1*/
	@JSONField(name = "input1")
	private String input1;
	
	/** 输入要素2*/
	@JSONField(name = "input2")
	private String input2;
	
	/** 输入要素3*/
	@JSONField(name = "input3")
	private String input3;
	
	/** 输入要素4*/
	@JSONField(name = "input4")
	private String input4;
	
	/** 输入要素5*/
	@JSONField(name = "input5")
	private String input5;
	
	/** 16位客户号*/
	@JSONField(name = "userId")
	private String userId;
	
	/** 缴费中心流水号*/
	@JSONField(name = "traceNo")
	private String traceNo;

	public String getEpayCode() {
		return epayCode;
	}

	public void setEpayCode(String epayCode) {
		this.epayCode = epayCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	public String getInput3() {
		return input3;
	}

	public void setInput3(String input3) {
		this.input3 = input3;
	}

	public String getInput4() {
		return input4;
	}

	public void setInput4(String input4) {
		this.input4 = input4;
	}

	public String getInput5() {
		return input5;
	}

	public void setInput5(String input5) {
		this.input5 = input5;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	
}
