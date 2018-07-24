package vn.tiki.android.collection.sample.collection

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import vn.tiki.android.collection.ListModel
import vn.tiki.android.collection.sample.viewholder.CheckBoxModel

class DemoViewModel : ViewModel() {

  private val _list = MutableLiveData<List<ListModel>>()

  init {
    _list.value = emptyList()
  }

  val list: LiveData<List<ListModel>>
    get() = _list

  fun add(model: ListModel) {
    setValue(getValue() + model)
  }

  fun remove(position: Int) {
    setValue(getValue().filterIndexed { index, _ -> index != position })
  }

  fun insert(position: Int, model: ListModel) {
    setValue(getValue().toMutableList().apply { add(position, model) })
  }

  fun setValue(newModels: List<ListModel>) {
    _list.value = newModels
  }

  fun update(position: Int, model: ListModel) {
    setValue(getValue().mapIndexed { index, oldModel ->
      if (index == position) {
        model
      } else {
        oldModel
      }
    })
  }

  private fun getValue(): List<ListModel> = _list.value!!
  fun update(model: CheckBoxModel) {
    setValue(getValue().map { oldModel ->
      if (oldModel == model) {
        model.copy(isChecked = !model.isChecked).also { it.onClick = model.onClick }
      } else {
        oldModel
      }
    })
  }
}
