package vn.tale.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.SchedulerSingleTransformer;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class LoginModule {

  @Provides
  LoginPresenter provideLoginPresenter(UserModel userModel, EmailValidator emailValidator,
      SchedulerSingleTransformer schedulerSingleTransformer) {
    return new LoginPresenter(userModel, emailValidator, schedulerSingleTransformer);
  }
}
