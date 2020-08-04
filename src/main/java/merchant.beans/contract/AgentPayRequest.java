package merchant.beans.contract;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 授权缴费单笔扣款
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgentPayRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;

	public AgentPayRequest() {
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

			/** 缴费项目编号 */
			@JSONField(name = "epayCode")
			private String epayCode;
			/** 商户编号 */
			@JSONField(name = "merchantId")
			private String merchantId;
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
			/** 账单编号 */
			@JSONField(name = "billNo")
			private String billNo;
			/** 签约编号 */
			@JSONField(name = "agentSignNo")
			private String agentSignNo;
			/** 缴费金额 */
			@JSONField(name = "amount")
			private String amount;
			/** 收款方账号 */
			@JSONField(name = "receiveAccount")
			private String receiveAccount;
			/** 分账交易模板号 */
			@JSONField(name = "splitAccTemplate")
			private String splitAccTemplate;

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

			public String getBillNo() {
				return billNo;
			}

			@JSONField(name = "billNo")
			public void setBillNo(String billNo) {
				this.billNo = billNo;
			}

			public String getAgentSignNo() {
				return agentSignNo;
			}

			@JSONField(name = "agentSignNo")
			public void setAgentSignNo(String agentSignNo) {
				this.agentSignNo = agentSignNo;
			}

			public String getAmount() {
				return amount;
			}

			@JSONField(name = "amount")
			public void setAmount(String amount) {
				this.amount = amount;
			}

			public String getReceiveAccount() {
				return receiveAccount;
			}

			@JSONField(name = "receiveAccount")
			public void setReceiveAccount(String receiveAccount) {
				this.receiveAccount = receiveAccount;
			}

			public String getSplitAccTemplate() {
				return splitAccTemplate;
			}

			@JSONField(name = "splitAccTemplate")
			public void setSplitAccTemplate(String splitAccTemplate) {
				this.splitAccTemplate = splitAccTemplate;
			}

			@Override
			public String toString() {
				return "Info [agentSignNo=" + agentSignNo + ", epayCode="
						+ epayCode + ", merchantId=" + merchantId + ", input1="
						+ input1 + ", input2=" + input2 + ", input3=" + input3
						+ ", input4=" + input4 + ", input5=" + input5 + "]";

			}

		}// end Info
	}// end Message
}
