package vn.tale.architecture.repos.pub;

import io.reactivex.Observable;
import java.util.List;
import vn.tale.architecture.common.pattern.AsyncLoad;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.manager.RepoModel;

/**
 * Created by Giang Nguyen on 3/3/17.
 */

public class PublicReposModel implements AsyncLoad.Model<List<Repo>> {

  private RepoModel repoModel;

  public PublicReposModel(RepoModel repoModel) {
    this.repoModel = repoModel;
  }

  @Override public Observable<List<Repo>> getData() {
    return repoModel.getPublicRepos().toObservable();
  }
}
