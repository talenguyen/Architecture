package vn.tale.architecture.login;

import vn.tale.architecture.AppComponent;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

class LoginComponent {

  private final AppComponent appComponent;

  LoginComponent(AppComponent appComponent) {
    this.appComponent = appComponent;
  }

  LoginPresenter provideLoginPresenter() {
    return new LoginPresenter(appComponent.provideUserModel(), appComponent.emailValidator());
  }
}
