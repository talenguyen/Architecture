package vn.tale.architecture.top_repos.result;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import vn.tale.architecture.common.reduxer.Result;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@com.google.auto.value.AutoValue
public abstract class RefreshTopRepoResult implements Result {

  public static Builder builder(RefreshTopRepoResult source) {
    return new AutoValue_RefreshTopRepoResult.Builder(source);
  }

  public static Builder builder() {
    return new AutoValue_RefreshTopRepoResult.Builder()
        .loading(false)
        .content(Collections.emptyList())
        .error(null);
  }

  public static RefreshTopRepoResult inProgress() {
    return builder()
        .loading(true)
        .make();
  }

  public static RefreshTopRepoResult success(List<Repo> content) {
    return builder()
        .content(content)
        .make();
  }

  public static RefreshTopRepoResult failure(Throwable throwable) {
    return builder()
        .error(throwable)
        .make();
  }

  public abstract boolean loading();

  @Nullable public abstract Throwable error();

  public abstract List<Repo> content();

  @com.google.auto.value.AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder error(Throwable error);

    public abstract Builder content(List<Repo> content);

    public abstract RefreshTopRepoResult make();
  }
}
