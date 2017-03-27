package vn.tale.architecture.login.transformer;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.mvvm.Action;
import vn.tale.architecture.common.mvvm.Result;
import vn.tale.architecture.common.mvvm.Transformer;
import vn.tale.architecture.login.LoginUiModel;
import vn.tale.architecture.login.action.SubmitAction;
import vn.tale.architecture.login.result.SubmitResult;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 3/23/17.
 */

public class SubmitTransformer implements Transformer<LoginUiModel> {

  private final UserModel userModel;

  public SubmitTransformer(UserModel userModel) {
    this.userModel = userModel;
  }

  @Override
  public Observable<Result> transform(Observable<Action> action$, LoginUiModel loginUiModel) {
    return action$.ofType(SubmitAction.class)
        .flatMap(action -> userModel.login(action.email, action.password)
            .toObservable()
            .map(user -> SubmitResult.SUCCESS)
            .onErrorReturn(SubmitResult::error)
            .subscribeOn(Schedulers.io())
            .startWith(SubmitResult.IN_FLIGHT));
  }
}
