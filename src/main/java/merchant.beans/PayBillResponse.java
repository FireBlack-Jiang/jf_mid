package merchant.beans;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 线下渠道支付接口返回信息domain
 *
 */
public class PayBillResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JSONField(name="status")
	private String status;//缴费是否成功标志【SUCC/FAIL  必送】
	
	@JSONField(name="errCode")
	private String errCode;//缴费交易码【0000/9999等   可为空】
	
	@JSONField(name="errMsg")
	private String errMsg; //缴费返回信息【必送】
	
	@JSONField(name="traceNo")
	private String traceNo; //缴费中心流水编号【可为空】
	
	@JSONField(name="traceStatus")    
	private String traceStatus; //流水状态【可为空】
	
	@JSONField(name="traceStatusMsg")    
	private String traceStatusMsg; //流水状态中文提示【可为空】
	
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
	@JSONField(name="traceNo")
	public String getTraceNo() {
		return traceNo;
	}
	@JSONField(name="traceNo")
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	@JSONField(name="traceStatus")
	public String getTraceStatus() {
		return traceStatus;
	}
	@JSONField(name="traceStatus")
	public void setTraceStatus(String traceStatus) {
		this.traceStatus = traceStatus;
	}
	@JSONField(name="traceStatusMsg")
	public String getTraceStatusMsg() {
		return traceStatusMsg;
	}
	@JSONField(name="traceStatusMsg")
	public void setTraceStatusMsg(String traceStatusMsg) {
		this.traceStatusMsg = traceStatusMsg;
	}
	
	/**
	 *  @return
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + "PayBillResponse [status=" + status + ", errCode=" + errCode +
				", errMsg=" + errMsg + ", traceNo=" + traceNo +", traceStatus=" + traceStatus +", traceStatusMsg=" + traceStatusMsg + "]";
	}
	
}
