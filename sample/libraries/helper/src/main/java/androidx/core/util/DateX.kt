package androidx.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Hours per day.
 */
internal const val HOURS_PER_DAY = 24
/**
 * Minutes per hour.
 */
internal const val MINUTES_PER_HOUR = 60
/**
 * Seconds per minute.
 */
internal const val SECONDS_PER_MINUTE = 60
/**
 * Seconds per hour.
 */
internal const val SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR
/**
 * Seconds per day.
 */
internal const val SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY
/**
 * Millis per day.
 */
internal const val MILLIS_PER_DAY = SECONDS_PER_DAY * 1_000

/**
 * Millis per hour.
 */
internal const val MILLIS_PER_HOUR = SECONDS_PER_HOUR * 1_000

/**
 * Convert millis to day
 */
fun Long.toDay(): Long = this / MILLIS_PER_DAY

/**
 * Convert millis to hour
 */
fun Long.toHour() = this / MILLIS_PER_HOUR

/**
 * Int as a day
 */
fun Int.asDay() = this * MILLIS_PER_DAY

/**
 * Int as hour
 */
fun Int.asHour() = this * MILLIS_PER_HOUR

/**
 * Convert millis to [Date]
 */
fun Long.toDate() = Date(this)

fun Date.format(pattern: String): String {
  return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

object DateFormat {
  val HHmm_ddMMyyy = "HH:mm dd/MM/yyyy"
}
