package vn.tale.architecture.login.result;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class CheckEmailResult implements Result {

  public static final CheckEmailResult SUCCESS = new CheckEmailResult(null);
  public static final CheckEmailResult IN_FLIGHT = new CheckEmailResult(null);

  public final Throwable error;

  private CheckEmailResult(Throwable throwable) {
    error = throwable;
  }

  public static CheckEmailResult error(Throwable error) {
    return new CheckEmailResult(error);
  }

  @Override public Throwable error() {
    return error;
  }
}
