package vn.tiki.android.githubsample.base.util

import vn.tiki.android.githubsample.domain.entities.ListData

internal class ListDataWrapper<T>(
  private val listData: ListData<T>
) : vn.tiki.android.collectionx.ListData<T> {

  override fun data(): List<T> {
    return listData.list
  }

  override fun lastPage(): Int {
    return listData.paging.lastPage
  }
}

fun <T> ListData<T>.asListData(): vn.tiki.android.collectionx.ListData<T> = ListDataWrapper(this)
