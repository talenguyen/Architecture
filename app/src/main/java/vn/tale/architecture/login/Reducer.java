package vn.tale.architecture.login;

import io.reactivex.functions.BiFunction;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.login.result.CheckEmailResult;
import vn.tale.architecture.login.result.SubmitResult;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class Reducer implements BiFunction<LoginUiState, Result, LoginUiState> {

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
      error = new IllegalArgumentException("unknown result " + result);
    }
    return LoginUiState.error(error);
  }
}
