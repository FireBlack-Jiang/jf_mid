package merchant.beans.contract;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 授权缴费签约/解约结果查询响应报文
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgentSignQueryResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;

	public AgentSignQueryResponse() {
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

			/** 商户交易编号 */
			@JSONField(name = "orderNo")
			private String orderNo;
			/** 签约编号 */
			@JSONField(name = "agentSignNo")
			private String agentSignNo;
			/** 签约日期 */
			@JSONField(name = "signDate")
			private String signDate;
			/** 解约日期 */
			@JSONField(name = "unSignDate")
			private String unSignDate;
			/** 签约/解约状态 */
			@JSONField(name = "agentSignStatus")
			private String agentSignStatus;
			/** 证件号码 */
			@JSONField(name = "certificateNo")
			private String certificateNo;
			/** 证件类型 */
			@JSONField(name = "certificateType")
			private String certificateType;
			/** 签约卡号后四位 */
			@JSONField(name = "last4CardNo")
			private String last4CardNo;
			/** 签约卡类型 */
			@JSONField(name = "cardType")
			private String cardType;
			/** 商户编号 */
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 缴费项目唯一标识号 */
			@JSONField(name = "epayCode")
			private String epayCode;
			/** 输入要素1 */
			@JSONField(name = "input1")
			private String input1;
			/** 输入要素2 */
			@JSONField(name = "input2")
			private String input2;
			/** 输入要素3 */
			@JSONField(name = "input3")
			private String input3;
			/** 输入要素4 */
			@JSONField(name = "input4")
			private String input4;
			/** 输入要素5 */
			@JSONField(name = "input5")
			private String input5;
			/** 签约有效期 */
			@JSONField(name = "invaidDate")
			private String invaidDate;

			public Info() {

			}

			@Override
			public String toString() {
				return "Info [orderNo=" + orderNo + ", agentSignNo="
						+ agentSignNo + ", signDate=" + signDate
						+ ", unSignDate=" + unSignDate + ", agentSignStatus="
						+ agentSignStatus + ", certificateNo=" + certificateNo
						+ ", certificateType=" + certificateType
						+ ", last4CardNo=" + last4CardNo + ", cardType="
						+ cardType + ", merchantId=" + merchantId
						+ ", epayCode=" + epayCode + ", input1=" + input1
						+ ", input2=" + input2 + ", input3=" + input3
						+ ", input4=" + input4 + ", input5=" + input5
						+ ", invaidDate=" + invaidDate + "]";
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

			public String getSignDate() {
				return signDate;
			}

			@JSONField(name = "signDate")
			public void setSignDate(String signDate) {
				this.signDate = signDate;
			}

			public String getUnSignDate() {
				return unSignDate;
			}

			@JSONField(name = "unSignDate")
			public void setUnSignDate(String unSignDate) {
				this.unSignDate = unSignDate;
			}

			public String getAgentSignStatus() {
				return agentSignStatus;
			}

			@JSONField(name = "agentSignStatus")
			public void setAgentSignStatus(String agentSignStatus) {
				this.agentSignStatus = agentSignStatus;
			}

			public String getCertificateNo() {
				return certificateNo;
			}

			@JSONField(name = "certificateNo")
			public void setCertificateNo(String certificateNo) {
				this.certificateNo = certificateNo;
			}

			public String getCertificateType() {
				return certificateType;
			}

			@JSONField(name = "certificateType")
			public void setCertificateType(String certificateType) {
				this.certificateType = certificateType;
			}

			public String getLast4CardNo() {
				return last4CardNo;
			}

			@JSONField(name = "last4CardNo")
			public void setLast4CardNo(String last4CardNo) {
				this.last4CardNo = last4CardNo;
			}

			public String getCardType() {
				return cardType;
			}

			@JSONField(name = "cardType")
			public void setCardType(String cardType) {
				this.cardType = cardType;
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

			public String getInput1() {
				return input1;
			}

			@JSONField(name = "input1")
			public void setInput1(String input1) {
				this.input1 = input1;
			}

			public String getInput2() {
				return input2;
			}

			@JSONField(name = "input2")
			public void setInput2(String input2) {
				this.input2 = input2;
			}

			public String getInput3() {
				return input3;
			}

			@JSONField(name = "input3")
			public void setInput3(String input3) {
				this.input3 = input3;
			}

			public String getInput4() {
				return input4;
			}

			@JSONField(name = "input4")
			public void setInput4(String input4) {
				this.input4 = input4;
			}

			public String getInput5() {
				return input5;
			}

			@JSONField(name = "input5")
			public void setInput5(String input5) {
				this.input5 = input5;
			}

			public String getInvaidDate() {
				return invaidDate;
			}

			@JSONField(name = "invaidDate")
			public void setInvaidDate(String invaidDate) {
				this.invaidDate = invaidDate;
			}

		}// end Info
	}// end Message
}
