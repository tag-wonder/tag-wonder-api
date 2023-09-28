package org.tagwonder.exceptions

import org.springframework.http.HttpStatusCode

fun InvalidCommandException.toHttpException(status: HttpStatusCode): HttpException {
    return HttpException(
        status.value(),
        this.message,
        this.className
    )
}

fun InvalidRequestException.toHttpException(status: HttpStatusCode): HttpException {
    return HttpException(
        status.value(),
        this.message,
        this.className
    )
}
