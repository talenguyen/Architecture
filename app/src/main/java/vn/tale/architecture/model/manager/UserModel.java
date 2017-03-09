package vn.tale.architecture.model.manager;

import android.support.v4.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.exeption.UserNotFoundException;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class UserModel {

  public static final User ANNOYMOUS = User.user("annoymous@tale.vn", "annoymous");

  private final BehaviorSubject<User> userSubject = BehaviorSubject.createDefault(ANNOYMOUS);

  public Observable<User> user() {
    return userSubject;
  }

  public Single<User> login(final String email, final String password) {
    final Pair<String, String> authInfo = new Pair<>(email, password);
    return Single.fromCallable(() -> {
      Thread.sleep(500);
      if (MockManager.USER_MAP.containsKey(authInfo)) {
        return MockManager.USER_MAP.get(authInfo);
      }
      throw new UserNotFoundException("user name & password is not matched");
    }).doOnEvent((user, throwable) -> {
      if (user != null) {
        userSubject.onNext(user);
      }
    });
  }

  public Completable logout() {
    return Completable.fromAction(() -> userSubject.onNext(ANNOYMOUS));
  }
}
