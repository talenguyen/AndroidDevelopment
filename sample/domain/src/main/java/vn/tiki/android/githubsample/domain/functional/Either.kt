package vn.tiki.android.githubsample.domain.functional

import kotlinx.coroutines.experimental.Deferred
import vn.tiki.android.githubsample.domain.UNKNOWN_ERROR
import vn.tiki.android.githubsample.domain.functional.Either.Left
import vn.tiki.android.githubsample.domain.functional.Either.Right

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
  /** * Represents the left side of [Either] class which by convention is a "Failure". */
  data class Left<out L>(val a: L) : Either<L, Nothing>()

  /** * Represents the right side of [Either] class which by convention is a "Success". */
  data class Right<out R>(val b: R) : Either<Nothing, R>()

  val isRight get() = this is Right<R>
  val isLeft get() = this is Left<L>

  fun <L> left(a: L) = Either.Left(a)
  fun <R> right(b: R) = Either.Right(b)

  fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
    when (this) {
      is Left -> fnL(a)
      is Right -> fnR(b)
    }
}

suspend fun <T> Deferred<T>.awaitSafe(): Either<String, T> = try {
  Right(await())
} catch (e: Exception) {
  Left(e.message ?: UNKNOWN_ERROR)
}
