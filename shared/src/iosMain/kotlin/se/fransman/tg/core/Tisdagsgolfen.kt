package se.fransman.tg.core

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun Tisdagsgolfen.Companion.create(withLog: Boolean) = Tisdagsgolfen().also {
    if (withLog) Napier.base(DebugAntilog())
}