package vn.tale.architecture.top_repos;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@com.google.auto.value.AutoValue
public abstract class TopRepoListState {

  public static Builder builder(TopRepoListState source) {
    return new AutoValue_TopRepoListState.Builder(source);
  }

  public static Builder builder() {
    return new AutoValue_TopRepoListState.Builder()
        .loading(false)
        .loadingMore(false)
        .refreshing(false)
        .page(1)
        .content(Collections.emptyList());
  }

  public static TopRepoListState idle() {
    return builder().make();
  }

  public abstract boolean loading();

  public abstract boolean loadingMore();

  public abstract boolean refreshing();

  @Nullable public abstract Throwable loadError();

  @Nullable public abstract Throwable loadMoreError();

  @Nullable public abstract Throwable refreshError();

  public abstract List<Repo> content();

  public abstract int page();

  @com.google.auto.value.AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder loadingMore(boolean loadingMore);

    public abstract Builder refreshing(boolean refreshing);

    public abstract Builder loadError(Throwable loadError);

    public abstract Builder loadMoreError(Throwable loadMoreError);

    public abstract Builder refreshError(Throwable refreshError);

    public abstract Builder content(List<Repo> content);

    public abstract Builder page(int page);

    public abstract TopRepoListState make();
  }
}
