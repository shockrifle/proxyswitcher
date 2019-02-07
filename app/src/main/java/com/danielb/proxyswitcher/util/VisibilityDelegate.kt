package com.danielb.proxyswitcher.util

import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class VisibilityDelegate(val isVisible: () -> Boolean) : ReadOnlyProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = if (isVisible()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
