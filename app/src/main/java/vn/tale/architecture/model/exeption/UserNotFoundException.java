package vn.tale.architecture.model.exeption;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
