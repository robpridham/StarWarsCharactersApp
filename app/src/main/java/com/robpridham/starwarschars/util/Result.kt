package com.robpridham.starwarschars.util

import java.lang.Error

sealed class Result<Payload, Error>

class Success<PayloadType>(val payload: PayloadType): Result<PayloadType, Error>()

class Failure<PayloadType>(val error: Error): Result<PayloadType, Error>()