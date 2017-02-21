package vn.tale.architecture.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import vn.tale.architecture.App;
import vn.tale.architecture.AppComponent;
import vn.tale.architecture.R;
import vn.tale.architecture.common.AppRouter;

public class ReposActivity extends AppCompatActivity {

  private static final String TAG = "ReposActivity";
  private FrameLayout container;

  private AppRouter appRouter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDependencies();

    setContentView(R.layout.activity_repos);

    container = (FrameLayout) this.findViewById(R.id.container);

    Coordinators.installBinder(container, new CoordinatorProvider() {
      @Nullable @Override public Coordinator provideCoordinator(View view) {
        Log.d(TAG, "provideCoordinator: " + view.getTag());
        return new RepoListCoordinator();
      }
    });

    final BottomNavigationView bottomNavigationView =
        (BottomNavigationView) this.findViewById(R.id.btNavigation);

    final MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.action_my_repos);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
        .OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_public_repos:
            Log.d(TAG, "onNavigationItemSelected: ");
            showContent(recyclerView("public"));
            return true;
          case R.id.action_my_repos:
            showContent(recyclerView("my"));
            Log.d(TAG, "onNavigationItemSelected: MyRepos");
            return true;
        }
        return false;
      }
    });
  }

  private void injectDependencies() {
    final AppComponent appComponent = App.get(this).getAppComponent();
    appRouter = appComponent.provideAppRouter();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.repos_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_login:
        startActivity(appRouter.loginIntent(this));
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showContent(View view) {
    container.removeAllViews();
    container.addView(view, new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
  }

  private RecyclerView recyclerView(String tag) {
    final RecyclerView recyclerView = new RecyclerView(this);
    recyclerView.setTag(tag);
    return recyclerView;
  }
}
