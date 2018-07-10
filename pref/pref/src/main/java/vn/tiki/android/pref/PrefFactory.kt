package vn.tiki.android.pref

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

internal fun <T> pref(preferences: SharedPreferences, key: String, defaultValue: T): Pref<T> {
  val getter = findGetter(preferences, defaultValue)
  val setter: Setter<T> = findSetter(preferences, defaultValue)
  return Pref(getter, setter, key, defaultValue)
}

internal fun <T> findSetter(preferences: SharedPreferences, defaultValue: T): Setter<T> {
  @Suppress("UNCHECKED_CAST")
  val setterFunc: (Editor, String, T) -> Unit = when (defaultValue) {
    is Int -> { editor, key, value ->
      editor.putInt(key, value as Int)
    }

    is Long -> { editor, key, value ->
      editor.putLong(key, value as Long)
    }

    is Boolean -> { editor, key, value ->
      editor.putBoolean(key, value as Boolean)
    }

    is Float -> { editor, key, value ->
      editor.putFloat(key, value as Float)
    }

    is String -> { editor, key, value ->
      editor.putString(key, value as String)
    }

    is Set<*> -> { editor, key, value ->
      editor.putStringSet(key, value as Set<String>)
    }

    else -> throw IllegalArgumentException("not support $defaultValue")
  }

  return object : Setter<T> {
    override fun invoke(key: String, value: T) {
      preferences.edit()
        .apply {
          setterFunc(this, key, value)
        }
        .apply()
    }
  }
}

@Suppress("UNCHECKED_CAST")
internal fun <T> findGetter(preferences: SharedPreferences, value: T): Getter<T> {
  val getterFunc: (String, T) -> T = when (value) {
    is Int -> { key, defaultValue ->
      preferences.getInt(key, defaultValue as Int) as T
    }

    is Long -> { key, defaultValue ->
      preferences.getLong(key, defaultValue as Long) as T
    }

    is Boolean -> { key, defaultValue ->
      preferences.getBoolean(key, defaultValue as Boolean) as T
    }

    is Float -> { key, defaultValue ->
      preferences.getFloat(key, defaultValue as Float) as T
    }

    is String -> { key, defaultValue ->
      preferences.getString(key, defaultValue as String) as T
    }

    is Set<*> -> { key, defaultValue ->
      preferences.getStringSet(key, defaultValue as Set<String>) as T
    }

    else -> throw IllegalArgumentException("not support $value")
  }

  return object : Getter<T> {
    override fun invoke(key: String, defaultValue: T): T = getterFunc(key, defaultValue)
  }
}
