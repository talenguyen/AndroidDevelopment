package androidx.lifecycle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

inline fun <reified T : ViewModel> Fragment.viewModel(
  factory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, factory).get(T::class.java)
