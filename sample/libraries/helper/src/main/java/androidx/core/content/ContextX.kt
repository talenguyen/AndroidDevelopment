package androidx.core.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

fun Context.dip(size: Int): Int = (resources.displayMetrics.density * size).toInt()

fun Context.dimen(@DimenRes id: Int): Int = resources.getDimensionPixelSize(id)

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)

fun Context.drawable(
  @DrawableRes drawableRes: Int
): Drawable? = ContextCompat.getDrawable(this, drawableRes)
