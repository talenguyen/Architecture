package vn.tale.architecture.common.pattern;

import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Giang Nguyen on 3/8/17.
 */

public interface AsyncLoadPaging {

  interface View<T> {

    Observable<Object> onLoad();

    Observable<Object> onRefresh();

    Observable<Object> onLoadMore();

    Observable<Object> onDestroy();

    void showLoading();

    void showLoadMoreIndicator();

    void showRefreshIndicator();

    void showContent(List<T> content);

    void showError(Throwable error);

    void showLoadMoreError(Throwable error);

    void showRefreshError(Throwable error);
  }

  interface GetDataInteractor<T> {

    /**
     * Get items of given page
     *
     * @param page 0
     * @return list of items in page
     */
    Observable<List<T>> itemsOfPage(int page);
  }
}
