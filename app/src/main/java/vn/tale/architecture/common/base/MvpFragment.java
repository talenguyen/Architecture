package vn.tale.architecture.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import vn.tale.architecture.common.mvp.MvpLifecycleDelegate;
import vn.tale.architecture.common.mvp.MvpView;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public abstract class MvpFragment<ViewState, V extends MvpView<ViewState>> extends Fragment {

  private MvpLifecycleDelegate<ViewState, V> mvpLifecycleDelegate;

  protected abstract vn.tale.architecture.common.mvp.MvpPresenter<ViewState, V> presenter();

  protected abstract V mvpView();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mvpLifecycleDelegate = new MvpLifecycleDelegate<>(presenter(), mvpView());
    mvpLifecycleDelegate.onStart();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mvpLifecycleDelegate.onStop(getActivity().isFinishing());
  }
}
