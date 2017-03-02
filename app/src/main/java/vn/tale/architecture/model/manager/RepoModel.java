package vn.tale.architecture.model.manager;

import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class RepoModel {

  private static final int RESPONSE_TIME = 3000;

  public Single<List<Repo>> getPublicRepos() {
    return Single.fromCallable(new Callable<List<Repo>>() {
      @Override public List<Repo> call() throws Exception {
        final ArrayList<Repo> repos = new ArrayList<>();
        for (String key : MockManager.REPOS.keySet()) {
          repos.addAll(MockManager.REPOS.get(key));
        }
        return repos;
      }
    }).delay(3, TimeUnit.SECONDS);
  }

  public Single<List<Repo>> getUserRepos(final String email) {
    return Single.fromCallable(new Callable<List<Repo>>() {
      @Override public List<Repo> call() throws Exception {
        Thread.sleep(RESPONSE_TIME);
        if (MockManager.REPOS.containsKey(email)) {
          return MockManager.REPOS.get(email);
        }
        throw new NoSuchElementException("no data");
      }
    });
  }
}
