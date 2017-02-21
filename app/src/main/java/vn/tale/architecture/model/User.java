package vn.tale.architecture.model;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class User {

  private final String email;
  private final String name;

  public User(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) {
      return false;
    }
    return getName() != null ? getName().equals(user.getName()) : user.getName() == null;
  }

  @Override public int hashCode() {
    int result = getEmail() != null ? getEmail().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }
}
