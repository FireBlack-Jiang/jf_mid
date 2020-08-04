package merchant.beans;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 单笔缴费流水查询响应报文
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ConfirmTraceResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 格式 */
	@JSONField(name = "format")
	private String format;
	/** 消息 */
	@JSONField(name = "message")
	private Message message;
	
	public ConfirmTraceResponse(){
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
			/**  交易序列号 */
			@JSONField(name = "transSeqNum")
			private String transSeqNum;
			/**  交易码  */
			@JSONField(name = "transCode")
			private String transCode;
			
			/** 返回值 */
			@JSONField(name = "returnCode")
			private String returnCode;
			/** 返回提示信息 */
			@JSONField(name = "returnMessage")
			private String returnMessage;
			
			/** 时间戳  */
			@JSONField(name = "timeStamp")
			private String timeStamp;
			/**  交易上行下送标志  */
			@JSONField(name = "transFlag")
			private String transFlag;
			
            public Head() {
				
			}

			@Override
			public String toString() {
				return "Head [transSeqNum=" + transSeqNum + ", transCode="
						+ transCode + ", returnCode=" + returnCode
						+ ", returnMessage=" + returnMessage + ", timeStamp="
						+ timeStamp + ", transFlag=" + transFlag + "]";
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
		
			/** 商户编号*/
			@JSONField(name = "merchantId")
			private String merchantId;
			/** 商户名称*/
			@JSONField(name = "merchantName")
			private String merchantName;
			
			/** 缴费项目*/
			@JSONField(name = "epayCode")
			private String epayCode;
			/** 输入要素1*/
			@JSONField(name = "input1")
			private String input1;
			/** 输入要素2*/
			@JSONField(name = "input2")
			private String input2;
			/** 输入要素3*/
			@JSONField(name = "input3")
			private String input3;
			/** 输入要素4*/
			@JSONField(name = "input4")
			private String input4;
			/** 输入要素5*/
			@JSONField(name = "input5")
			private String input5;
			/** 缴费中心流水号*/
			@JSONField(name = "traceNo")
			private String traceNo;
			/** 账单编号*/
			@JSONField(name = "billNo")
			private String billNo;
			
			/** 缴费金额*/
			@JSONField(name = "amtEpay")
			private String amtEpay;
			/** 优惠金额*/
			@JSONField(name = "amtPreferential")
			private String amtPreferential;
			/** 支付时间*/
			@JSONField(name = "timeEpay")
			private String timeEpay;
			/** 缴费状态*/
			@JSONField(name = "payStatus")
			private String payStatus;
			/** 缴费结果提示信息*/
			@JSONField(name = "payStatusMessage")
			private String payStatusMessage;

			/** 交易渠道  add 2020-01-13*/
			@JSONField(name = "indEpayChannel")
			private String indEpayChannel;

			/** 支付交易码  add 2020-01-13*/
			@JSONField(name = "typPay")
			private String typPay;

			/** 支付卡号（暂时为空）  add 2020-01-13*/
			@JSONField(name = "accPay")
			private String accPay;

			/** 所选套餐名称  add 2020-01-13*/
			@JSONField(name = "optionName")
			private String optionName;

			/** 所选套餐编码  add 2020-01-13*/
			@JSONField(name = "optionCode")
			private String optionCode;

			/** 第三方支付平台商户订单号  add 2020-01-13*/
			@JSONField(name = "numOpenMerchantOrder")
			private String numOpenMerchantOrder;
			
			
			public Info(){
				
			}


			@Override
			public String toString() {
				return "Info{" +
						"merchantId='" + merchantId + '\'' +
						", merchantName='" + merchantName + '\'' +
						", epayCode='" + epayCode + '\'' +
						", input1='" + input1 + '\'' +
						", input2='" + input2 + '\'' +
						", input3='" + input3 + '\'' +
						", input4='" + input4 + '\'' +
						", input5='" + input5 + '\'' +
						", traceNo='" + traceNo + '\'' +
						", billNo='" + billNo + '\'' +
						", amtEpay='" + amtEpay + '\'' +
						", amtPreferential='" + amtPreferential + '\'' +
						", timeEpay='" + timeEpay + '\'' +
						", payStatus='" + payStatus + '\'' +
						", payStatusMessage='" + payStatusMessage + '\'' +
						", indEpayChannel='" + indEpayChannel + '\'' +
						", typPay='" + typPay + '\'' +
						", accPay='" + accPay + '\'' +
						", optionName='" + optionName + '\'' +
						", optionCode='" + optionCode + '\'' +
						", numOpenMerchantOrder='" + numOpenMerchantOrder + '\'' +
						'}';
			}


			public String getMerchantId() {
				return merchantId;
			}

			@JSONField(name = "merchantId")
			public void setMerchantId(String merchantId) {
				this.merchantId = merchantId;
			}
			

			public String getMerchantName() {
				return merchantName;
			}

			@JSONField(name = "merchantName")
			public void setMerchantName(String merchantName) {
				this.merchantName = merchantName;
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


			public String getTraceNo() {
				return traceNo;
			}

			@JSONField(name = "traceNo")
			public void setTraceNo(String traceNo) {
				this.traceNo = traceNo;
			}


			public String getBillNo() {
				return billNo;
			}

			@JSONField(name = "billNo")
			public void setBillNo(String billNo) {
				this.billNo = billNo;
			}


			public String getAmtEpay() {
				return amtEpay;
			}

			@JSONField(name = "amtEpay")
			public void setAmtEpay(String amtEpay) {
				this.amtEpay = amtEpay;
			}


			public String getAmtPreferential() {
				return amtPreferential;
			}

			@JSONField(name = "amtPreferential")
			public void setAmtPreferential(String amtPreferential) {
				this.amtPreferential = amtPreferential;
			}


			public String getTimeEpay() {
				return timeEpay;
			}

			@JSONField(name = "timeEpay")
			public void setTimeEpay(String timeEpay) {
				this.timeEpay = timeEpay;
			}


			public String getPayStatus() {
				return payStatus;
			}

			@JSONField(name = "payStatus")
			public void setPayStatus(String payStatus) {
				this.payStatus = payStatus;
			}


			public String getPayStatusMessage() {
				return payStatusMessage;
			}

			@JSONField(name = "payStatusMessage")
			public void setPayStatusMessage(String payStatusMessage) {
				this.payStatusMessage = payStatusMessage;
			}
			public String getIndEpayChannel() {
				return indEpayChannel;
			}

			@JSONField(name = "indEpayChannel")
			public void setIndEpayChannel(String indEpayChannel) {
				this.indEpayChannel = indEpayChannel;
			}

			public String getTypPay() {
				return typPay;
			}

			@JSONField(name = "typPay")
			public void setTypPay(String typPay) {
				this.typPay = typPay;
			}

			public String getAccPay() {
				return accPay;
			}

			@JSONField(name = "accPay")
			public void setAccPay(String accPay) {
				this.accPay = accPay;
			}

			public String getOptionName() {
				return optionName;
			}

			@JSONField(name = "optionName")
			public void setOptionName(String optionName) {
				this.optionName = optionName;
			}

			public String getOptionCode() {
				return optionCode;
			}

			@JSONField(name = "optionCode")
			public void setOptionCode(String optionCode) {
				this.optionCode = optionCode;
			}

			public String getNumOpenMerchantOrder() {
				return numOpenMerchantOrder;
			}

			@JSONField(name = "numOpenMerchantOrder")
			public void setNumOpenMerchantOrder(String numOpenMerchantOrder) {
				this.numOpenMerchantOrder = numOpenMerchantOrder;
			}

			
		}//end Info
	}// end Message
}
