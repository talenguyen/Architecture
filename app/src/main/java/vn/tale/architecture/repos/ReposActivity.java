package vn.tale.architecture.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import vn.tale.architecture.R;

public class ReposActivity extends AppCompatActivity {

  private static final String TAG = "ReposActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repos);

    final BottomNavigationView bottomNavigationView =
        (BottomNavigationView) this.findViewById(R.id.btNavigation);

    final MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.action_my_repos);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
        .OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_public_repos:
            Log.d(TAG, "onNavigationItemSelected: ");
            return true;
          case R.id.action_my_repos:
            Log.d(TAG, "onNavigationItemSelected: MyRepos");
            return true;
        }
        return false;
      }
    });
  }
}
