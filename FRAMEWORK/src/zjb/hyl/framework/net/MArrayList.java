package zjb.hyl.framework.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author heiyulong
 * 
 */
public class MArrayList extends ArrayList<NameValuePair> {

	private static final long serialVersionUID = 1L;
	private Map<String, NameValuePair> map = new HashMap<String, NameValuePair>();

	@Override
	public boolean add(NameValuePair nameValuePair) {
		map.put(nameValuePair.getName(), nameValuePair);
		return super.add(nameValuePair);
	}

	public String get(String name) {
		if (map.get(name) != null) {
			return map.get(name).getValue();
		} else {
			return "";
		}
	}

	@Override
	public void clear() {
		map.clear();
		super.clear();
	}

	/**
	 * 添加
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean add(String name, String value) {
		BasicNameValuePair pair = new BasicNameValuePair(name, value);
		map.put(name, pair);
		return add(pair);
	}

	/**
	 * 添加
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean add(String name, int value) {
		BasicNameValuePair pair = new BasicNameValuePair(name, String.valueOf(value));
		map.put(name, pair);
		return add(pair);
	}

	public boolean remove(String name) {
		if (map.containsKey(name)) {
			return remove(map.get(name));
		}
		return true;
	}
}
