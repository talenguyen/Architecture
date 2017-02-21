package vn.tale.architecture.model.manager;

import android.support.v4.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.subjects.BehaviorSubject;
import java.util.concurrent.Callable;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.exeption.UserNotFoundException;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class UserModel {

  public static final User ANNOYMOUS = new User("annoymous@tale.vn", "annoymous");

  private BehaviorSubject<User> userSubject = BehaviorSubject.createDefault(ANNOYMOUS);

  public Observable<User> user() {
    return userSubject;
  }

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
    }).doOnEvent(new BiConsumer<User, Throwable>() {
      @Override public void accept(User user, Throwable throwable) throws Exception {
        if (user != null) {
          userSubject.onNext(user);
        }
      }
    });
  }

  public Completable logout() {
    return Completable.fromAction(new Action() {
      @Override public void run() throws Exception {
        userSubject.onNext(ANNOYMOUS);
      }
    });
  }
}
