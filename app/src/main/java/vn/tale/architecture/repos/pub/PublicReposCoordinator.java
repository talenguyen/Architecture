package vn.tale.architecture.repos.pub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.repos.RepoListDelegate;
import vn.tale.architecture.repos.ReposComponent;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public class PublicReposCoordinator extends Coordinator implements PublicReposView {

  @Inject PublicReposPresenter presenter;
  private RepoListDelegate repoListDelegate;

  public PublicReposCoordinator(ReposComponent reposComponent) {
    reposComponent.inject(this);
  }

  @Override public void attach(View view) {
    super.attach(view);
    final RecyclerView recyclerView = (RecyclerView) view;
    injectDependencies(recyclerView);

    presenter.attachView(this);
  }

  private void injectDependencies(RecyclerView recyclerView) {
    repoListDelegate = new RepoListDelegate(recyclerView);
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
