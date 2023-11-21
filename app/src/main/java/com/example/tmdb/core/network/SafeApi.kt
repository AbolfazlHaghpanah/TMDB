package com.example.tmdb.core.network

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import retrofit2.Response

suspend fun <T> safeApi(
    call: suspend () -> Response<T>,
    onDataReady: (T) -> Unit
): Flow<Result> {
    return flow {
        emit(Result.Loading)
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (currentCoroutineContext().isActive) {
                        emit(Result.Success(body))
                    }
                } else {
                    val bodyCode = response.errorBody()?.string()
                        ?.let {
                            val json = Json { ignoreUnknownKeys = true }
                            json.decodeFromString<ErrorResponse>(it)
                        }
                    throw Throwable(message = mapServerErrorMessage(bodyCode?.status_message ?: ""))
                }
            } else {
                val bodyCode = response.errorBody()?.string()
                    ?.let {
                        val json = Json { ignoreUnknownKeys = true }
                        json.decodeFromString<ErrorResponse>(it)
                    }
                throw Throwable(message = mapServerErrorMessage(bodyCode?.status_message ?: ""))
            }
        } catch (t: Throwable) {
            emit(Result.Error(t.message ?: "Network may not be Available"))
        }
        onRequestDone()
    }.cancellable()
}

fun mapServerErrorMessage(serverErrorMessage: String): String {
    return when {
        serverErrorMessage.contains("Success.") -> "Operation was successful."
        serverErrorMessage.contains("Invalid service: this service does not exist.") -> "Invalid service. Please check the service."
        serverErrorMessage.contains("Authentication failed: You do not have permissions to access the service.") -> "Authentication failed. Check your permissions."
        serverErrorMessage.contains("Invalid format: This service doesn't exist in that format.") -> "Invalid format for the service."
        serverErrorMessage.contains("Invalid parameters: Your request parameters are incorrect.") -> "Invalid parameters. Please check your request."
        serverErrorMessage.contains("Invalid id: The pre-requisite id is invalid or not found.") -> "Invalid ID. The specified ID is not valid or not found."
        serverErrorMessage.contains("Invalid API key: You must be granted a valid key.") -> "Invalid API key. Make sure you have a valid key."
        serverErrorMessage.contains("Duplicate entry: The data you tried to submit already exists.") -> "Duplicate entry. The submitted data already exists."
        serverErrorMessage.contains("Service offline: This service is temporarily offline, try again later.") -> "Service is temporarily offline. Please try again later."
        serverErrorMessage.contains("Suspended API key: Access to your account has been suspended, contact TMDB.") -> "Suspended API key. Contact TMDB for assistance."
        serverErrorMessage.contains("Internal error: Something went wrong, contact TMDB.") -> "Internal error. Please contact TMDB for support."
        serverErrorMessage.contains("The item/record was updated successfully.") -> "The item/record was updated successfully."
        serverErrorMessage.contains("The item/record was deleted successfully.") -> "The item/record was deleted successfully."
        serverErrorMessage.contains("Authentication failed.") -> "Authentication failed."
        serverErrorMessage.contains("Failed.") -> "Operation failed. Please try again."
        serverErrorMessage.contains("Device denied.") -> "Access denied for this device."
        serverErrorMessage.contains("Session denied.") -> "Access denied for this session."
        serverErrorMessage.contains("Validation failed.") -> "Validation failed. Check your input."
        serverErrorMessage.contains("Invalid accept header.") -> "Invalid accept header."
        serverErrorMessage.contains("Invalid date range: Should be a range no longer than 14 days.") -> "Invalid date range. Maximum range is 14 days."
        serverErrorMessage.contains("Entry not found: The item you are trying to edit cannot be found.") -> "Entry not found. The specified item cannot be found."
        serverErrorMessage.contains("Invalid page: Pages start at 1 and max at 500. They are expected to be an integer.") -> "Invalid page. Pages should start at 1 and have a maximum of 500."
        serverErrorMessage.contains("Invalid date: Format needs to be YYYY-MM-DD.") -> "Invalid date format. Please use the format YYYY-MM-DD."
        serverErrorMessage.contains("Your request to the backend server timed out. Try again.") -> "Request timed out. Please try again."
        serverErrorMessage.contains("Your request count (#) is over the allowed limit of (40).") -> "Request count exceeded the limit of 40. Please try again later."
        serverErrorMessage.contains("You must provide a username and password.") -> "You must provide a username and password."
        serverErrorMessage.contains("Too many append to response objects: The maximum number of remote calls is 20.") -> "Too many remote calls. Limit is 20."
        serverErrorMessage.contains("Invalid timezone: Please consult the documentation for a valid timezone.") -> "Invalid timezone. Consult the documentation for a valid timezone."
        serverErrorMessage.contains("You must confirm this action: Please provide a confirm=true parameter.") -> "Confirmation required. Please provide confirm=true parameter."
        serverErrorMessage.contains("Invalid username and/or password: You did not provide a valid login.") -> "Invalid username and/or password. Please provide a valid login."
        serverErrorMessage.contains("Account disabled: Your account is no longer active. Contact TMDB if this is an error.") -> "Account disabled. Contact TMDB for assistance."
        serverErrorMessage.contains("Email not verified: Your email address has not been verified.") -> "Email not verified. Verify your email address."
        serverErrorMessage.contains("Invalid request token: The request token is either expired or invalid.") -> "Invalid request token. The token is either expired or invalid."
        serverErrorMessage.contains("The resource you requested could not be found.") -> "The requested resource could not be found."
        serverErrorMessage.contains("Invalid token.") -> "Invalid token."
        serverErrorMessage.contains("This token hasn't been granted write permission by the user.") -> "This token hasn't been granted write permission by the user."
        serverErrorMessage.contains("The requested session could not be found.") -> "The requested session could not be found."
        serverErrorMessage.contains("You don't have permission to edit this resource.") -> "You don't have permission to edit this resource."
        serverErrorMessage.contains("This resource is private.") -> "This resource is private."
        serverErrorMessage.contains("Nothing to update.") -> "Nothing to update."
        serverErrorMessage.contains("This request token hasn't been approved by the user.") -> "This request token hasn't been approved by the user."
        serverErrorMessage.contains("This request method is not supported for this resource.") -> "This request method is not supported for this resource."
        serverErrorMessage.contains("Couldn't connect to the backend server.") -> "Couldn't connect to the backend server."
        serverErrorMessage.contains("The ID is invalid.") -> "The ID is invalid."
        serverErrorMessage.contains("This user has been suspended.") -> "This user has been suspended."
        serverErrorMessage.contains("The API is undergoing maintenance. Try again later.") -> "The API is undergoing maintenance. Try again later."
        serverErrorMessage.contains("The input is not valid.") -> "The input is not valid."
        else -> serverErrorMessage
    }
}