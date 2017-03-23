package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.login.action.CheckEmailAction;
import vn.tale.architecture.login.result.CheckEmailResult;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class CheckEmailTransformer implements ObservableTransformer<Action, Result> {

  private EmailValidator emailValidator;

  public CheckEmailTransformer(EmailValidator emailValidator) {
    this.emailValidator = emailValidator;
  }

  @Override public ObservableSource<Result> apply(Observable<Action> actions) {
    return actions
        .ofType(CheckEmailAction.class)
        .skip(1)
        .switchMap(action -> emailValidator.checkEmail(action.email)
            .map(ignored -> CheckEmailResult.SUCCESS)
            .onErrorReturn(CheckEmailResult::error)
            .startWith(CheckEmailResult.IN_FLIGHT));
  }
}
