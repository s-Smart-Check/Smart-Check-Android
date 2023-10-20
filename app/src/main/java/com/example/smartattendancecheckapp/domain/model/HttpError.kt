package com.example.smartattendancecheckapp.domain.model

class HttpError(val code: Int, val errorBody: String): Exception()