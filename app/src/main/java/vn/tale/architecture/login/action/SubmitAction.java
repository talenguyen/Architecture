package vn.tale.architecture.login.action;

/**
 * Created by Giang Nguyen on 3/23/17.
 */
public class SubmitAction implements Action {
  public final String email;
  public final String password;

  public SubmitAction(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
