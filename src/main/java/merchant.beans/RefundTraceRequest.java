package merchant.beans;
import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 商户直连系统退款请求报文
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RefundTraceRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "message")
    private Message message;

    public RefundTraceRequest() {
        this.setFormat("json");
        this.setMessage(new Message());
    }

    /**
     * 消息体
     */
    public class Message implements Serializable {
        private static final long serialVersionUID = 1L;

        @JSONField(name = "head")
        private Head head;

        @JSONField(name = "info")
        private Info info;

        public Message() {
            this.setHead(new Head());
            this.setInfo(new Info());
        }

        /**
         * 消息头
         */
        public class Head implements Serializable {
            private static final long serialVersionUID = 1L;

            /**
             * 缴费中心交易序列号
             * BRIDGE前缀+当前17位时间戳timeStamp+商户号merchantId
             */
            @JSONField(name = "transSeqNum")
            private String transSeqNum;

            /**
             * 交易码
             * refundBill
             */
            @JSONField(name = "transCode")
            private String transCode;

            /**
             * 交易上行下送标志
             * 01
             */
            @JSONField(name = "transFlag")
            private String transFlag;

            /**
             * 时间戳
             * yyyyMMddHHmmssSSS
             */
            @JSONField(name = "timeStamp")
            private String timeStamp;
            
            /**
             * 分行4位iGoal码，用来前置分行交易
             * 36家分行每个分行分配一个唯一的4位iGoal码
             */
            @JSONField(name = "branchCode")
            private String branchCode;

            public String getBranchCode() {
				return branchCode;
			}

			public void setBranchCode(String branchCode) {
				this.branchCode = branchCode;
			}

			public String getTransSeqNum() {
                return transSeqNum;
            }

            public void setTransSeqNum(String transSeqNum) {
                this.transSeqNum = transSeqNum;
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

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }
        }

        /**
         * 消息内容
         */
        public class Info implements Serializable {
            private static final long serialVersionUID = 1L;

            /**
             * 商户编号
             */
            @JSONField(name = "merchantId")
            private String merchantId;

            /**
             * 缴费中心流水号
             */
            @JSONField(name = "traceNo")
            private String traceNo;

            /**
             * 退款金额
             */
            @JSONField(name = "amtRefund")
            private String amtRefund;

            public String getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(String merchantId) {
                this.merchantId = merchantId;
            }

            public String getTraceNo() {
                return traceNo;
            }

            public void setTraceNo(String traceNo) {
                this.traceNo = traceNo;
            }

            public String getAmtRefund() {
                return amtRefund;
            }

            public void setAmtRefund(String amtRefund) {
                this.amtRefund = amtRefund;
            }
        }

        public Head getHead() {
            return head;
        }

        public void setHead(Head head) {
            this.head = head;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
