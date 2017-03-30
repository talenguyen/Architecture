package vn.tale.architecture.top_repos;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@com.google.auto.value.AutoValue
public abstract class TopRepoListUiState {

  public static Builder builder(TopRepoListUiState source) {
    return new AutoValue_TopRepoListUiState.Builder(source);
  }

  public static Builder builder() {
    return new AutoValue_TopRepoListUiState.Builder()
        .loading(false)
        .refreshing(false)
        .refreshError(null)
        .loadError(null)
        .content(Collections.emptyList());
  }

  public static TopRepoListUiState idle() {
    return builder().make();
  }

  public abstract boolean loading();

  public abstract boolean refreshing();

  @Nullable public abstract Throwable loadError();

  @Nullable public abstract Throwable refreshError();

  public abstract List<Repo> content();

  @com.google.auto.value.AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder refreshing(boolean refreshing);

    public abstract Builder loadError(Throwable loadError);

    public abstract Builder refreshError(Throwable refreshError);

    public abstract Builder content(List<Repo> content);

    public abstract TopRepoListUiState make();
  }
}
