package merchant.beans;

import java.io.Serializable;

/**
 *  @author lenovo
 * 
 *  
 * 
 */
public class FeeDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sCpt;
	private String sVal;
	public String getsCpt() {
		return sCpt;
	}
	public void setsCpt(String sCpt) {
		this.sCpt = sCpt;
	}
	public String getsVal() {
		return sVal;
	}
	public void setsVal(String sVal) {
		this.sVal = sVal;
	}
	
}
