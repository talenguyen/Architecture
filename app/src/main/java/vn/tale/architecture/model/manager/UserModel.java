package vn.tale.architecture.model.manager;

import android.support.v4.util.Pair;
import io.reactivex.Single;
import java.util.concurrent.Callable;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.exeption.UserNotFoundException;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class UserModel {

  public Single<User> login(final String email, final String password) {
    final Pair<String, String> authInfo = new Pair<>(email, password);
    return Single.fromCallable(new Callable<User>() {
      @Override public User call() throws Exception {
        Thread.sleep(500);
        if (MockManager.USER_MAP.containsKey(authInfo)) {
          return MockManager.USER_MAP.get(authInfo);
        }
        throw new UserNotFoundException("user name & password is not matched");
      }
    });
  }
}
