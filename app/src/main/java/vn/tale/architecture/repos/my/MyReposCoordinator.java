package vn.tale.architecture.repos.my;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import io.reactivex.Observable;
import java.util.List;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.repos.RepoListDelegate;
import vn.tale.architecture.repos.ReposComponent;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public class MyReposCoordinator extends Coordinator implements MyReposView {

  private RepoListDelegate repoListDelegate;
  private ReposComponent reposComponent;
  private MyReposPresenter presenter;

  public MyReposCoordinator(ReposComponent reposComponent) {
    this.reposComponent = reposComponent;
  }

  @Override public void attach(View view) {
    super.attach(view);
    final RecyclerView recyclerView = (RecyclerView) view;
    injectDependencies(recyclerView);

    presenter.attachView(this);
  }

  private void injectDependencies(RecyclerView recyclerView) {
    repoListDelegate = new RepoListDelegate(recyclerView);
    presenter = reposComponent.provideMyReposPresenter();
  }

  @Override public Observable<Object> loadRepos() {
    return Observable.just(new Object());
  }

  @Override public void showLoading() {

  }

  @Override public void showError(Throwable throwable) {

  }

  @Override public void showRepos(List<Repo> repos) {
    repoListDelegate.setItems(repos);
  }
}
