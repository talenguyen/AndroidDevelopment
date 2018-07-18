package vn.tiki.android.githubsample.domain.entities

data class Paging(val page: Int, val lastPage: Int, val total: Int)

fun Paging.hasMore() = page < lastPage

fun Paging.nextPage() = page + 1
