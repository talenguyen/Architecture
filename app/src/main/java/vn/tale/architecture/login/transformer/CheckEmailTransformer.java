package vn.tale.architecture.login.transformer;

import io.reactivex.Observable;
import timber.log.Timber;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Function0;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.common.mvvm.Transformer;
import vn.tale.architecture.login.LoginUiState;
import vn.tale.architecture.login.action.CheckEmailAction;
import vn.tale.architecture.login.result.CheckEmailResult;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class CheckEmailTransformer implements Transformer<LoginUiState> {

  private EmailValidator emailValidator;

  public CheckEmailTransformer(EmailValidator emailValidator) {
    this.emailValidator = emailValidator;
  }

  @Override public Observable<Result> transform(Observable<Action> action$,
      Function0<LoginUiState> getState) {
    return action$
        .ofType(CheckEmailAction.class)
        .skip(1)
        .switchMap(action -> emailValidator.checkEmail(action.email)
            .map(ignored -> CheckEmailResult.SUCCESS)
            .onErrorReturn(CheckEmailResult::error)
            .doOnNext(result -> Timber.d("result => %s", result))
        );
  }
}
