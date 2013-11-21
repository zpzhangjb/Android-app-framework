package zjb.hyl.framework.exception;

public class NetException extends Exception {
	private static final long serialVersionUID = -5141404472314025412L;
	public static final String TIME_OUT = "time_out";
	public static final String NET_ERROR = "error";

	public NetException() {
		super();
	}

	public NetException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public NetException(String detailMessage) {
		super(detailMessage);
	}

	public NetException(Throwable throwable) {
		super(throwable);
	}
}
