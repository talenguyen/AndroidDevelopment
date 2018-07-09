/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.tiki.android.collection

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView

/**
 * ListUpdateCallback that dispatches update events to the given adapter.
 *
 * @see DiffUtil.DiffResult.dispatchUpdatesTo
 */
internal class AdapterListUpdateCallback
/**
 * Creates an AdapterListUpdateCallback that will dispatch update events to the given adapter.
 */
  : ListUpdateCallback {

  var adapter: RecyclerView.Adapter<*>? = null
  /**
   * {@inheritDoc}
   */
  override fun onChanged(position: Int, count: Int, payload: Any?) {
    adapter?.notifyItemRangeChanged(position, count, payload)
  }

  /**
   * {@inheritDoc}
   */
  override fun onInserted(position: Int, count: Int) {
    adapter?.notifyItemRangeInserted(position, count)
  }

  /**
   * {@inheritDoc}
   */
  override fun onMoved(fromPosition: Int, toPosition: Int) {
    adapter?.notifyItemMoved(fromPosition, toPosition)
  }

  /**
   * {@inheritDoc}
   */
  override fun onRemoved(position: Int, count: Int) {
    adapter?.notifyItemRangeRemoved(position, count)
  }
}
