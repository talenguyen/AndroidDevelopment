package org.mockito

import org.mockito.stubbing.*

inline fun <reified T> mock(func: T.() -> Unit = {}): T = Mockito.mock(T::class.java).apply(func)
fun <T> on(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)
infix fun <T> OngoingStubbing<T>.doReturn(value: T): OngoingStubbing<T> = thenReturn(value)
//inline fun <reified T> any(): T = Mockito.any() ?: mock()
