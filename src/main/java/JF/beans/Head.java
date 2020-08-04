package JF.beans;

import com.alibaba.fastjson.annotation.JSONField;

public class Head {

	/**  渠道编码 */
	@JSONField(name = "channel")
	private String channel;
	
	/**  交易码  */
	@JSONField(name = "transCode")
	private String transCode;
	
	/**  交易上行下送标志位  */
	@JSONField(name = "transFlag")
	private String transFlag;			
	
	/**  缴费中心交易序列号 */
	@JSONField(name = "transSeqNum")
	private String transSeqNum;
	
	/**   时间戳  */
	@JSONField(name = "timeStamp")
	private String timeStamp;
	
	/**   4为分行iGoal码  */
	@JSONField(name = "branchCode")
	private String branchCode;
    
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	public String getTransSeqNum() {
		return transSeqNum;
	}

	public void setTransSeqNum(String transSeqNum) {
		this.transSeqNum = transSeqNum;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
}
