package merchant.beans;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  @author lenovo
 * 
 *  
 * 
 */
public class CheckBillResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="status")
	private String status;//鍟咵浠樻煡璇佹帴鍙ｆ槸鍚︽垚鍔熸爣蹇椼�SUCC/FAIL  蹇呴�銆�
	
	@JSONField(name="errCode")
	private String errCode;//鍟咵浠樻煡璇佹帴鍙ｈ繑鍥為敊璇爜銆�000/9999绛�  鍙负绌恒�
	
	@JSONField(name="errMsg")
	private String errMsg; //鏌ヨ瘉鎺ュ彛杩斿洖淇℃伅銆愬繀閫併�
	/*
	@JSONField(name="checkStatus")
	private String checkStatus; //鍟咵浠樻煡璇佹帴鍙ｈ繑鍥炴煡璇佺姸鎬併�鍙负绌恒�
	
	@JSONField(name="checkStatusMsg")
	private String checkStatusMsg; //鍟咵浠樻煡璇佹帴鍙ｈ繑鍥炴煡璇佺姸鎬佷腑鏂囨彁绀恒�鍙负绌恒�
	*/
	@JSONField(name="checkedTraceNo")
	private String checkedTraceNo; //鍟咵浠樻煡璇佹帴鍙ｆ煡璇佽繑鍥炵殑瀹為檯鎴愬姛鎵ｆ娴佹按鍙�
	
	@JSONField(name="status")
	public String getStatus() {
		return status;
	}
	@JSONField(name="status")
	public void setStatus(String status){
		this.status = status;
	}
	@JSONField(name="errCode")
	public String getErrCode() {
		return errCode;
	}
	@JSONField(name="errCode")
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	@JSONField(name="errMsg")
	public String getErrMsg() {
		return errMsg;
	}
	@JSONField(name="errMsg")
	public void setErrMsg(String errMsg){
		this.errMsg = errMsg;
	}
	/*
	@JSONField(name="checkStatus")
	public String getCheckStatus() {
		return checkStatus;
	}
	@JSONField(name="checkStatus")
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	@JSONField(name="checkStatusMsg")
	public String getCheckStatusMsg() {
		return checkStatusMsg;
	}
	@JSONField(name="checkStatusMsg")
	public void setCheckStatusMsg(String checkStatusMsg) {
		this.checkStatusMsg = checkStatusMsg;
	}
	*/
	@JSONField(name="checkedTraceNo")
	public String getCheckedTraceNo() {
		return checkedTraceNo;
	}
	@JSONField(name="checkedTraceNo")
	public void setCheckedTraceNo(String checkedTraceNo) {
		this.checkedTraceNo = checkedTraceNo;
	}
	
	/**
	 *  @return
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + "PayBillResponse [status=" + status + ", errCode=" + errCode +
				", errMsg=" + errMsg + ", checkedTraceNo=" + checkedTraceNo + "]";
	}
	
}
