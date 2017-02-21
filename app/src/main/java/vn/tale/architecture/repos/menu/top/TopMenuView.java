package vn.tale.architecture.repos.menu.top;

import android.view.Menu;
import android.view.MenuItem;
import vn.tale.architecture.R;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class TopMenuView {

  private final MenuItem loginItem;
  private final MenuItem logoutItem;

  public TopMenuView(Menu menu) {
    loginItem = menu.findItem(R.id.action_login);
    logoutItem = menu.findItem(R.id.action_logout);
  }

  public void showLoginMenu() {
    loginItem.setVisible(true);
    logoutItem.setVisible(false);
  }

  public void showLogoutMenu() {
    loginItem.setVisible(false);
    logoutItem.setVisible(true);
  }
}
