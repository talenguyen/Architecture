package vn.tale.architecture.repos.my;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;
import vn.tale.architecture.repos.RepoListDelegate;
import vn.tale.architecture.repos.ReposComponent;

/**
 * Created by Giang Nguyen on 2/26/17.
 */

public class MyReposCoordinator extends Coordinator {

  @Inject MyReposViewModel viewModel;
  private RepoListDelegate repoListDelegate;
  private Disposable disposable;

  public MyReposCoordinator(ReposComponent reposComponent) {
    reposComponent.inject(this);
  }

  @Override public void attach(View view) {
    super.attach(view);
    final RecyclerView recyclerView = (RecyclerView) view;
    injectDependencies(recyclerView);

    startBinding();
    viewModel.loadRepos();
  }

  @Override public void detach(View view) {
    super.detach(view);
    viewModel.unbind();
    if (disposable != null) {
      disposable.dispose();
    }
  }

  private void startBinding() {
    disposable = viewModel.getState()
        .subscribe(myReposState -> {
          if (myReposState.loading()) {
            // TODO: 3/3/17 show loading
          } else if (myReposState.error() == null) {
            repoListDelegate.setItems(myReposState.items());
          } else {
            // TODO: 3/3/17 show error
          }
        });
  }

  private void injectDependencies(RecyclerView recyclerView) {
    repoListDelegate = new RepoListDelegate(recyclerView);
  }
}
