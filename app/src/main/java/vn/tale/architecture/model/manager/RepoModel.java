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

  private static final String[] LANGUAGES = { "java", "swift", "javascript", "ruby" };

  private final GithubApi githubApi;
  private String language;

  public RepoModel(GithubApi githubApi) {
    this.githubApi = githubApi;
    language = randomLanguage();
  }

  private String randomLanguage() {
    int randomIndex = (int) (System.currentTimeMillis() % LANGUAGES.length);
    return LANGUAGES[randomIndex];
  }

  public Single<List<Repo>> getPublicRepos(boolean refresh, int page) {
    if (refresh) {
      language = randomLanguage();
    }
    final String query = String.format("language:%s", language);
    return githubApi.searchRepos(query, "stars", "desc", page, 10)
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
