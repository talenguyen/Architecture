package vn.tale.architecture.common.lce;

import vn.tale.architecture.common.mvp.MvpPresenter;

/**
 * Created by Giang Nguyen on 3/20/17.
 */

public class LcePresenter<Content>
    extends MvpPresenter<LceViewState<Content>, LceContract.View<Content>> {

  private final LceContract.Model<Content> model;

  public LcePresenter(LceContract.Model<Content> model) {
    this.model = model;
  }

  @Override protected void onViewAttached() {
    super.onViewAttached();
    disposeOnDetach(getView().loadIntent().subscribe(o -> load()));
  }

  private void load() {
    getView().render(LceViewState.loading());

    disposeOnStop(model.getContent()
        .subscribe(
            content -> getView().render(LceViewState.content(content)),
            error -> getView().render(LceViewState.error(error))
        ));
  }
}
