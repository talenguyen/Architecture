package vn.tale.architecture.top_repos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.R2;
import vn.tale.architecture.common.base.RvvmActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.redux.Store;
import vn.tale.architecture.common.util.ImageLoader;
import vn.tale.architecture.common.util.InfiniteScrollListener;
import vn.tale.architecture.model.Constants;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tiki.noadapter2.OnlyAdapter;

/**
 * Created by Giang Nguyen on 3/27/17.
 */

public class TopRepoListActivity extends RvvmActivity<TopRepoListComponent, TopRepoListState> {

  @Inject ImageLoader imageLoader;
  @Inject Store<TopRepoListState> store;
  @Inject TopRepoListViewModel viewModel;

  @BindView(R2.id.rvRepoList) RecyclerView rvRepoList;
  @BindView(R2.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R2.id.contentView) RelativeLayout contentView;

  private OnlyAdapter adapter;
  private Snackbar errorSnackbar;

  @Override protected DaggerComponentFactory<TopRepoListComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new TopRepoListModule());
  }

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected Store<TopRepoListState> store() {
    return store;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_top_repo_list);
    bindViews(this);
    setupProductListView();
    swipeRefreshLayout.setOnRefreshListener(() -> store.dispatch(LoadTopRepoAction.REFRESH));
  }

  @Override protected void onStart() {
    super.onStart();
    store.dispatch(LoadTopRepoAction.LOAD);

    disposeOnStop(viewModel.loading$().subscribe(ignored -> renderLoading()));
    disposeOnStop(viewModel.refreshing$().subscribe(ignored -> renderRefreshing()));
    disposeOnStop(viewModel.loadingMore$().subscribe(ignored -> renderLoadingMore()));
    disposeOnStop(viewModel.loadError$().subscribe(this::renderLoadError));
    disposeOnStop(viewModel.loadMoreError$().subscribe(this::renderLoadMoreError));
    disposeOnStop(viewModel.refreshError$().subscribe(this::renderRefreshError));
    disposeOnStop(viewModel.content$().subscribe(this::renderContent));
  }

  private void setupProductListView() {
    final LinearLayoutManager layoutManager = new LinearLayoutManager(
        this,
        LinearLayoutManager.VERTICAL,
        false);

    rvRepoList.setLayoutManager(layoutManager);

    rvRepoList.addItemDecoration(new DividerItemDecoration(
        this,
        LinearLayoutManager.VERTICAL));

    rvRepoList.setHasFixedSize(true);

    rvRepoList.addOnScrollListener(new InfiniteScrollListener(
        layoutManager,
        3,
        () -> store.dispatch(LoadTopRepoAction.LOAD_MORE)));

    adapter = productListAdapter();
    rvRepoList.setAdapter(adapter);
  }

  private OnlyAdapter productListAdapter() {
    return new OnlyAdapter.Builder()
        .typeFactory(item -> {
          if (item == Constants.ListItem.LOADING) {
            return 1;
          } else if (item == Constants.ListItem.RETRY) {
            return 2;
          }
          return 0;
        })
        .viewHolderFactory((parent, type) -> {
          switch (type) {
            case 1:
            case 2:
            default:
              return RepoViewHolder.make(parent, imageLoader);
          }
        })
        .build();
  }

  private void renderLoadMoreError(Throwable error) {
    swipeRefreshLayout.setRefreshing(false);
    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
  }

  private void renderRefreshError(Throwable error) {
    swipeRefreshLayout.setRefreshing(false);
    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
  }

  private void renderRefreshing() {
    swipeRefreshLayout.setRefreshing(true);
    if (errorSnackbar != null) {
      errorSnackbar.dismiss();
    }
  }

  private void renderLoadingMore() {
    Timber.d("renderLoadingMore");
  }

  private void renderContent(List<Repo> repoList) {
    runOnUiThread(() -> {
      swipeRefreshLayout.setRefreshing(false);
      adapter.setItems(repoList);
    });
  }

  private void renderLoadError(Throwable error) {
    swipeRefreshLayout.setRefreshing(false);
    errorSnackbar = Snackbar.make(contentView, error.getMessage(),
        BaseTransientBottomBar.LENGTH_INDEFINITE);
    errorSnackbar.show();
  }

  private void renderLoading() {
    swipeRefreshLayout.setRefreshing(true);
    if (errorSnackbar != null) {
      errorSnackbar.dismiss();
    }
  }
}
