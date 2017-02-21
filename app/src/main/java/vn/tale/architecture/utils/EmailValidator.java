package vn.tale.architecture.utils;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class EmailValidator {

  public boolean isValid(CharSequence email) {
    return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }
}
