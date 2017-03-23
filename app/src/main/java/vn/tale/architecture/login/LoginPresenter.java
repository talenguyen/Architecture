package vn.tale.architecture.login;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.mvp.MvpPresenter;
import vn.tale.architecture.login.action.Action;
import vn.tale.architecture.login.action.CheckEmailAction;
import vn.tale.architecture.login.action.SubmitAction;
import vn.tale.architecture.login.result.CheckEmailResult;
import vn.tale.architecture.login.result.Result;
import vn.tale.architecture.login.result.SubmitResult;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

class LoginPresenter extends MvpPresenter<LoginUiState, LoginView> {
  private final UserModel userModel;
  private final EmailValidator emailValidator;

  LoginPresenter(UserModel userModel, EmailValidator emailValidator) {
    this.userModel = userModel;
    this.emailValidator = emailValidator;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();

    final LoginView view = getView();

    final Observable<SubmitAction> submitActions = view.signInClick()
        .flatMap(ignored -> Observable.zip(
            view.emailChanges(),
            view.passwordChanges(),
            (email, password) -> new SubmitAction(email.toString(), password.toString())));

    final Observable<CheckEmailAction> checkEmailActions = view.emailChanges()
        .map(email -> new CheckEmailAction(email.toString()));

    final Observable<Action> actions = Observable.merge(Arrays.asList(
        submitActions,
        checkEmailActions));

    final LoginUiState initialState = LoginUiState.idle();

    disposeOnStop(actions.compose(result())
        .scan(initialState, reducer())
        .subscribe(state -> setState(state)));
  }

  @NonNull private ObservableTransformer<Action, Result> result() {
    return actions -> actions
        .publish(shared -> Observable.merge(Arrays.asList(
            shared.ofType(SubmitAction.class).compose(submit()),
            shared.ofType(CheckEmailAction.class).compose(checkEmail()))
        ));
  }

  @NonNull private ObservableTransformer<CheckEmailAction, CheckEmailResult> checkEmail() {
    return actions -> actions
        .skip(1)
        .switchMap(action -> emailValidator.checkEmail(action.email)
            .map(ignored -> CheckEmailResult.SUCCESS)
            .onErrorReturn(CheckEmailResult::error)
            .startWith(CheckEmailResult.IN_FLIGHT));
  }

  @NonNull private ObservableTransformer<SubmitAction, SubmitResult> submit() {
    return actions -> actions
        .flatMap(action -> userModel.login(action.email, action.password)
            .toObservable()
            .map(user -> SubmitResult.SUCCESS)
            .onErrorReturn(SubmitResult::error)
            .subscribeOn(Schedulers.io())
            .startWith(SubmitResult.IN_FLIGHT));
  }

  @NonNull private BiFunction<LoginUiState, Result, LoginUiState> reducer() {
    return (loginUiState, result) -> {
      if (result == SubmitResult.IN_FLIGHT) {
        return LoginUiState.inProgress();
      } else if (result == CheckEmailResult.SUCCESS) {
        return LoginUiState.idle();
      } else if (result == SubmitResult.SUCCESS) {
        return LoginUiState.success();
      }
      return LoginUiState.error(result.error());
    };
  }
}
