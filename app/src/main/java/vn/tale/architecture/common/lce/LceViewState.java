package vn.tale.architecture.common.lce;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public class LceViewState<Content> {
  public final boolean loading;
  public final Content content;
  public final Throwable error;

  private LceViewState(boolean loading, Content content, Throwable error) {
    this.loading = loading;
    this.content = content;
    this.error = error;
  }

  public static <Content> LceViewState<Content> loading() {
    return new LceViewState<>(true, null, null);
  }

  public static <Content> LceViewState<Content> content(Content content) {
    return new LceViewState<>(false, content, null);
  }

  public static <Content> LceViewState<Content> error(Throwable error) {
    return new LceViewState<>(false, null, error);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LceViewState<?> that = (LceViewState<?>) o;

    if (loading != that.loading) return false;
    if (content != null ? !content.equals(that.content) : that.content != null) return false;
    return error != null ? error.equals(that.error) : that.error == null;
  }

  @Override public int hashCode() {
    int result = (loading ? 1 : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (error != null ? error.hashCode() : 0);
    return result;
  }
}
