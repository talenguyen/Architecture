package vn.tale.architecture.repos.my;

import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.List;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.User;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tale.architecture.model.manager.UserModel;

/**
 * Created by Giang Nguyen on 3/3/17.
 */

public class MyReposViewModel {

  private final RepoModel repoModel;
  private final UserModel userModel;
  private final SchedulerObservableTransformer schedulerObservableTransformer;

  private Subject<MyReposState> state = BehaviorSubject.create();
  private Disposable disposable;
  private Observable<List<Repo>> getRepos;

  public MyReposViewModel(UserModel userModel, RepoModel repoModel,
      SchedulerObservableTransformer schedulerObservableTransformer) {
    this.userModel = userModel;
    this.repoModel = repoModel;
    this.schedulerObservableTransformer = schedulerObservableTransformer;
  }

  public Subject<MyReposState> getState() {
    return state;
  }

  public void loadRepos() {
    if (getRepos == null) {
      getRepos = userModel.user()
          .flatMapSingle(new Function<User, SingleSource<List<Repo>>>() {
            @Override public SingleSource<List<Repo>> apply(User user) throws Exception {
              return repoModel.getUserRepos(user.getEmail());
            }
          })
          .cache();
    }
    state.onNext(MyReposState.builder().loading(true).build());
    disposable = getRepos
        .compose(schedulerObservableTransformer.<List<Repo>>transformer())
        .subscribe(new Consumer<List<Repo>>() {
          @Override public void accept(List<Repo> repos) throws Exception {
            state.onNext(MyReposState.builder().items(repos).build());
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            state.onNext(MyReposState.builder().error(throwable).build());
          }
        });
  }

  public void unbind() {
    if (disposable != null) {
      disposable.dispose();
    }
  }
}
