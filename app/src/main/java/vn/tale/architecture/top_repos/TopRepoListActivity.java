package vn.tale.architecture.top_repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import io.reactivex.functions.Action;
import javax.inject.Inject;
import timber.log.Timber;
import vn.tale.architecture.App;
import vn.tale.architecture.R;
import vn.tale.architecture.common.base.ReduxActivity;
import vn.tale.architecture.common.dagger.DaggerComponentFactory;
import vn.tale.architecture.common.mvvm.Store;
import vn.tale.architecture.common.util.ImageLoader;
import vn.tale.architecture.common.util.InfiniteScrollListener;
import vn.tale.architecture.model.Constants;
import vn.tale.architecture.top_repos.action.LoadTopRepoAction;
import vn.tiki.noadapter2.OnlyAdapter;

/**
 * Created by Giang Nguyen on 3/27/17.
 */

public class TopRepoListActivity extends ReduxActivity<TopRepoListComponent, TopRepoListUiState> {

  @Inject ImageLoader imageLoader;
  @Inject Store<TopRepoListUiState> store;

  @BindView(R.id.rvRepoList) RecyclerView rvRepoList;
  @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.contentView) RelativeLayout contentView;

  private OnlyAdapter adapter;
  private Snackbar errorSnackbar;

  @Override protected DaggerComponentFactory<TopRepoListComponent> daggerComponentFactory() {
    return () -> App.get(this).getAppComponent().plus(new TopRepoListModule());
  }

  @Override protected void injectDependencies() {
    daggerComponent().inject(this);
  }

  @Override protected Store<TopRepoListUiState> store() {
    return store;
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored") @NonNull @Override
  protected Action render(TopRepoListUiState topRepoListUiState) {
    if (topRepoListUiState.loading()) {
      return renderLoading();
    } else if (topRepoListUiState.error() != null) {
      topRepoListUiState.error().printStackTrace();
      return renderError(topRepoListUiState.error());
    } else {
      return renderContent(topRepoListUiState);
    }
  }

  @NonNull private Action renderContent(TopRepoListUiState topRepoListUiState) {
    return () -> {
      swipeRefreshLayout.setRefreshing(false);
      adapter.setItems(topRepoListUiState.content());
    };
  }

  @NonNull private Action renderError(Throwable error) {
    return () -> {
      errorSnackbar = Snackbar.make(contentView, error.getMessage(),
          BaseTransientBottomBar.LENGTH_INDEFINITE);
      swipeRefreshLayout.setRefreshing(false);
      errorSnackbar.show();
    };
  }

  @NonNull private Action renderLoading() {
    return () -> {
      swipeRefreshLayout.setRefreshing(true);
      if (errorSnackbar != null) {
        errorSnackbar.dismiss();
      }
    };
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_top_repo_list);
    bindViews(this);
    setupProductListView();
  }

  @Override protected void onStart() {
    super.onStart();
    Timber.d("store => %s", store);
    store.dispatch(LoadTopRepoAction.LOAD);
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
        () -> store.dispatch(LoadTopRepoAction.REFRESH)));

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
}
