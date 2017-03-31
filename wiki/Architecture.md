# Architecture

## RVVM
![RVVM](./Rvvm.png "RVVM")

## Redux

### Store
- Central, brain of the Model. It’s a Delegator 
- Ask Effects to get the Result then give the Result to Reducer to update state.

~~~java
class Store<State> { 
  void dispatch(Action action) {
    ...
  }
  Observable<State> state$() {
    ...
  }
}
~~~

### Effect
- Call external services e.g. database, api... to get result

### Reducer
- Pure function

## ViewModel
Simply map **State** to **UiModel**.

e.g. [LoginViewModel.java](../app/src/main/java/vn/tale/architecture/login/LoginViewModel.java)

~~~java
public class LoginViewModel {

  private final Observable<LoginState> state$;

  public LoginViewModel(Observable<LoginState> state$) {
    this.state$ = state$;
  }

  public Observable<LoginState> idle() {
    return state$
        .filter(state -> state.equals(LoginState.idle()));
  }

  public Observable<LoginState> loading() {
    return state$
        .filter(state -> state.inProgress);
  }

  public Observable<LoginState> success() {
    return state$
        .filter(state -> state.success);
  }

  public Observable<Throwable> error() {
    return state$
        .filter(state -> state.error != null)
        .map(state -> state.error);
  }
}
~~~