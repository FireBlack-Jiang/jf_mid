package merchant.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json数据转换为list<T>
 *
 */
public class JsonUtil {
	public static<T> List<T> JsonToObj(String json,Class<T[]> clazz){
		T[] arrs = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			arrs = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> list = Arrays.asList(arrs);
		return list;
	}
}
