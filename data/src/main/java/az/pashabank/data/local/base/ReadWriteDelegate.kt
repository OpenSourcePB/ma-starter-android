package az.pashabank.data.local.base

import kotlin.reflect.KProperty

interface ReadWriteDelegate<T> : ReadDelegate<T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}