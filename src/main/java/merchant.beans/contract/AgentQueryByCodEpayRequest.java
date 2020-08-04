package merchant.beans.contract;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 授权缴费根据缴费项目编号查询签约请求报文
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgentQueryByCodEpayRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;

	public AgentQueryByCodEpayRequest() {
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

			/** 商户编号 */
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 缴费项目编号 */
			@JSONField(name = "epayCode")
			private String epayCode;
			/** 查询状态 */
			@JSONField(name = "agentSignStatus")
			private String agentSignStatus;
			/** 页码 */
			@JSONField(name = "pageNo")
			private String pageNo;
			/** 页大小 */
			@JSONField(name = "pageSize")
			private String pageSize;

			public Info() {

			}
			
			public String getMerchantId() {
				return merchantId;
			}

			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}

			public String getEpayCode() {
				return epayCode;
			}

			@JSONField(name = "epayCode")
			public void setEpayCode(String epayCode) {
				this.epayCode = epayCode;
			}

			public String getAgentSignStatus() {
				return agentSignStatus;
			}

			@JSONField(name = "agentSignStatus")
			public void setAgentSignStatus(String agentSignStatus) {
				this.agentSignStatus = agentSignStatus;
			}

			public String getPageNo() {
				return pageNo;
			}

			@JSONField(name = "pageNo")
			public void setPageNo(String pageNo) {
				this.pageNo = pageNo;
			}

			public String getPageSize() {
				return pageSize;
			}

			@JSONField(name = "pageSize")
			public void setPageSize(String pageSize) {
				this.pageSize = pageSize;
			}


			@Override
			public String toString() {
				return "Info [merchantId=" + merchantId + ", epayCode"
						+ epayCode + ", agentSignStatus=" + agentSignStatus
						+ ", pageNo=" + pageNo + ", pageSize" + pageSize + "]";

			}

		}// end Info
	}// end Message
}
