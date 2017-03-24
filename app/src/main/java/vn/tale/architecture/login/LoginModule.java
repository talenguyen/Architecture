package vn.tale.architecture.login;

import dagger.Module;
import dagger.Provides;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.common.mvvm.Builder;
import vn.tale.architecture.common.mvvm.ViewModel;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/27/17.
 */
@Module
public class LoginModule {

  @Provides
  ViewModel<LoginUiModel> provideLoginViewModel(UserModel userModel,
      EmailValidator emailValidator) {
    return new Builder<LoginUiModel>()
        .initialState(LoginUiModel.idle())
        .reducer(new Reducer())
        .transformers(
            new CheckEmailTransformer(emailValidator),
            new SubmitTransformer(userModel)
        )
        .make();
  }
}
