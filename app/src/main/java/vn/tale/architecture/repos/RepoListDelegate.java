package vn.tale.architecture.repos;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import java.util.List;
import vn.tale.architecture.model.Repo;
import vn.tiki.noadapter2.AbsViewHolder;
import vn.tiki.noadapter2.OnlyAdapter;
import vn.tiki.noadapter2.ViewHolderFactory;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class RepoListDelegate extends Coordinator {

  private final OnlyAdapter adapter;

  public RepoListDelegate(RecyclerView recyclerView) {
    configureRecyclerView(recyclerView);

    adapter = createAdapter();

    recyclerView.setAdapter(adapter);
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
