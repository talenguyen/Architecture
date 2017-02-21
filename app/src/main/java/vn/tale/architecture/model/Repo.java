package vn.tale.architecture.model;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class Repo {
  private final String name;
  private final String description;

  public Repo(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Repo repo = (Repo) o;

    if (getName() != null ? !getName().equals(repo.getName()) : repo.getName() != null) {
      return false;
    }
    return getDescription() != null ? getDescription().equals(repo.getDescription())
        : repo.getDescription() == null;
  }

  @Override public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    return result;
  }
}
