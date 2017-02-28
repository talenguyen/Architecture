package vn.tale.architecture.repos.menu.bottom;

import android.support.design.widget.BottomNavigationView;
import android.view.View;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class BottomMenuView {

  private final BottomNavigationView bottomNavigationView;

  public BottomMenuView(BottomNavigationView bottomNavigationView) {
    this.bottomNavigationView = bottomNavigationView;
  }

  void hideUserRepos() {
    bottomNavigationView.setVisibility(View.GONE);
  }

  void showUserRepos() {
    bottomNavigationView.setVisibility(View.VISIBLE);
  }
}
