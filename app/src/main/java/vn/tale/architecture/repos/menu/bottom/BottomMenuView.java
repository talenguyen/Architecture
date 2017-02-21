package vn.tale.architecture.repos.menu.bottom;

import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import vn.tale.architecture.R;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class BottomMenuView {

  private final MenuItem myRepoItem;

  public BottomMenuView(BottomNavigationView bottomNavigationView) {
    myRepoItem = bottomNavigationView.getMenu().findItem(R.id.action_my_repos);
  }

  public void hideUserRepos() {
    myRepoItem.setVisible(false);
  }

  public void showUserRepos() {
    myRepoItem.setVisible(true);
  }
}
