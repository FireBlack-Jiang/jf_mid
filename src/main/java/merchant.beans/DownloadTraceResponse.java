package merchant.beans;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商户直连文件下载返回报文
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DownloadTraceResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;
	
	public DownloadTraceResponse() {
		this.setMessage(new Message());
	}
	
	@Override
	public String toString() {
		return "BridgeFileResp [format=" + format + ",message=" + message
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
		 * 
		 * @author DELL
		 *
		 */
		@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
		public class Head implements Serializable {
			private static final long serialVersionUID = 1L;
			/**  交易码  */
			@JSONField(name = "transCode")
			private String transCode;
			/**  交易上行下送标志  */
			@JSONField(name = "transFlag")
			private String transFlag;
			/**  渠道交易流水号 */
			@JSONField(name = "transSeqNum")
			private String transSeqNum;
			/** 时间戳  */
			@JSONField(name = "timeStamp")
			private String timeStamp;
			/**  查询返回码 */
			@JSONField(name = "returnCode")
			private String returnCode;
			/**  返回提示信息  */
			@JSONField(name = "returnMessage")
			private String returnMessage;
			
            public Head() {
            	
            }
            
            @Override
			public String toString() {
				return "Head [transCode=" + transCode + ",transFlag="
						+ transFlag + ",transSeqNum=" + transSeqNum
						+ ",timeStamp=" + timeStamp + ",returnCode="
						+ returnCode + ",returnMessage=" + returnMessage + "]";
			}

			public String getTransCode() {
				return transCode;
			}

			@JSONField(name = "transCode")
			public void setTransCode(String transCode) {
				this.transCode = transCode;
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

			public String getTransFlag() {
				return transFlag;
			}

			@JSONField(name = "transFlag")
			public void setTransFlag(String transFlag) {
				this.transFlag = transFlag;
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
			
			/** 编码格式*/
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 对账单日期*/
			@JSONField(name = "billDate")
			private String billDate;
			
            public Info() {
				
			}
            
            @Override
			public String toString() {
            	return "Info [merchantId=" + merchantId + ",billDate=" + billDate + "]";
			}

			public String getMerchantId() {
				return merchantId;
			}

			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}

			public String getBillDate() {
				return billDate;
			}

			@JSONField(name = "billDate")
			public void setBillDate(String billDate) {
				this.billDate = billDate;
			}
            
		}//end Info
	}//end Message
}
