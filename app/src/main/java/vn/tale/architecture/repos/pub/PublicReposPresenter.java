package vn.tale.architecture.repos.pub;

import android.support.annotation.NonNull;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.List;
import vn.tale.architecture.common.SchedulerObservableTransformer;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.manager.RepoModel;

/**
 * Created by Giang Nguyen on 2/25/17.
 */

public class PublicReposPresenter extends MvpPresenter<PublicReposView> {

  private static final String TAG = "ReposPresenter";
  private final RepoModel repoModel;
  private final SchedulerObservableTransformer schedulerObservableTransformer;

  public PublicReposPresenter(RepoModel repoModel) {
    this.repoModel = repoModel;
    this.schedulerObservableTransformer = SchedulerObservableTransformer.IO;
  }

  public PublicReposPresenter(RepoModel repoModel, SchedulerObservableTransformer schedulerObservableTransformer) {
    this.repoModel = repoModel;
    this.schedulerObservableTransformer = schedulerObservableTransformer;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    disposeOnDetach(
        getView().loadRepos()
            .subscribe(new Consumer<Object>() {
              @Override public void accept(Object object) throws Exception {
                loadRepos();
              }
            })
    );
  }

  private void loadRepos() {
    getView().showLoading();
    disposeOnDetach(
        repoModel.getPublicRepos()
            .subscribe(new Consumer<List<Repo>>() {
              @Override public void accept(List<Repo> repos) throws Exception {
                getView().showRepos(repos);
              }
            }, new Consumer<Throwable>() {
              @Override public void accept(Throwable throwable) throws Exception {
                getView().showError(throwable);
              }
            })
    );
  }

  @NonNull @Override protected PublicReposView getView() {
    PublicReposView view = super.getView();
    if (view == null) {
      view = new PublicReposView() {
        @Override public Observable<Object> loadRepos() {
          return Observable.empty();
        }

        @Override public void showLoading() {
          Log.d(TAG, "showLoading: ");
        }

        @Override public void showError(Throwable throwable) {
          Log.e(TAG, "showError: ", throwable);
        }

        @Override public void showRepos(List<Repo> repos) {
          Log.d(TAG, "showRepos: " + (repos == null ? 0 : repos.size()));
        }
      };
    }
    return view;
  }
}
