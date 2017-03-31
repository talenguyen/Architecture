package vn.tale.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.reduxer.Store;
import vn.tale.architecture.login.effect.CheckEmailEffect;
import vn.tale.architecture.login.effect.SubmitEffect;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class LoginModule {

  @Provides LoginRenderer provideLoginRenderer() {
    return new LoginRenderer();
  }
  @ActivityScope
  @Provides
  Store<LoginState> provideLoginStore(UserModel userModel,
      EmailValidator emailValidator) {
    return Store.<LoginState>builder()
        .initialState(LoginState.idle())
        .reducer(new LoginReducer())
        .transformers(
            new CheckEmailEffect(emailValidator),
            new SubmitEffect(userModel)
        )
        .make();
  }
}
