package vn.tale.architecture.model;

/**
 * Created by Giang Nguyen on 3/27/17.
 */
@com.google.auto.value.AutoValue
public abstract class Owner {

  public static Owner make(String avatarUrl) {
    return new AutoValue_Owner(avatarUrl);
  }

  public abstract String avatarUrl();
}
