package vn.tiki.android.collection

interface ListModel {

  /**
   * @return unique key for this model in the list
   */
  fun getKey(): String

  /**
   * @return [ViewHolderDelegate] factory
   */
  fun <T: ListModel> getViewHolderDelegateFactory(): () -> ViewHolderDelegate<T>
}
