package merchant.beans.contract;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 授权缴费解约请求报文
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgentUnSignRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;

	public AgentUnSignRequest() {
		message = new Message();
	}

	@Override
	public String toString() {
		return "BridgeFileReq [format=" + format + ",message=" + message + "]";
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
	public static class Message implements Serializable {
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
			@JSONField(name = "timestamp")
			private String timestamp;

			public Head() {

			}

			@Override
			public String toString() {
				return "Head [transCode=" + transCode + ",transFlag="
						+ transFlag + ",transSeqNum=" + transSeqNum
						+ ",timestamp=" + timestamp + "]";
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

			public String getTimestamp() {
				return timestamp;
			}

			@JSONField(name = "timestamp")
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}

		}// end Head

		/**
		 * @author MaRui
		 *
		 */
		@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
		public class Info implements Serializable {
			private static final long serialVersionUID = 1L;

			/** 商户交易编号 */
			@JSONField(name = "orderNo")
			private String orderNo;
			/** 签约编号 */
			@JSONField(name = "agentSignNo")
			private String agentSignNo;
			/** 缴费项目编号 */
			@JSONField(name = "epayCode")
			private String epayCode;
			/** 商户编号 */
			@JSONField(name = "merchantId")
			private String merchantId;
			
			public Info() {

			}

			public String getOrderNo() {
				return orderNo;
			}

			@JSONField(name = "orderNo")
			public void setOrderNo(String orderNo) {
				this.orderNo = orderNo;
			}

			public String getAgentSignNo() {
				return agentSignNo;
			}

			@JSONField(name = "agentSignNo")
			public void setAgentSignNo(String agentSignNo) {
				this.agentSignNo = agentSignNo;
			}

			public String getEpayCode() {
				return epayCode;
			}

			@JSONField(name = "epayCode")
			public void setEpayCode(String epayCode) {
				this.epayCode = epayCode;
			}

			public String getMerchantId() {
				return merchantId;
			}

			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}

			@Override
			public String toString() {
				return "Info [orderNo" + orderNo + ", agentSignNo="
						+ agentSignNo + ", epayCode=" + epayCode
						+ ", merchantId=" + merchantId + "]";
			}

		}// end Info
	}// end Message
}
