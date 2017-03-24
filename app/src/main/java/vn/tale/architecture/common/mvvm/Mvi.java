package vn.tale.architecture.common.mvvm;

import io.reactivex.Observable;
import java.util.Map;

/**
 * Created by Giang Nguyen on 3/24/17.
 */

public class Mvi {

  void run(android.view.View viewSource, Intent intent, Model<Object> model, View<Object> view) {
    final Map<String, Observable<Action>> intentObject = intent.intent(viewSource);
    final Observable<Object> model$ = model.model(intentObject);
    model$.subscribe(view::render);
  }

  interface VirtualView {

  }

  interface Intent {
    Map<String, Observable<Action>> intent(android.view.View view);
  }

  interface Model<UiModel> {
    Observable<UiModel> model(Map<String, Observable<Action>> intent$);
  }

  interface View<UiModel> {
    void render(UiModel model);
  }
}
