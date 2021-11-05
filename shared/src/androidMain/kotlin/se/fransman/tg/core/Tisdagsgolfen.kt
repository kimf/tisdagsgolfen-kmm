package se.fransman.tg.core

import android.content.Context
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun Tisdagsgolfen.Companion.create(ctx: Context, withLog: Boolean) = Tisdagsgolfen().also {
    if (withLog) Napier.base(DebugAntilog())
}