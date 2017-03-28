package vn.tale.architecture.login;

/**
 * Created by Giang Nguyen on 3/21/17.
 */
public final class LoginUiState {

  public final boolean inProgress;

  public final boolean success;

  public final Throwable error;

  private LoginUiState(boolean inProgress, boolean success, Throwable error) {
    this.inProgress = inProgress;
    this.success = success;
    this.error = error;
  }

  public static LoginUiState idle() {
    return new LoginUiState(false, false, null);
  }

  public static LoginUiState inProgress() {
    return new LoginUiState(true, false, null);
  }

  public static LoginUiState success() {
    return new LoginUiState(false, true, null);
  }

  public static LoginUiState error(Throwable throwable) {
    return new LoginUiState(false, false, throwable);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LoginUiState that = (LoginUiState) o;

    if (inProgress != that.inProgress) return false;
    if (success != that.success) return false;
    return error != null ? error.equals(that.error) : that.error == null;
  }

  @Override public int hashCode() {
    int result = (inProgress ? 1 : 0);
    result = 31 * result + (success ? 1 : 0);
    result = 31 * result + (error != null ? error.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "LoginUiModel{" +
        "inProgress=" + inProgress +
        ", success=" + success +
        ", error=" + error +
        '}';
  }
}
