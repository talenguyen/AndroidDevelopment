package vn.tiki.android.repatch

typealias Reducer<T> = (T) -> T
typealias Dispatcher<T> = (Reducer<T>) -> Unit
typealias Middleware<T> = (Store<T>, Dispatcher<T>) -> Dispatcher<T>
