package vn.tale.architecture.common.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Giang Nguyen on 2/22/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

  private Unbinder unbinder;

  protected void bindViews(Activity activity) {
    unbinder = ButterKnife.bind(activity);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}
