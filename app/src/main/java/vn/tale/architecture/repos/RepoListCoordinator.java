package vn.tale.architecture.repos;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.manager.RepoModel;
import vn.tiki.noadapter2.AbsViewHolder;
import vn.tiki.noadapter2.OnlyAdapter;
import vn.tiki.noadapter2.ViewHolderFactory;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class RepoListCoordinator extends Coordinator {

  private OnlyAdapter adapter;

  @Override public void attach(View view) {
    super.attach(view);

    final RecyclerView recyclerView = (RecyclerView) view;
    configureRecyclerView(recyclerView);

    adapter = createAdapter();

    recyclerView.setAdapter(adapter);

    new RepoModel().getPublicRepos()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Repo>>() {
          @Override public void accept(List<Repo> repos) throws Exception {
            setItems(repos);
          }
        });
  }

  public void setItems(List<Repo> items) {
    adapter.setItems(items);
  }

  @NonNull private OnlyAdapter createAdapter() {
    return new OnlyAdapter.Builder()
        .viewHolderFactory(new ViewHolderFactory() {
          @Override public AbsViewHolder viewHolderForType(ViewGroup parent, int type) {
            return RepoViewHolder.create(parent);
          }
        })
        .build();
  }

  @NonNull private RecyclerView configureRecyclerView(RecyclerView recyclerView) {
    final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
        recyclerView.getContext(),
        LinearLayoutManager.VERTICAL,
        false);

    recyclerView.setLayoutManager(layoutManager);

    recyclerView.addItemDecoration(new DividerItemDecoration(
        recyclerView.getContext(),
        LinearLayoutManager.VERTICAL));

    recyclerView.setHasFixedSize(true);
    return recyclerView;
  }
}
