package vn.tale.architecture.model.manager;

import io.reactivex.Single;
import ix.Ix;
import java.util.List;
import vn.tale.architecture.model.Owner;
import vn.tale.architecture.model.Repo;
import vn.tale.architecture.model.api.GithubApi;

/**
 * Created by Giang Nguyen on 2/21/17.
 */

public class RepoModel {

  private final GithubApi githubApi;

  public RepoModel(GithubApi githubApi) {
    this.githubApi = githubApi;
  }

  public Single<List<Repo>> getPublicRepos(int page) {
    return githubApi.searchRepos("language:java", "stars", "desc", page, 10)
        .map(response ->
            Ix.from(response.getItems())
                .map(repoResponse ->
                    Repo.builder()
                        .name(repoResponse.getName())
                        .description(repoResponse.getDescription())
                        .forksCount(repoResponse.getForksCount())
                        .stargazersCount(repoResponse.getStargazersCount())
                        .owner(Owner.make(repoResponse.getUserResponse().getAvatarUrl()))
                        .make())
                .toList());
  }
}
