package vn.tale.architecture.login;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.login.action.SubmitAction;
import vn.tale.architecture.login.result.SubmitResult;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class SubmitTransformer implements ObservableTransformer<Action, Result> {

  private final UserModel userModel;

  public SubmitTransformer(UserModel userModel) {
    this.userModel = userModel;
  }

  @Override public ObservableSource<Result> apply(Observable<Action> actions) {
    return actions.ofType(SubmitAction.class)
        .flatMap(action -> userModel.login(action.email, action.password)
            .toObservable()
            .map(user -> SubmitResult.SUCCESS)
            .onErrorReturn(SubmitResult::error)
            .subscribeOn(Schedulers.io())
            .startWith(SubmitResult.IN_FLIGHT));
  }
}
