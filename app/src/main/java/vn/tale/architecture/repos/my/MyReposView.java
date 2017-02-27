package vn.tale.architecture.repos.my;

import io.reactivex.Observable;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 2/25/17.
 */

public interface MyReposView {

  Observable<Object> loadRepos();

  void showLoading();

  void showError(Throwable throwable);

  void showRepos(List<Repo> repos);
}
