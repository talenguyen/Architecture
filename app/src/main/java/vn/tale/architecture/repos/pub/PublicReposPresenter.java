package vn.tale.architecture.repos.pub;

import io.reactivex.Scheduler;
import java.util.List;
import vn.tale.architecture.common.pattern.AsyncLoadPresenter;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 2/25/17.
 */

public class PublicReposPresenter extends AsyncLoadPresenter<List<Repo>> {

  public PublicReposPresenter(PublicReposGetDataInteractor getDataInteractor,
      Scheduler threadScheduler,
      Scheduler uiScheduler, boolean cache) {
    super(getDataInteractor, threadScheduler, uiScheduler, cache);
  }
}
