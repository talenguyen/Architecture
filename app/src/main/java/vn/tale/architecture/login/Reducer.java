package vn.tale.architecture.login;

import io.reactivex.functions.BiFunction;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.login.result.CheckEmailResult;
import vn.tale.architecture.login.result.SubmitResult;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class Reducer implements BiFunction<LoginUiModel, Result, LoginUiModel> {

  @Override public LoginUiModel apply(LoginUiModel loginUiModel, Result result) {
    if (result == SubmitResult.IN_FLIGHT) {
      return LoginUiModel.inProgress();
    } else if (result == CheckEmailResult.SUCCESS) {
      return LoginUiModel.idle();
    } else if (result == SubmitResult.SUCCESS) {
      return LoginUiModel.success();
    }
    final Throwable error;
    if (result instanceof SubmitResult) {
      error = ((SubmitResult) result).error();
    } else if (result instanceof CheckEmailResult) {
      error = ((CheckEmailResult) result).error();
    } else {
      error = new IllegalArgumentException("unknown result " + result);
    }
    return LoginUiModel.error(error);
  }
}
