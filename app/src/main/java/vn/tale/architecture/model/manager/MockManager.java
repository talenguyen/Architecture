package vn.tale.architecture.model.manager;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.User;

import static vn.tale.architecture.model.Repo.repo;
import static vn.tale.architecture.model.User.user;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
class MockManager {
  static final Map<Pair<String, String>, User> USER_MAP;
  static final Map<String, List<Repo>> REPOS;

  static {
    USER_MAP = new ArrayMap<>();
    USER_MAP.put(new Pair<>("foo@tiki.vn", "foo123"), user("foo@tiki.vn", "Mr. Foo"));
    USER_MAP.put(new Pair<>("bar@tiki.vn", "bar123"), user("bar@tiki.vn", "Mr. Bar"));
  }

  static {
    REPOS = new ArrayMap<>();
    REPOS.put("foo@tiki.vn", generateMock(1, 10));
    REPOS.put("bar@tiki.vn", generateMock(2, 10));
  }

  private static List<Repo> generateMock(int index, int size) {
    final ArrayList<Repo> repos = new ArrayList<>(size);
    for (int i = index; i < index + size; i++) {
      repos.add(repo("Repo " + i, "This is mock repo"));
    }
    return repos;
  }
}
