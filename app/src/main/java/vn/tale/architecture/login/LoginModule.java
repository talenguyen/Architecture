package vn.tale.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.reduxer.Store;
import vn.tale.architecture.login.transformer.CheckEmailTransformer;
import vn.tale.architecture.login.transformer.SubmitTransformer;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class LoginModule {

  @ActivityScope
  @Provides
  Store<LoginUiState> provideLoginStore(UserModel userModel,
      EmailValidator emailValidator) {
    return Store.<LoginUiState>builder()
        .initialState(LoginUiState.idle())
        .reducer(new LoginReducer())
        .transformers(
            new CheckEmailTransformer(emailValidator),
            new SubmitTransformer(userModel)
        )
        .make();
  }
}
