package merchant.utils;

/**
 *  @author lenovo
 * 
 *  
 * 
 */
public final class StringUtil {

    /**
     * 验证字符数组是否为空
     * 
     * @param string
     * @return
     */
    public static boolean isEmpty(String[] string) {
        return string == null || string.length == 0;
    }


    /**
     * 验证字符串是否为空
     * 
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string.trim()) || "null".equals(string.trim())) {
            return true;
        }
        return false;
    }
    
    /**
     * 随机获取字符串数组中的任意一个字符串
     */
    public static String RandomErrCod(String[] codArr){
    	int random_index=Integer.parseInt(String.valueOf(Math.random()*codArr.length));
    	return codArr[random_index];
    }
    
}
