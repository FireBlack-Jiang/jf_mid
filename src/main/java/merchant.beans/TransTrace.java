package merchant.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 缴费记录对象
 */
public class TransTrace implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String numTransSeq;

    private String codEpay;

    private String idMerchant;

    private String idUser;

    private String namEpay;

    private String namMerchant;

    private String status;

    private BigDecimal amtEpay;

    private String indEpayChannel;

    private String dateEpay;

	private String timeEpay;

    private String timeResponse;

    private String numEpayBill;

    private String namBill;

    private String typPay;

    private String accPay;

    private String numPaySeq;

    private String indPayChannel;

    private String numReqSeq;

    private String namInput1;

    private String input1;

    private String namInput2;

    private String input2;

    private String namInput3;

    private String input3;

    private String namInput4;

    private String input4;

    private String namInput5;

    private String input5;
    
    private String numBatchBill;
    
    private String numRefundTrace;
    
    private String reserve2;
    
    private String numPhone;
    
    private String idOpen;
    
    private String accReceive;//商户收款账号(分账模板号同时存在时，优先使用分账模板号)
    
    private String tmplSplitAcc;//分账模板号

    private String idReceiveMerchant;//收款商户号

    private String namReceiveAcc;//收款账户名称

    private String dateAccounting;//会计日期

    private String typClear;//清算类型

    public String getNumTransSeq() {
        return numTransSeq;
    }

    public void setNumTransSeq(String numTransSeq) {
        this.numTransSeq = numTransSeq == null ? null : numTransSeq.trim();
    }

    public String getCodEpay() {
        return codEpay;
    }

    public void setCodEpay(String codEpay) {
        this.codEpay = codEpay == null ? null : codEpay.trim();
    }

    public String getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(String idMerchant) {
        this.idMerchant = idMerchant == null ? null : idMerchant.trim();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser == null ? null : idUser.trim();
    }

    public String getNamEpay() {
        return namEpay;
    }

    public void setNamEpay(String namEpay) {
        this.namEpay = namEpay == null ? null : namEpay.trim();
    }

    public String getNamMerchant() {
        return namMerchant;
    }

    public void setNamMerchant(String namMerchant) {
        this.namMerchant = namMerchant == null ? null : namMerchant.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getAmtEpay() {
        return amtEpay;
    }

    public void setAmtEpay(BigDecimal amtEpay) {
        this.amtEpay = amtEpay;
    }

    public String getIndEpayChannel() {
        return indEpayChannel;
    }

    public void setIndEpayChannel(String indEpayChannel) {
        this.indEpayChannel = indEpayChannel == null ? null : indEpayChannel.trim();
    }

    /**
	 * @return the dateEpay
	 */
	public String getDateEpay() {
		return dateEpay;
	}

	/**
	 * @param dateEpay the dateEpay to set
	 */
	public void setDateEpay(String dateEpay) {
		this.dateEpay = dateEpay == null ? null : dateEpay.trim();
	}
    
    public String getTimeEpay() {
        return timeEpay;
    }

    public void setTimeEpay(String timeEpay) {
        this.timeEpay = timeEpay == null ? null : timeEpay.trim();
    }

    public String getTimeResponse() {
        return timeResponse;
    }

    public void setTimeResponse(String timeResponse) {
        this.timeResponse = timeResponse == null ? null : timeResponse.trim();
    }

    public String getNumEpayBill() {
        return numEpayBill;
    }

    public void setNumEpayBill(String numEpayBill) {
        this.numEpayBill = numEpayBill == null ? null : numEpayBill.trim();
    }

    public String getNamBill() {
        return namBill;
    }

    public void setNamBill(String namBill) {
        this.namBill = namBill == null ? null : namBill.trim();
    }

    public String getTypPay() {
        return typPay;
    }

    public void setTypPay(String typPay) {
        this.typPay = typPay == null ? null : typPay.trim();
    }

    public String getAccPay() {
        return accPay;
    }

    public void setAccPay(String accPay) {
        this.accPay = accPay == null ? null : accPay.trim();
    }

    public String getNumPaySeq() {
        return numPaySeq;
    }

    public void setNumPaySeq(String numPaySeq) {
        this.numPaySeq = numPaySeq == null ? null : numPaySeq.trim();
    }

    public String getIndPayChannel() {
        return indPayChannel;
    }

    public void setIndPayChannel(String indPayChannel) {
        this.indPayChannel = indPayChannel == null ? null : indPayChannel.trim();
    }

    public String getNumReqSeq() {
        return numReqSeq;
    }

    public void setNumReqSeq(String numReqSeq) {
        this.numReqSeq = numReqSeq == null ? null : numReqSeq.trim();
    }

    public String getNamInput1() {
        return namInput1;
    }

    public void setNamInput1(String namInput1) {
        this.namInput1 = namInput1 == null ? null : namInput1.trim();
    }

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1 == null ? null : input1.trim();
    }

    public String getNamInput2() {
        return namInput2;
    }

    public void setNamInput2(String namInput2) {
        this.namInput2 = namInput2 == null ? null : namInput2.trim();
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2 == null ? null : input2.trim();
    }

    public String getNamInput3() {
        return namInput3;
    }

    public void setNamInput3(String namInput3) {
        this.namInput3 = namInput3 == null ? null : namInput3.trim();
    }

    public String getInput3() {
        return input3;
    }

    public void setInput3(String input3) {
        this.input3 = input3 == null ? null : input3.trim();
    }

    public String getNamInput4() {
        return namInput4;
    }

    public void setNamInput4(String namInput4) {
        this.namInput4 = namInput4 == null ? null : namInput4.trim();
    }

    public String getInput4() {
        return input4;
    }

    public void setInput4(String input4) {
        this.input4 = input4 == null ? null : input4.trim();
    }

    public String getNamInput5() {
        return namInput5;
    }

    public void setNamInput5(String namInput5) {
        this.namInput5 = namInput5 == null ? null : namInput5.trim();
    }

    public String getInput5() {
        return input5;
    }

    public void setInput5(String input5) {
        this.input5 = input5 == null ? null : input5.trim();
    }
    
	public String getNumBatchBill() {
		return numBatchBill;
	}

	public void setNumBatchBill(String numBatchBill) {
		this.numBatchBill = numBatchBill;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	
	public String getNumPhone() {
		return numPhone;
	}

	public void setNumPhone(String numPhone) {
		this.numPhone = numPhone;
	}

	public String getIdOpen() {
		return idOpen;
	}

	public void setIdOpen(String idOpen) {
		this.idOpen = idOpen;
	}
	
	public String getAccReceive() {
		return accReceive;
	}

	public void setAccReceive(String accReceive) {
		this.accReceive = accReceive == null ? null : accReceive.trim();
	}

	public String getTmplSplitAcc() {
		return tmplSplitAcc;
	}

	public void setTmplSplitAcc(String tmplSplitAcc) {
		this.tmplSplitAcc = tmplSplitAcc == null ? null : tmplSplitAcc.trim();
	}

    public String getIdReceiveMerchant() {
        return idReceiveMerchant;
    }

    public void setIdReceiveMerchant(String idReceiveMerchant) {
        this.idReceiveMerchant = idReceiveMerchant == null ? null : idReceiveMerchant.trim();
    }

    public String getNamReceiveAcc() {
        return namReceiveAcc;
    }

    public void setNamReceiveAcc(String namReceiveAcc) {
        this.namReceiveAcc = namReceiveAcc == null ? null : namReceiveAcc.trim();
    }

    public String getDateAccounting() {
        return dateAccounting;
    }

    public void setDateAccounting(String dateAccounting) {
        this.dateAccounting = dateAccounting == null ? null : dateAccounting.trim();
    }

    public String getTypClear() {
        return typClear;
    }

    public void setTypClear(String clearType) {
        this.typClear = clearType;
    }

    /**
	 * 返回的内容
	 * 缴费中心流水号|交易日期|交易时间|支付订单号|
	 * 银行返回交易流水号|缴费项目编号|缴费项目名称|
	 * 商户代码|商户名称|用户ID|交易金额|订单状态|
	 * 缴费用户输入1|缴费用户输入2|缴费用户输入3|缴费用户输入4|缴费用户输入5|
     * 收款商户号|收款帐号|分账模板号|付款卡号|
	 * @param split 分隔符
	 * @return
	 */
	public String toString(String split){
    	return String.format("%32s" +split+ "%8s" +split+ "%6s" +split+ "%32s" +split 
    			+ "%32s" +split+ "%32s" +split+ "%60s" +split
    			+ "%32s" +split+ "%60s" +split+ "%16s" +split+ "%19.2f" + split+ "%1s" + split
    			+ "%60s" +split+ "%60s" +split+ "%60s" +split+ "%60s" +split+ "%60s" +split
                + "%32s" +split+ "%32s" +split+ "%32s" +split+ "%32s" +split+ "%6s" +split,
    			(numTransSeq == null ? "" : numTransSeq),
    			(dateEpay  == null ? "" : dateEpay),
    			(timeEpay == null ? "" : timeEpay),
    			(numEpayBill == null ? "" : numEpayBill),
    			(numPaySeq == null ? "" : numPaySeq),
    			(codEpay == null ? "" : codEpay),
    			(namEpay == null ? "" : namEpay),
    			(idMerchant == null ? "" : idMerchant),
    			(namMerchant == null ? "" : namMerchant),
    			(idUser == null ? "" : idUser),
    			(amtEpay == null ? BigDecimal.ZERO : amtEpay.setScale(2, RoundingMode.DOWN)),
    			(status == null ? "" : status),
    			(input1 == null ? "" : input1),
    			(input2 == null ? "" : input2),
    			(input3 == null ? "" : input3),
    			(input4 == null ? "" : input4),
    			(input5 == null ? "" : input5),
                (idReceiveMerchant == null ? "" : idReceiveMerchant),
                (accReceive == null ? "" : accReceive),
                (tmplSplitAcc == null ? "" : tmplSplitAcc),
                (accPay == null ? "" : accPay),
                (dateAccounting == null ? "" : dateAccounting)
    			).trim().replace(" ", "");
    }
	
	/**
	 * 返回的内容
	 * 
     * <br>【新版缴费中心流水明细】
     * <br>交易日期|交易时间|缴费中心流水号|商E付支付订单号|
     * <br>主机交易流水号|缴费项目编号|缴费项目名称|
     * <br>商户代码|商户名称|用户ID|支付金额|缴费状态|
     * <br>缴费用户输入1|缴费用户输入2|缴费用户输入3|缴费用户输入4|缴费用户输入5|
     * <br>收款商户号|收款账号|分账模板号|付款账号|
     * <br>会计日期|缴费渠道|缴费中心退款流水号|商E付退款订单号|缴费退款金额|缴费退款状态|清算模式|
     * 
	 * @param split 分隔符
	 * @return
	 */
	public String toStringV2(String split){
    	return String.format(
    			  "%8s"  +split+ "%6s"  +split+ "%32s" +split+ "%32s" +split 
    			+ "%32s" +split+ "%32s" +split+ "%60s" +split
    			+ "%32s" +split+ "%60s" +split+ "%16s" +split+ "%19.2f" + split+ "%1s" + split
    			+ "%60s" +split+ "%60s" +split+ "%60s" +split+ "%60s" +split+ "%60s" +split
                + "%32s" +split+ "%32s" +split+ "%32s" +split+ "%32s" +split
                + "%8s"  +split+ "%8s"  +split+ "%32s" +split+ "%32s" +split+ "%19.2f" +split+ "%16s" +split+ "%16s" +split,
    			(dateEpay  == null ? "" : dateEpay),       //交易日期
    			(timeEpay == null ? "" : timeEpay),        //交易时间
    			(numTransSeq == null ? "" : numTransSeq),  //缴费中心流水号
    			(numEpayBill == null ? "" : numEpayBill),  //商E付支付订单号
    			(numPaySeq == null ? "" : numPaySeq),      //主机交易流水号
    			(codEpay == null ? "" : codEpay),          //缴费项目编号
    			(namEpay == null ? "" : namEpay),          //缴费项目名称
    			(idMerchant == null ? "" : idMerchant),    //商户代码
    			(namMerchant == null ? "" : namMerchant),  //商户名称
    			(idUser == null ? "" : idUser),            //用户ID
    			(amtEpay == null ? BigDecimal.ZERO : amtEpay.setScale(2, RoundingMode.DOWN)), //支付金额
    			(status == null ? "" : status),  //缴费状态
    			(input1 == null ? "" : input1),  //缴费用户输入1
    			(input2 == null ? "" : input2),  //缴费用户输入2
    			(input3 == null ? "" : input3),  //缴费用户输入3
    			(input4 == null ? "" : input4),  //缴费用户输入4
    			(input5 == null ? "" : input5),  //缴费用户输入5
                (idReceiveMerchant == null ? "" : idReceiveMerchant), //收款商户号
                (accReceive == null ? "" : accReceive),          //收款账号
                (tmplSplitAcc == null ? "" : tmplSplitAcc),      //分账模板号
                (accPay == null ? "" : accPay),                  //付款账号
                (dateAccounting == null ? "" : dateAccounting),  //会计日期
                (indEpayChannel == null ? "" : indEpayChannel),  //缴费渠道
                (""),  //缴费中心退款流水号  numRefundTrace  没有送退款流水前暂时全部送空  ***********?
                (""),  //商E付退款订单号  numRefundTrace     没有送退款流水前暂时全部送空  ***********?
                (BigDecimal.ZERO),  //缴费退款金额                    没有送退款流水前暂时全部送0.00**********?
                (""),   //缴费退款状态                                            没有送退款流水前暂时全部送空   ***********?
                (typClear == null ? "" : typClear)  //清算模式
    			).trim().replace(" ", "");
    }
	
	/**
	 * 通过index和value赋值namInput，index从1开始，小于等于5
	 * 
	 * @param i
	 * @param value
	 */
	public void setNameInputWithIndex(int i ,String value){
		switch (i) {
			case 1:
				setNamInput1(value);
				break;
			case 2:
				setNamInput2(value);
				break;
			case 3:
				setNamInput3(value);
				break;
			case 4:
				setNamInput4(value);
				break;
			case 5:
				setNamInput5(value);
				break;
			default:
				break;
		}
	}
	
	/**
	 * 通过index和value赋值namInput，index从1开始，小于等于5
	 * 
	 * @param i
	 * @param value
	 */
	public void setInputWithIndex(int i ,String value){
		switch (i) {
			case 1:
				setInput1(value);
				break;
			case 2:
				setInput2(value);
				break;
			case 3:
				setInput3(value);
				break;
			case 4:
				setInput4(value);
				break;
			case 5:
				setInput5(value);
				break;
			default:
				break;
		}
	}

	/**
	 * 通过index和value赋值namInput，index从1开始，小于等于5
	 * 
	 * @param i
	 */
	public String getInputWithIndex(int i){
		switch (i) {
			case 1:
				return getInput1();
			case 2:
				return getInput2();
			case 3:
				return getInput3();
			case 4:
				return getInput4();
			case 5:
				return getInput5();			
			default:
				return null;
		}
	}
	
	public String getNumRefundTrace() {
		return numRefundTrace;
	}

	public void setNumRefundTrace(String numRefundTrace) {
		this.numRefundTrace = numRefundTrace;
	}

}