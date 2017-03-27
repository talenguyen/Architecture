package vn.tale.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.mvvm.ViewModel;
import vn.tale.architecture.login.transformer.CheckEmailTransformer;
import vn.tale.architecture.login.transformer.SubmitTransformer;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class LoginModule {

  @Provides
  ViewModel<LoginUiModel> provideLoginViewModel(UserModel userModel,
      EmailValidator emailValidator) {
    return ViewModel.<LoginUiModel>builder()
        .initialState(LoginUiModel.idle())
        .reducer(new LoginReducer())
        .transformers(
            new CheckEmailTransformer(emailValidator),
            new SubmitTransformer(userModel)
        )
        .make();
  }
}
