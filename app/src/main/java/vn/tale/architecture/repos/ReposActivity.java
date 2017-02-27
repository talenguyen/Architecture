package vn.tale.architecture.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import vn.tale.architecture.model.manager.UserModel;
import vn.tale.architecture.repos.menu.bottom.BottomMenuPresenter;
import vn.tale.architecture.repos.menu.bottom.BottomMenuView;
import vn.tale.architecture.repos.menu.top.TopMenuPresenter;
import vn.tale.architecture.repos.menu.top.TopMenuView;
import vn.tale.architecture.repos.my.MyReposCoordinator;
import vn.tale.architecture.repos.pub.PublicReposCoordinator;

public class ReposActivity extends AppCompatActivity {

  private FrameLayout container;

  private AppRouter appRouter;
  private BottomMenuPresenter bottomMenuPresenter;
  private BottomNavigationView bottomNavigationView;
  private TopMenuPresenter topMenuPresenter;
  private TopMenuView topMenuView;
  private ReposComponent reposComponent;
  private UserModel userModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDependencies();

    setContentView(R.layout.activity_repos);

    container = (FrameLayout) this.findViewById(R.id.container);

    Coordinators.installBinder(container, new CoordinatorProvider() {
      @Nullable @Override public Coordinator provideCoordinator(View view) {
        final Object tag = view.getTag();
        if ("my".equals(tag)) {
          return new MyReposCoordinator(reposComponent);
        } else {
          return new PublicReposCoordinator(reposComponent);
        }
      }
    });

    bottomNavigationView = (BottomNavigationView) this.findViewById(R.id.btNavigation);

    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
        .OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_public_repos:
            showContent(recyclerView("public"));
            return true;
          case R.id.action_my_repos:
            showContent(recyclerView("my"));
            return true;
        }
        return false;
      }
    });
    showContent(recyclerView("public"));
  }

  @Override protected void onStart() {
    super.onStart();
    bottomMenuPresenter.attachView(new BottomMenuView(bottomNavigationView));
    if (topMenuView != null) {
      topMenuPresenter.attachView(topMenuView);
    }
  }

  @Override protected void onStop() {
    bottomMenuPresenter.detachView();
    topMenuPresenter.detachView();
    super.onStop();
  }

  private void injectDependencies() {
    final AppComponent appComponent = App.get(this).getAppComponent();
    reposComponent = new ReposComponent(appComponent);
    appRouter = appComponent.provideAppRouter();
    userModel = appComponent.provideUserModel();
    bottomMenuPresenter = reposComponent.provideBottomMenuPresenter();
    topMenuPresenter = reposComponent.provideTopMenuPresenter();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.repos_menu, menu);
    if (topMenuView == null) {
      topMenuView = new TopMenuView(menu);
      topMenuPresenter.attachView(topMenuView);
    }
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_login:
        startActivity(appRouter.loginIntent(this));
        return true;
      case R.id.action_logout:
        userModel.logout().subscribe();
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
