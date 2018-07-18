package androidx.lifecycle

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.support.annotation.MainThread

fun <T> LiveData<T>.nonNull(): NonNullLiveData<T> {
  val mediator: NonNullLiveData<T> = androidx.lifecycle.NonNullLiveData()
  mediator.addSource(this) { it?.let { mediator.value = it } }
  return mediator
}

@MainThread
fun <X, Y> LiveData<X>.map(func: (X?) -> Y?): LiveData<Y> = Transformations.map(this, func)

@MainThread
fun <X> LiveData<X>.filter(predicate: (X?) -> Boolean): LiveData<X> {
  val result = MediatorLiveData<X>()
  result.addSource(this) { value ->
    if (predicate(value)) {
      result.value = value
    }
  }
  return result
}

@MainThread
fun <X, Y> LiveData<X>.switchMap(
  func: (X?) -> LiveData<Y>
): LiveData<Y> = Transformations.switchMap(this, func)

fun <T> liveData(value: T?): LiveData<T> = ValueLiveData.create(value)

fun <T> mutableLiveData(initialValue: T? = null): MutableLiveData<T> {
  val mutableLiveData = MutableLiveData<T>()
  if (initialValue != null) {
    mutableLiveData.postValue(initialValue)
  }
  return mutableLiveData
}

object LiveDataX {

  fun <T, R> combineLatest(
    source1: LiveData<T>,
    source2: LiveData<T>,
    func: (T?, T?) -> R?
  ): LiveData<R> {
    val result = MediatorLiveData<R>()
    var v1: ValueHolder<T>? = null
    var v2: ValueHolder<T>? = null

    val combine: () -> Unit = {
      if (v1 != null && v2 != null) {
        result.value = func(v1?.value, v2?.value)
      }
    }

    result.addSource(source1) { t1 ->
      v1 = ValueHolder(t1)
      combine()
    }
    result.addSource(source2) { t2 ->
      v2 = ValueHolder(t2)
      combine()
    }
    return result
  }
}

internal class ValueHolder<T>(val value: T?)
