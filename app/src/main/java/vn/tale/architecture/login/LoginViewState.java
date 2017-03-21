package vn.tale.architecture.login;

import android.support.annotation.Nullable;

/**
 * Created by Giang Nguyen on 3/21/17.
 */
@com.google.auto.value.AutoValue
public abstract class LoginViewState {

  public static LoginViewState loginState(boolean success, @Nullable Throwable error) {
    return new AutoValue_LoginViewState(success, error);
  }

  public abstract boolean success();

  @Nullable public abstract Throwable error();
}
