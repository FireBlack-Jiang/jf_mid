package merchant.beans;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 单笔缴费流水查询请求报文
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ConfirmTraceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;
	
	public ConfirmTraceRequest(){
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
	 * 
	 * @author DELL
	 *
	 */
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public static class Message implements Serializable {
		private static final long serialVersionUID = 1L;
		/** 消息头部 */
		@JSONField(name = "head")
		private Head head;
		/** 消息体  */
		@JSONField(name = "info")
		private Info info;
		
		public Message(){
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
		 * 
		 * @author DELL
		 *
		 */
		@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
		public class Head implements Serializable {
			private static final long serialVersionUID = 1L;
			/**  直连商户请求流水号 */
			@JSONField(name = "transSeqNum")
			private String transSeqNum;
			/**  交易码  */
			@JSONField(name = "transCode")
			private String transCode;
			/**  交易上行下送标志  */
			@JSONField(name = "transFlag")
			private String transFlag;
			/** 时间戳  */
			@JSONField(name = "timestamp")
			private String timestamp;
			
            public Head() {
				
			}
			
			@Override
			public String toString() {
				return "Head [transSeqNum=" + transSeqNum + ",transCode="
						+ transCode + ",transFlag=" + transFlag
						+ ",timestamp=" + timestamp + "]";
			}

			public String getTransSeqNum() {
				return transSeqNum;
			}

			@JSONField(name = "transSeqNum")
			public void setTransSeqNum(String transSeqNum) {
				this.transSeqNum = transSeqNum;
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

			public String getTimestamp() {
				return timestamp;
			}

			@JSONField(name = "timestamp")
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
		}//end Head
		
		
		/**
		 * 
		 * @author DELL
		 *
		 */
		@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
		public class Info implements Serializable {
			private static final long serialVersionUID = 1L;
			
			/** 商户编号*/
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 缴费中心流水号*/
			@JSONField(name = "traceNo")
			private String traceNo;
			
			public Info() {

			}
			
			public String getMerchantId() {
				return merchantId;
			}
			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}
			public String getTraceNo() {
				return traceNo;
			}
			@JSONField(name = "traceNo")
			public void setTraceNo(String traceNo) {
				this.traceNo = traceNo;
			}
		
			@Override
			public String toString() {
				return "Info [merchantId=" + merchantId + ", traceNo="
						+ traceNo + "]";
			}
			
		}//end Info
	}// end Message
}
