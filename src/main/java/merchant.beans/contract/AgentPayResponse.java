package merchant.beans.contract;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 授权缴费单笔扣款响应报文
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgentPayResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;

	public AgentPayResponse() {
		message = new Message();
	}

	@Override
	public String toString() {
		return "AgentPayResponse [format=" + format + ",message=" + message
				+ "]";
	}

	public String getFormat() {
		return format;
	}

	@JSONField(name = "format")
	public void setFormat(String format) {
		this.format = format;
	}

	public Message getMessage() {
		return message;
	}

	@JSONField(name = "message")
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * @author MaRui
	 *
	 */
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public class Message implements Serializable {
		private static final long serialVersionUID = 1L;
		/** 消息头部 */
		@JSONField(name = "head")
		private Head head;
		/** 消息体 */
		@JSONField(name = "info")
		private Info info;

		public Message() {
			head = new Head();
			info = new Info();
		}

		@Override
		public String toString() {
			return "Message [head=" + head + ",info=" + info + "]";
		}

		public Head getHead() {
			return head;
		}

		@JSONField(name = "head")
		public void setHead(Head head) {
			this.head = head;
		}

		public Info getInfo() {
			return info;
		}

		@JSONField(name = "info")
		public void setInfo(Info info) {
			this.info = info;
		}

		/**
		 * @author MaRui
		 *
		 */
		@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
		public class Head implements Serializable {
			private static final long serialVersionUID = 1L;
			/** 交易码 */
			@JSONField(name = "transCode")
			private String transCode;
			/** 交易上行下送标志 */
			@JSONField(name = "transFlag")
			private String transFlag;
			/** 缴费中心交易序列号 */
			@JSONField(name = "transSeqNum")
			private String transSeqNum;
			/** 时间戳 */
			@JSONField(name = "timeStamp")
			private String timeStamp;
			/** 返回值 */
			@JSONField(name = "returnCode")
			private String returnCode;
			/** 返回提示信息 */
			@JSONField(name = "returnMessage")
			private String returnMessage;

			public Head() {

			}

			@Override
			public String toString() {
				return "Head [transCode=" + transCode + ", transFlag"
						+ transFlag + ", transSeqNum=" + transSeqNum
						+ ", timeStamp=" + timeStamp + ", returnCode="
						+ returnCode + ", returnMessage=" + returnMessage + "]";
			}

			public String getTransCode() {
				return transCode;
			}

			@JSONField(name = "transCode")
			public void setTransCode(String transCode) {
				this.transCode = transCode;
			}

			public String getTransFlag() {
				return transFlag;
			}

			@JSONField(name = "transFlag")
			public void setTransFlag(String transFlag) {
				this.transFlag = transFlag;
			}

			public String getTransSeqNum() {
				return transSeqNum;
			}

			@JSONField(name = "transSeqNum")
			public void setTransSeqNum(String transSeqNum) {
				this.transSeqNum = transSeqNum;
			}

			public String getTimeStamp() {
				return timeStamp;
			}

			@JSONField(name = "timeStamp")
			public void setTimeStamp(String timeStamp) {
				this.timeStamp = timeStamp;
			}

			public String getReturnCode() {
				return returnCode;
			}

			@JSONField(name = "returnCode")
			public void setReturnCode(String returnCode) {
				this.returnCode = returnCode;
			}

			public String getReturnMessage() {
				return returnMessage;
			}

			@JSONField(name = "returnMessage")
			public void setReturnMessage(String returnMessage) {
				this.returnMessage = returnMessage;
			}

		}// end Head

		/**
		 * @author MaRui
		 *
		 */
		@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
		public class Info implements Serializable {
			private static final long serialVersionUID = 1L;

			/** 缴费金额 */
			@JSONField(name = "amount")
			private String amount;
			/** 扣款状态 */
			@JSONField(name = "status")
			private String status;
			/** 账单编号 */
			@JSONField(name = "billNo")
			private String billNo;
			/** 缴费流水号 */
			@JSONField(name = "traceNo")
			private String traceNo;
			/** 商户编号 */
			@JSONField(name = "merchantId")
			private String merchantId;

			public Info() {

			}

			@Override
			public String toString() {
				return "Info [amount=" + amount + ", status=" + status
						+ ", billNo=" + billNo + ", traceNo=" + traceNo
						+ ", merchantId=" + merchantId + "]";
			}

			public String getAmount() {
				return amount;
			}

			@JSONField(name = "amount")
			public void setAmount(String amount) {
				this.amount = amount;
			}

			public String getStatus() {
				return status;
			}

			@JSONField(name = "status")
			public void setStatus(String status) {
				this.status = status;
			}

			public String getBillNo() {
				return billNo;
			}

			@JSONField(name = "billNo")
			public void setBillNo(String billNo) {
				this.billNo = billNo;
			}

			public String getTraceNo() {
				return traceNo;
			}

			@JSONField(name = "traceNo")
			public void setTraceNo(String traceNo) {
				this.traceNo = traceNo;
			}

			public String getMerchantId() {
				return merchantId;
			}

			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}

		}// end Info
	}// end Message
}
