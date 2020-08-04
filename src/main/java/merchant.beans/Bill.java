package merchant.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @author MaRui
 *
 */
public class Bill implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String feeAmt;
	private String payAmt;
	private String numEpayBill;
	private String namEpayBill;
	private String attach;
	private String feeDetailSize;
	private String amtRule;
	private String totalAmt;
	private List<Map<String, String>> feeDetails;
	
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getNumEpayBill() {
		return numEpayBill;
	}
	public void setNumEpayBill(String numEpayBill) {
		this.numEpayBill = numEpayBill;
	}
	public String getNamEpayBill() {
		return namEpayBill;
	}
	public void setNamEpayBill(String namEpayBill) {
		this.namEpayBill = namEpayBill;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getFeeDetailSize() {
		return feeDetailSize;
	}
	public void setFeeDetailSize(String feeDetailSize) {
		this.feeDetailSize = feeDetailSize;
	}
	public String getAmtRule() {
		return amtRule;
	}
	public void setAmtRule(String amtRule) {
		this.amtRule = amtRule;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public List<Map<String, String>> getFeeDetails() {
		return feeDetails;
	}
	public void setFeeDetails(List<Map<String, String>> feeDetails) {
		this.feeDetails = feeDetails;
	}
	
}
