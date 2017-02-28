package vn.tale.architecture.login;

import dagger.Subcomponent;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

  void inject(LoginActivity loginActivity);
}
