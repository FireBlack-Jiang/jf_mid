package merchant.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  @author lenovo
 * 
 *  
 * 
 */
public class PackageInfo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codEpay;

    private String codPackage;

    private String typPackage;

    private String namPackage;

    private BigDecimal amtPackage;

    private BigDecimal minNum;

    private BigDecimal maxNum;

    private String txtPackage;

    public String getCodEpay() {
        return codEpay;
    }

    public void setCodEpay(String codEpay) {
        this.codEpay = codEpay == null ? null : codEpay.trim();
    }

    public String getCodPackage() {
        return codPackage;
    }

    public void setCodPackage(String codPackage) {
        this.codPackage = codPackage == null ? null : codPackage.trim();
    }

    public String getTypPackage() {
        return typPackage;
    }

    public void setTypPackage(String typPackage) {
        this.typPackage = typPackage == null ? null : typPackage.trim();
    }

    public String getNamPackage() {
        return namPackage;
    }

    public void setNamPackage(String namPackage) {
        this.namPackage = namPackage == null ? null : namPackage.trim();
    }

    public BigDecimal getAmtPackage() {
        return amtPackage;
    }

    public void setAmtPackage(BigDecimal amtPackage) {
        this.amtPackage = amtPackage;
    }

    public BigDecimal getMinNum() {
        return minNum;
    }

    public void setMinNum(BigDecimal minNum) {
        this.minNum = minNum;
    }

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public String getTxtPackage() {
        return txtPackage;
    }

    public void setTxtPackage(String txtPackage) {
        this.txtPackage = txtPackage == null ? null : txtPackage.trim();
    }
}