package vn.tale.architecture.login;

import io.reactivex.ObservableTransformer;

/**
 * Created by Giang Nguyen on 3/31/17.
 */
public class LoginRenderer {

  public ObservableTransformer<LoginState, LoginState> idle() {
    return state$ -> state$
        .filter(state -> state.equals(LoginState.idle()));
  }

  public ObservableTransformer<LoginState, LoginState> loading() {
    return state$ -> state$
        .filter(state -> state.inProgress);
  }

  public ObservableTransformer<LoginState, LoginState> success() {
    return state$ -> state$
        .filter(state -> state.success);
  }

  public ObservableTransformer<LoginState, Throwable> error() {
    return state$ -> state$
        .filter(state -> state.error != null)
        .map(state -> state.error);
  }
}
