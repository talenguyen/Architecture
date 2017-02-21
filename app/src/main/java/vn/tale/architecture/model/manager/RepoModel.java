package vn.tale.architecture.model.manager;

import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class RepoModel {

  public Single<List<Repo>> getPublicRepos() {
    return Single.fromCallable(new Callable<List<Repo>>() {
      @Override public List<Repo> call() throws Exception {
        Thread.sleep(500);
        final ArrayList<Repo> repos = new ArrayList<>();
        for (String key : MockManager.REPOS.keySet()) {
          repos.addAll(MockManager.REPOS.get(key));
        }
        return repos;
      }
    });
  }

  public Single<List<Repo>> getUserRepos(final String email) {
    return Single.fromCallable(new Callable<List<Repo>>() {
      @Override public List<Repo> call() throws Exception {
        Thread.sleep(500);
        if (MockManager.REPOS.containsKey(email)) {
          return MockManager.REPOS.get(email);
        }
        throw new NoSuchElementException("no data");
      }
    });
  }
}
