package JF.beans;


import com.alibaba.fastjson.annotation.JSONField;

public class Message {
	/** 消息头部 */
	@JSONField(name = "head")
	private Head head;
	
	/** 消息体  */
	@JSONField(name = "info")
	private Info info;

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
