package vn.tale.architecture.repos.pub;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import java.util.List;
import vn.tale.architecture.common.SchedulerSingleTransformer;
import vn.tale.architecture.common.base.MvpPresenter;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.manager.RepoModel;

/**
 * Created by Giang Nguyen on 2/25/17.
 */

public class PublicReposPresenter extends MvpPresenter<PublicReposView> {

  private static final String TAG = "ReposPresenter";
  private final RepoModel repoModel;
  private final SchedulerSingleTransformer schedulerSingleTransformer;
  private Single<List<Repo>> publicReposRequest;

  public PublicReposPresenter(RepoModel repoModel) {
    this(repoModel, SchedulerSingleTransformer.IO);
  }

  @VisibleForTesting PublicReposPresenter(RepoModel repoModel,
      SchedulerSingleTransformer schedulerSingleTransformer) {
    this.repoModel = repoModel;
    this.schedulerSingleTransformer = schedulerSingleTransformer;
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
    if (publicReposRequest == null) {
      publicReposRequest = repoModel.getPublicRepos().cache();
    }

    getView().showLoading();

    disposeOnDetach(
        publicReposRequest
            .compose(schedulerSingleTransformer.<List<Repo>>transformer())
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
