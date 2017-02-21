package vn.tale.architecture;

import vn.tale.architecture.common.AppRouter;
import vn.tale.architecture.common.EmailValidator;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class AppComponent {

  private UserModel userModel;
  private RepoModel repoModel;
  private AppRouter appRouter;

  public UserModel provideUserModel() {
    if (userModel == null) {
      userModel = new UserModel();
    }
    return userModel;
  }

  public RepoModel provideRepoModel() {
    if (repoModel == null) {
      repoModel = new RepoModel();
    }
    return repoModel;
  }

  public AppRouter provideAppRouter() {
    if (appRouter == null) {
      appRouter = new AppRouter();
    }
    return appRouter;
  }

  public EmailValidator emailValidator() {
    return new EmailValidator();
  }
}
