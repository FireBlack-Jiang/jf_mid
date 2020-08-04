package merchant.beans;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商户直连文件下载请求报文
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DownloadTraceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;
	
	public DownloadTraceRequest(){
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
	public class Message implements Serializable {
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
			/**  渠道交易流水号 */
			@JSONField(name = "transSeqNum")
			private String transSeqNum;
			/**  交易码  */
			@JSONField(name = "transCode")
			private String transCode;
			/**  交易上行下送标志  */
			@JSONField(name = "transFlag")
			private String transFlag;
			/** 时间戳  */
			@JSONField(name = "timeStamp")
			private String timeStamp;
			
            public Head() {
				
			}
			
			@Override
			public String toString() {
				return "Head [transSeqNum=" + transSeqNum + ",transCode="
						+ transCode + ",transFlag=" + transFlag
						+ ",timeStamp=" + timeStamp + "]";
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

			public String getTimeStamp() {
				return timeStamp;
			}

			@JSONField(name = "timeStamp")
			public void setTimeStamp(String timeStamp) {
				this.timeStamp = timeStamp;
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
			/** 对账文件格式*/
			@JSONField(name = "fileType")
			private String fileType;
			/** 编码格式*/
			@JSONField(name = "charset")
			private String charset;
			/** 编码格式*/
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 对账单日期*/
			@JSONField(name = "billDate")
			private String billDate;
			/** 对账单类型*/
			@JSONField(name = "billType")
			private String billType;
			
			public Info(){
				
			}
			
			@Override
			public String toString() {
				return "Info [fileType=" + fileType + ",charset=" + charset + 
						",merchantId=" + merchantId + ",billDate=" + billDate + 
						",billType=" + billType + "]";
			}

			public String getFileType() {
				return fileType;
			}

			@JSONField(name = "fileType")
			public void setFileType(String fileType) {
				this.fileType = fileType;
			}

			public String getCharset() {
				return charset;
			}

			@JSONField(name = "charset")
			public void setCharset(String charset) {
				this.charset = charset;
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

			public String getBillType() {
				return billType;
			}

			@JSONField(name = "billType")
			public void setBillType(String billType) {
				this.billType = billType;
			}
		}//end Info
	}// end Message
}
