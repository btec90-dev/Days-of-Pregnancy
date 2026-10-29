package com.example.ui.prenatal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

class LanguageState {
    val isFrench = mutableStateOf(true)

    fun toggle() {
        isFrench.value = !isFrench.value
    }
}

val LocalAppLanguage = compositionLocalOf { LanguageState() }

object Strings {
    fun t(fr: String, en: String, isFrench: Boolean): String {
        return if (isFrench) fr else en
    }
}
