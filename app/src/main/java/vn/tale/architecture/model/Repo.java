package vn.tale.architecture.model;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@com.google.auto.value.AutoValue
public abstract class Repo {

  public static Builder builder(Repo source) {
    return new AutoValue_Repo.Builder(source);
  }

  public static Builder builder() {
    return new AutoValue_Repo.Builder();
  }

  public abstract String name();

  public abstract String description();

  public abstract int stargazersCount();

  public abstract int forksCount();

  public abstract Owner owner();

  @com.google.auto.value.AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder name(String name);

    public abstract Builder description(String description);

    public abstract Builder stargazersCount(int stargazersCount);

    public abstract Builder forksCount(int forksCount);

    public abstract Builder owner(Owner owner);

    public abstract Repo make();
  }
}
