package vn.tiki.android.pref

import android.content.SharedPreferences

fun SharedPreferences.intPref(key: String, defaultValue: Int): Pref<Int> = pref(this, key, defaultValue)

fun SharedPreferences.longPref(key: String, defaultValue: Long): Pref<Long> = pref(this, key, defaultValue)

fun SharedPreferences.booleanPref(key: String, defaultValue: Boolean): Pref<Boolean> = pref(this, key, defaultValue)

fun SharedPreferences.floatPref(key: String, defaultValue: Float): Pref<Float> = pref(this, key, defaultValue)

fun SharedPreferences.stringPref(key: String, defaultValue: String): Pref<String> = pref(this, key, defaultValue)

fun SharedPreferences.stringSetPref(key: String, defaultValue: Set<String>): Pref<Set<String>> = pref(this, key, defaultValue)
