package vn.tale.architecture.top_repos.action;

import vn.tale.architecture.common.reduxer.Action;

/**
 * Created by Giang Nguyen on 3/27/17.
 */

public interface LoadTopRepoAction {
  Action LOAD = new Action() {};
  Action LOAD_MORE = new Action() {};
  Action REFRESH = new Action() {};
}
