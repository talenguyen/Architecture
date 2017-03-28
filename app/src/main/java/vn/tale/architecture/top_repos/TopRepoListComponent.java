package vn.tale.architecture.top_repos;

import dagger.Subcomponent;
import vn.tale.architecture.ActivityScope;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@ActivityScope
@Subcomponent(modules = TopRepoListModule.class)
public interface TopRepoListComponent {
  void inject(TopRepoListActivity topRepoListActivity);
}
