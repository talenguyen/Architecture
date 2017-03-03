package vn.tale.architecture.repos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import vn.tale.architecture.R;
import vn.tale.architecture.model.Repo;
import vn.tiki.noadapter2.AbsViewHolder;

/**
 * Created by Giang Nguyen on 2/21/17.
 */
class RepoViewHolder extends AbsViewHolder {

  private final TextView tvName;
  private final TextView tvDescription;

  private RepoViewHolder(View itemView) {
    super(itemView);
    tvName = (TextView) itemView.findViewById(R.id.tvName);
    tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
  }

  static RepoViewHolder create(ViewGroup parent) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View view = inflater.inflate(R.layout.item_repo, parent, false);
    return new RepoViewHolder(view);
  }

  @Override public void bind(Object item) {
    super.bind(item);
    final Repo repo = ((Repo) item);
    tvName.setText(repo.name());
    tvDescription.setText(repo.description());
  }
}
