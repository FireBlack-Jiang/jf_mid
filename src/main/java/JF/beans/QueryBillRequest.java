package JF.beans;
import com.alibaba.fastjson.annotation.JSONField;

public class QueryBillRequest {
	/** 格式 */
	@JSONField(name = "format")
	private String format="json";

	/** 消息 */
	@JSONField(name = "message")
	private Message message;

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
