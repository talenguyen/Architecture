package vn.tale.architecture.common.pattern;

import android.support.annotation.NonNull;
import java.util.List;
import vn.tale.architecture.common.base.MvpPresenter;

import static vn.tale.architecture.common.CollectionsX.concat;

public class AsyncLoadPagingPresenter<T> extends MvpPresenter<AsyncLoadPaging.View<T>> {

  private final AsyncLoadPaging.GetDataInteractor<T> getDataInteractor;
  private int nextPage = 2;
  private List<T> items;

  public AsyncLoadPagingPresenter(AsyncLoadPaging.GetDataInteractor<T> getDataInteractor) {
    this.getDataInteractor = getDataInteractor;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    final AsyncLoadPaging.View<T> view = getView();

    disposeOnDetach(view.onLoad().subscribe(ignored -> load()));
    disposeOnDetach(view.onLoadMore().subscribe(ignored -> loadMore()));
    disposeOnDetach(view.onRefresh().subscribe(ignored -> refresh()));
    disposeOnDetach(view.onDestroy().subscribe(ignored -> detachView()));
  }

  private void refresh() {
    getView().showRefreshIndicator();
    disposeOnDetach(getDataInteractor.itemsOfPage(1)
        .subscribe(items -> {
          nextPage = 2;
          updateItems(items);
          getView().showContent(items);
        }, getView()::showRefreshError));
  }

  private void loadMore() {
    getView().showLoadMoreIndicator();
    disposeOnDetach(getDataInteractor.itemsOfPage(nextPage)
        .subscribe(items -> {
          nextPage++;
          List<T> mergedItems = concat(this.items, items);
          getView().showContent(mergedItems);
          updateItems(mergedItems);
        }, getView()::showLoadMoreError));
  }

  private void load() {
    getView().showLoading();
    disposeOnDetach(getDataInteractor.itemsOfPage(1)
        .subscribe(items -> {
          updateItems(items);
          getView().showContent(items);
        }, getView()::showError));
  }

  @NonNull @Override protected AsyncLoadPaging.View<T> getView() {
    final AsyncLoadPaging.View<T> view = super.getView();
    if (view == null) {
      return new NoOpPagingView<>();
    }
    return view;
  }

  private void updateItems(List<T> items) {
    this.items = items;
  }
}