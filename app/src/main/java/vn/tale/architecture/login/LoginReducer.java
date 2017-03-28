package vn.tale.architecture.login;

import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.login.result.CheckEmailResult;
import vn.tale.architecture.login.result.SubmitResult;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class LoginReducer implements vn.tale.architecture.common.mvvm.Reducer<LoginUiState> {

  @Override public LoginUiState apply(LoginUiState loginUiState, Result result) {
    if (result == SubmitResult.IN_FLIGHT) {
      return LoginUiState.inProgress();
    } else if (result == CheckEmailResult.SUCCESS) {
      return LoginUiState.idle();
    } else if (result == SubmitResult.SUCCESS) {
      return LoginUiState.success();
    }
    final Throwable error;
    if (result instanceof SubmitResult) {
      error = ((SubmitResult) result).error();
    } else if (result instanceof CheckEmailResult) {
      error = ((CheckEmailResult) result).error();
    } else {
      return loginUiState;
    }
    return LoginUiState.error(error);
  }
}
