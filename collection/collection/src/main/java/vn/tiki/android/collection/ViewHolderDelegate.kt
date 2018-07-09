package vn.tiki.android.collection

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View

interface ViewHolderDelegate<in T> {

  /**
   * Call to bind views
   *
   * @param view the inflated view
   */
  fun bindView(view: View)

  /**
   * Called to update view
   *
   * @param model data
   */
  fun bind(model: T)

  /**
   * Called when a view created by adapter has been recycled. This may be
   * a good place to release expensive data or resources.
   */
  fun unbind()

  /**
   * @return layout id
   */
  @LayoutRes
  fun layout(): Int

  /**
   * @return ids of views that are clickable
   */
  fun clickableIds(): IntArray

  /**
   * Callback that is called when view with given id is clicked.
   */
  fun onClick(@IdRes id: Int)
}
