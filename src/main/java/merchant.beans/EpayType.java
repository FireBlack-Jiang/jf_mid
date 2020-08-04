package merchant.beans;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 缴费类别
 */
public class EpayType implements Serializable, Comparable<EpayType>{
   
	private static final long serialVersionUID = 1L;

	private String codEpayType;//缴费类别代码

    private String indTypeLevel;//类别级别

    private String namEpayType;//类别名称

    private String codUpperType;//上级类别代码

    private String namUpperType;//上级类别名称

    private BigDecimal indEpayType;//类别序号

    public String getCodEpayType() {
        return codEpayType;
    }

    public void setCodEpayType(String codEpayType) {
        this.codEpayType = codEpayType == null ? null : codEpayType.trim();
    }

    public String getIndTypeLevel() {
        return indTypeLevel;
    }

    public void setIndTypeLevel(String indTypeLevel) {
        this.indTypeLevel = indTypeLevel == null ? null : indTypeLevel.trim();
    }

    public String getNamEpayType() {
        return namEpayType;
    }

    public void setNamEpayType(String namEpayType) {
        this.namEpayType = namEpayType == null ? null : namEpayType.trim();
    }

    public String getCodUpperType() {
        return codUpperType;
    }

    public void setCodUpperType(String codUpperType) {
        this.codUpperType = codUpperType == null ? null : codUpperType.trim();
    }

    public String getNamUpperType() {
        return namUpperType;
    }

    public void setNamUpperType(String namUpperType) {
        this.namUpperType = namUpperType == null ? null : namUpperType.trim();
    }

    public BigDecimal getIndEpayType() {
        return indEpayType;
    }

    public void setIndEpayType(BigDecimal indEpayType) {
        this.indEpayType = indEpayType;
    }
    public int compareTo(EpayType epayType) {
    	return this.codEpayType.compareTo(epayType.codEpayType);
    }
}