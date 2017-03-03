package vn.tale.architecture.model;

import com.google.auto.value.AutoValue;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
@AutoValue
public abstract class Repo {
  public static Repo repo(String name, String description) {
    return new AutoValue_Repo(name, description);
  }

  public abstract String name();

  public abstract String description();
}
