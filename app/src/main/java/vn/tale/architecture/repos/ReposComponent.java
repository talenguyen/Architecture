package vn.tale.architecture.repos;

import dagger.Subcomponent;
import vn.tale.architecture.ActivityScope;
import vn.tale.architecture.repos.my.MyReposCoordinator;
import vn.tale.architecture.repos.pub.PublicReposCoordinator;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
@ActivityScope
@Subcomponent(modules = ReposModule.class)
public interface ReposComponent {

  void inject(ReposActivity reposActivity);

  void inject(PublicReposCoordinator publicReposCoordinator);

  void inject(MyReposCoordinator myReposCoordinator);
}
