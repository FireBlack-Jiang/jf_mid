package merchant.beans;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 鍒嗚绾夸笅娓犻亾鏀粯鎺ュ彛缁勮鐨勬煡璇㈠璞omain
 */
public class PayBillRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="userId")
	private String userId;    //16浣嶅鎴风紪鍙枫�蹇呰緭銆�
	
	@JSONField(name="epayCode")
	private String epayCode;   //缂磋垂椤圭洰缂栧彿銆愬繀杈撱�     eg锛欽F-EPAY2017040120805
	
	@JSONField(name="payAmt")
	private String payAmt;    //璁㈠崟鏀粯閲戦銆愬繀杈撱�
	
	@JSONField(name="channel")   //绾夸笅缂磋垂娓犻亾   TERM鏌滈潰娓犻亾  AUTO鑷姪缁堢娓犻亾   ZZDH鏅轰粯閫氭笭閬撱�蹇呰緭銆�
	private String channel;
	
	@JSONField(name="requestSeq")
	private String requestSeq;    //璇锋眰娴佹按鍙凤紝鐢卞垎琛岃嚜宸辩敓鎴愬苟閫佽繃鏉ョ粰鎴戜滑璁板綍娴佹按锛屾柟渚垮垎琛岃繘琛屾煡璇€�蹇呰緭銆�
	
	@JSONField(name="mobileNo")
	private String mobileNo;   // 鎵嬫満鍙�
	
	@JSONField(name="certificateID")   //韬唤璇佸彿鐮�
	private String certificateID;
	
	@JSONField(name="numEpayBill")
	private String numEpayBill;    //鍟咵浠樹笅鍗曡鍗曞彿锛岀敱鍒嗚
	//浼犻�杩囨潵銆愭壒閲忚处鍗曠洿鎺ヤ粠billDetail琛ㄩ噷鍙杗um_epay_bill瀛楁鍊硷紝鍚﹀垯浼犻�閫氳繃缂磋垂涓績0104杩斿洖鐨勭即璐规祦姘村彿traceNo銆戙�蹇呰緭銆�
	
	@JSONField(name="acct")
	private String acct;   //鍗″彿銆愬繀杈撱�
	
	@JSONField(name="accTyp")
	private String accTyp;   //鍗＄被鍨嬨�蹇呰緭銆�
	
	@JSONField(name="accPwd")
	private String accPwd;   //鍔犲瘑杩囩殑鍗″瘑鐮併�蹇呰緭銆�
	
	@JSONField(name="accName")
	private String accName;   //鍗¤处鎴峰悕
	
	@JSONField(name="input1")
	private String input1;   //缂磋垂瑕佺礌1銆愬繀杈撱�
	
	@JSONField(name="input2")
	private String input2;   //缂磋垂瑕佺礌2
	
	@JSONField(name="input3")
	private String input3;   //缂磋垂瑕佺礌3
	
	@JSONField(name="input4")
	private String input4;   //缂磋垂瑕佺礌4
	
	@JSONField(name="input5")
	private String input5;   //缂磋垂瑕佺礌5
	
	@JSONField(name="attach")
	private String attach;   //閫忎紶瀛楁
	
	@JSONField(name="option")
	private String option;   //鐢ㄦ埛閫夋嫨濂楅
	
	@JSONField(name="payInput")
	private String payInput;   //鐢ㄦ埛杈撳叆閲戦鎴栦唤棰�
	
	@JSONField(name="customizeFlag")//淇濆瓨瀹氬埗缂磋垂椤规爣璇�
	private String customizeFlag;
	
	
	@JSONField(name="customizeFlag")
	public String getCustomizeFlag() {
		return customizeFlag;
	}
	@JSONField(name="customizeFlag")
	public void setCustomizeFlag(String customizeFlag) {
		this.customizeFlag = customizeFlag;
	}
	@JSONField(name="userId")
	public String getUserId() {
		return userId;
	}
	@JSONField(name="userId")
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@JSONField(name="epayCode")
	public String getEpayCode() {
		return epayCode;
	}
	@JSONField(name="epayCode")
	public void setEpayCode(String epayCode) {
		this.epayCode = epayCode;
	}
	
	@JSONField(name="payAmt")
	public String getPayAmt() {
		return payAmt;
	}
	@JSONField(name="payAmt")
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	@JSONField(name="channel")
	public String getChannel() {
		return channel;
	}
	@JSONField(name="channel")
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@JSONField(name="requestSeq")
	public String getRequestSeq() {
		return requestSeq;
	}
	@JSONField(name="requestSeq")
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
	@JSONField(name="mobileNo")
	public String getMobileNo() {
		return mobileNo;
	}
	@JSONField(name="mobileNo")
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@JSONField(name="certificateID")
	public String getCertificateID() {
		return certificateID;
	}
	@JSONField(name="certificateID")
	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}
	@JSONField(name="numEpayBill")
	public String getNumEpayBill() {
		return numEpayBill;
	}
	@JSONField(name="numEpayBill")
	public void setNumEpayBill(String numEpayBill) {
		this.numEpayBill = numEpayBill;
	}
	@JSONField(name="acct")
	public String getAcct() {
		return acct;
	}
	@JSONField(name="acct")
	public void setAcct(String acct) {
		this.acct = acct;
	}
	@JSONField(name="accTyp")
	public String getAccTyp() {
		return accTyp;
	}
	@JSONField(name="accTyp")
	public void setAccTyp(String accTyp) {
		this.accTyp = accTyp;
	}
	@JSONField(name="accPwd")
	public String getAccPwd() {
		return accPwd;
	}
	@JSONField(name="accPwd")
	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}
	@JSONField(name="accName")
	public String getAccName() {
		return accName;
	}
	@JSONField(name="accName")
	public void setAccName(String accName) {
		this.accName = accName;
	}
	@JSONField(name="input1")
	public String getInput1() {
		return input1;
	}
	@JSONField(name="input1")
	public void setInput1(String input1) {
		this.input1 = input1;
	}
	@JSONField(name="input2")
	public String getInput2() {
		return input2;
	}
	@JSONField(name="input2")
	public void setInput2(String input2) {
		this.input2 = input2;
	}
	@JSONField(name="input3")
	public String getInput3() {
		return input3;
	}
	@JSONField(name="input3")
	public void setInput3(String input3) {
		this.input3 = input3;
	}
	@JSONField(name="input4")
	public String getInput4() {
		return input4;
	}
	@JSONField(name="input4")
	public void setInput4(String input4) {
		this.input4 = input4;
	}
	@JSONField(name="input5")
	public String getInput5() {
		return input5;
	}
	@JSONField(name="input5")
	public void setInput5(String input5) {
		this.input5 = input5;
	}
	@JSONField(name="attach")
	public String getAttach() {
		return attach;
	}
	@JSONField(name="attach")
	public void setAttach(String attach) {
		this.attach = attach;
	}
	@JSONField(name="option")
	public String getOption() {
		return option;
	}
	@JSONField(name="option")
	public void setOption(String option) {
		this.option = option;
	}
	@JSONField(name="payInput")
	public String getPayInput() {
		return payInput;
	}
	@JSONField(name="payInput")
	public void setPayInput(String payInput) {
		this.payInput = payInput;
	}
	@Override
	public String toString() {
		return super.toString() + "PayBillRequest [userId=" + userId + ", epayCode=" + epayCode
				+ ", payAmt=" + payAmt + ", channel=" + channel
				+ ", requestSeq=" + requestSeq + ", mobileNo=" + mobileNo
				+ ", certificateID=" + certificateID + ", numEpayBill="
				+ numEpayBill + ", acct=" + acct + ", accTyp=" + accTyp
				+ ", accPwd=" + accPwd + ", accName=" + accName + ", input1="
				+ input1 + ", input2=" + input2 + ", input3=" + input3
				+ ", input4=" + input4 + ", input5=" + input5 + ", attach="
				+ attach + ", option=" + option + ", payInput=" + payInput
				+ ", customizeFlag=" + customizeFlag + "]";
	}
	
}
