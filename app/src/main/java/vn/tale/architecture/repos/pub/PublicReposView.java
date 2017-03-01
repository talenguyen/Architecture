package vn.tale.architecture.repos.pub;

import io.reactivex.Observable;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 2/25/17.
 */

interface PublicReposView {

  Observable<Object> loadRepos();

  void showLoading();

  void showError(Throwable throwable);

  void showRepos(List<Repo> repos);
}
