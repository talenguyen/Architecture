package vn.tale.architecture.login.effect;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.reduxer.Action;
import vn.tale.architecture.common.reduxer.Function0;
import vn.tale.architecture.common.reduxer.Result;
import vn.tale.architecture.common.reduxer.Effect;
import vn.tale.architecture.login.LoginState;
import vn.tale.architecture.login.action.SubmitAction;
import vn.tale.architecture.login.result.SubmitResult;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class SubmitEffect implements Effect<LoginState> {

  private final UserModel userModel;

  public SubmitEffect(UserModel userModel) {
    this.userModel = userModel;
  }

  @Override public Observable<Result> apply(Observable<Action> action$,
      Function0<LoginState> getState) {
    return action$.ofType(SubmitAction.class)
        .flatMap(action -> userModel.login(action.email, action.password)
            .toObservable()
            .map(user -> SubmitResult.SUCCESS)
            .onErrorReturn(SubmitResult::error)
            .subscribeOn(Schedulers.io())
            .startWith(SubmitResult.IN_FLIGHT));
  }
}
