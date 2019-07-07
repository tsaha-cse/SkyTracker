package com.skyscanner.domain.base


/**
 * Base class for UseCase that calls it's
 * implementation and wrap the output in
 * a Result<*>
 */
abstract class BaseUseCase<in Param, out T> {

    abstract suspend fun build(param: Param): T

    suspend operator fun invoke(param: Param): Result<T> {
        return try {
            Result.Success(build(param))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(Result.Reason(exception = e))
        }
    }
}