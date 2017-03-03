package vn.tale.architecture.repos.pub;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.List;
import javax.inject.Inject;
import vn.tale.architecture.common.pattern.AsyncLoad;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.repos.RepoListDelegate;
import vn.tale.architecture.repos.ReposComponent;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public class PublicReposCoordinator extends Coordinator implements AsyncLoad.View<List<Repo>> {

  private static final String TAG = "PublicReposCoordinator";
  @Inject PublicReposPresenter presenter;
  private RepoListDelegate repoListDelegate;
  private Subject<Object> destroySubject = PublishSubject.create();

  public PublicReposCoordinator(ReposComponent reposComponent) {
    reposComponent.inject(this);
    Log.d(TAG, "PublicReposCoordinator: " + presenter);
  }

  @Override public void attach(View view) {
    super.attach(view);
    final RecyclerView recyclerView = (RecyclerView) view;
    injectDependencies(recyclerView);

    presenter.attachView(this);
  }

  @Override public void detach(View view) {
    super.detach(view);
    destroySubject.onNext(new Object());
  }

  private void injectDependencies(RecyclerView recyclerView) {
    repoListDelegate = new RepoListDelegate(recyclerView);
  }

  @Override public Observable<Object> onLoad() {
    return Observable.just(new Object());
  }

  @Override public Observable<Object> onDestroy() {
    return destroySubject;
  }

  @Override public void showLoading() {
    Log.d(TAG, "showLoading() called");
  }

  @Override public void showContent(List<Repo> content) {
    repoListDelegate.setItems(content);
  }

  @Override public void showError(Throwable throwable) {
    Log.e(TAG, "showError: ", throwable);
  }
}
