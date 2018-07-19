package vn.tiki.android.githubsample.repositories.viewholder

import android.widget.TextView
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.ViewHolderDelegate
import vn.tiki.android.githubsample.base.viewholder.BaseViewHolderDelegate
import vn.tiki.android.githubsample.base.viewholder.view
import vn.tiki.android.githubsample.domain.entities.Repo
import vn.tiki.android.githubsample.repositories.R

data class RepoModel(val repo: Repo) : ListModel {

  override fun getKey() = "vn.tiki.android.githubsample.repositories.viewholder.RepoModel${repo.id}"

  override fun <T : ListModel> getViewHolderDelegateFactory() = {
    @Suppress("UNCHECKED_CAST")
    RepoViewHolderDelegate() as ViewHolderDelegate<T>
  }
}

class RepoViewHolderDelegate : BaseViewHolderDelegate<RepoModel>() {

  private val tvRepoName: TextView by view(R.id.tvRepoName)
  private val tvDescription: TextView by view(R.id.tvDescription)
  private val tvStars: TextView by view(R.id.tvStars)
  private val tvForks: TextView by view(R.id.tvForks)

  override fun layout(): Int = R.layout.item_repo

  override fun bind(model: RepoModel) {
    super.bind(model)
    val repo = model.repo
    val repoName = "${repo.owner.login}/${repo.name}"
    tvRepoName.text = repoName
    tvDescription.text = repo.description
    tvStars.text = "${repo.stars}"
    tvForks.text = "${repo.forks}"
  }
}
