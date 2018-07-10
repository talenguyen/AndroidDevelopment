package vn.tiki.android.pref;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.*;
import org.mockito.*;

@SuppressWarnings("KotlinInternalInJava")
public class PrefFactoryKtTest {

  private SharedPreferences mockedSharedPreferences;
  private Editor mockedEditor;

  @Before
  public void setUp() {
    mockedSharedPreferences = mock(SharedPreferences.class);
    mockedEditor = mock(Editor.class);
    when(mockedSharedPreferences.edit()).thenReturn(mockedEditor);
    when(mockedSharedPreferences.getString(anyString(), anyString())).thenReturn("");
    when(mockedSharedPreferences.getStringSet(
        anyString(),
        ArgumentMatchers.<String>anySet())).thenReturn(Collections.<String>emptySet());
  }

  @Test
  public void testInt() {
    final String key = "int";
    final int defaultValue = 0;
    final Pref<Integer> intPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    intPref.set(1);
    Mockito.verify(mockedEditor).putInt(eq(key), eq(1));

    // test get
    intPref.get();
    Mockito.verify(mockedSharedPreferences).getInt(eq(key), eq(defaultValue));
  }

  @Test
  public void testLong() {
    final String key = "long";
    final long defaultValue = 0;
    final Pref<Long> longPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    longPref.set(1L);
    Mockito.verify(mockedEditor).putLong(eq(key), eq(1L));

    // test get
    longPref.get();
    Mockito.verify(mockedSharedPreferences).getLong(eq(key), eq(defaultValue));
  }

  @Test
  public void testFloat() {
    final String key = "float";
    final float defaultValue = 0;
    final Pref<Float> floatPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    floatPref.set(1F);
    Mockito.verify(mockedEditor).putFloat(eq(key), eq(1F));

    // test get
    floatPref.get();
    Mockito.verify(mockedSharedPreferences).getFloat(eq(key), eq(defaultValue));
  }

  @Test
  public void testBoolean() {
    final String key = "boolean";
    final boolean defaultValue = false;
    final Pref<Boolean> booleanPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    booleanPref.set(true);
    Mockito.verify(mockedEditor).putBoolean(eq(key), eq(true));

    // test get
    booleanPref.get();
    Mockito.verify(mockedSharedPreferences).getBoolean(eq(key), eq(defaultValue));
  }

  @Test
  public void testString() {
    final String key = "string";
    final String defaultValue = "";
    final Pref<String> stringPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    stringPref.set("value");
    Mockito.verify(mockedEditor).putString(eq(key), eq("value"));

    // test get
    stringPref.get();
    Mockito.verify(mockedSharedPreferences).getString(eq(key), eq(defaultValue));
  }

  @Test
  public void testStringSet() {
    final String key = "stringSet";
    final Set<String> defaultValue = Collections.emptySet();
    final Pref<Set<String>> stringSetPref = PrefFactoryKt.pref(mockedSharedPreferences, key, defaultValue);
    // test set
    final Set<String> value = new HashSet<>();
    stringSetPref.set(value);
    Mockito.verify(mockedEditor).putStringSet(eq(key), eq(value));

    // test get
    stringSetPref.get();
    Mockito.verify(mockedSharedPreferences).getStringSet(eq(key), eq(defaultValue));
  }
}
