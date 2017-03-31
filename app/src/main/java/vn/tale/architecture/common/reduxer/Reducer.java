package vn.tale.architecture.common.reduxer;

import io.reactivex.functions.BiFunction;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public interface Reducer<State> extends BiFunction<State, Result, State> {
}
