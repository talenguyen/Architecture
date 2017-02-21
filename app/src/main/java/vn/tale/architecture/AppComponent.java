package vn.tale.architecture;

import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;
import vn.tale.architecture.common.EmailValidator;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class AppComponent {

  private UserModel userModel;
  private RepoModel repoModel;

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

  public EmailValidator emailValidator() {
    return new EmailValidator();
  }
}
