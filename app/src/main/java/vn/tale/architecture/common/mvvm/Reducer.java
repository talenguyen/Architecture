package vn.tale.architecture.common.mvvm;

import io.reactivex.functions.BiFunction;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public interface Reducer<UiModel> extends BiFunction<UiModel, Result, UiModel> {
}
