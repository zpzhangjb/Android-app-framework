package zjb.hyl.framework.net;

import java.util.List;

import zjb.hyl.framework.BaseActivity;

public class Response<T> {
	public static final int RESULT_OK = 200;
	public static final int RESULT_NUll = 401;
	public static final int RESULT_NET_ERROR = 500;

	public T result;
	public int status = RESULT_NUll;
	public List<T> list;
	public BaseActivity clazz;
	public int taskid;

	public Response() {
		super();
	}

	public Response(int status, List<T> list) {
		super();
		this.status = status;
		this.list = list;
	}

	public Response(T result, int status) {
		super();
		this.result = result;
		this.status = status;
	}
}
