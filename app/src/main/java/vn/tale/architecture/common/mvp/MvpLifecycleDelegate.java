package vn.tale.architecture.common.mvp;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public class MvpLifecycleDelegate<ViewState, View extends MvpView<ViewState>> {

  private final MvpPresenter<ViewState, View> mvpPresenter;
  private final View mvpView;

  public MvpLifecycleDelegate(MvpPresenter<ViewState, View> mvpPresenter, View mvpView) {
    this.mvpPresenter = mvpPresenter;
    this.mvpView = mvpView;
  }

  public void onStart() {
    mvpPresenter.attachView(mvpView);
  }

  public void onStop(boolean isFinishing) {
    mvpPresenter.detachView();
    if (isFinishing) {
      mvpPresenter.stop();
    }
  }
}
