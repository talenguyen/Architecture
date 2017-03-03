package vn.tale.architecture.repos.my;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import vn.tale.architecture.model.Repo;

/**
 * Created by Giang Nguyen on 3/3/17.
 */
@com.google.auto.value.AutoValue
public abstract class MyReposState {

  public static Builder builder() {
    return new AutoValue_MyReposState.Builder()
        .loading(false)
        .items(Collections.<Repo>emptyList());
  }

  public abstract boolean loading();

  @Nullable public abstract Throwable error();

  public abstract List<Repo> items();

  public Builder copy() {
    return new AutoValue_MyReposState.Builder(this);
  }

  @com.google.auto.value.AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder error(Throwable error);

    public abstract Builder items(List<Repo> items);

    public abstract MyReposState build();
  }
}
