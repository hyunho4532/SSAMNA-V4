package com.app.presentation.component.util

import kotlinx.serialization.json.JsonObject

sealed class JsonObj {
    abstract fun build(): JsonObject
}