package vn.tale.architecture.top_repos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vn.tale.architecture.R;
import vn.tale.architecture.common.util.ImageLoader;
import vn.tale.architecture.model.Repo;
import vn.tiki.noadapter2.AbsViewHolder;

@SuppressWarnings("WeakerAccess") class RepoViewHolder extends AbsViewHolder {

  private final ImageLoader imageLoader;
  @BindView(R.id.tvName) TextView tvName;
  @BindView(R.id.tvDescription) TextView tvDescription;
  @BindView(R.id.tvStars) TextView tvStars;
  @BindView(R.id.tvForks) TextView tvForks;
  @BindView(R.id.ivOwnerAvatar) ImageView ivOwnerAvatar;

  private RepoViewHolder(View view, ImageLoader imageLoader) {
    super(view);
    ButterKnife.bind(this, view);
    this.imageLoader = imageLoader;
    registerOnClickOn(view);
  }

  public static RepoViewHolder make(ViewGroup parent, ImageLoader imageLoader) {
    final View view = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.item_repo_list,
        parent,
        false);
    return new RepoViewHolder(view, imageLoader);
  }

  @Override public void bind(Object item) {
    super.bind(item);
    if (!(item instanceof Repo)) {
      return;
    }

    final Repo repo = (Repo) item;
    imageLoader.downloadInto(repo.owner().avatarUrl(), ivOwnerAvatar);
    tvName.setText(repo.name());
    tvDescription.setText(repo.description());
    tvStars.setText(String.valueOf(repo.stargazersCount()));
    tvForks.setText(String.valueOf(repo.forksCount()));
  }

  @Override public void unbind() {
    super.unbind();
    imageLoader.cancel(ivOwnerAvatar);
  }
}