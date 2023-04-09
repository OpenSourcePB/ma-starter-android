package az.pashabank.data.local.base

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import az.pashabank.data.mapper.SerializableMapper
import kotlinx.serialization.json.Json
import kotlin.reflect.KProperty

abstract class BasePreferences(context: Context) {

    protected abstract val filename: String

    protected open val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        )
    }

    protected inner class StringValue(private val key: String, private val default: String) :
        ReadWriteDelegate<String> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return prefs.getString(key, default) ?: default
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            prefs.edit { putString(key, value) }
        }
    }

    protected inner class IntValue(private val key: String, private val default: Int) :
        ReadWriteDelegate<Int> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return prefs.getInt(key, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            prefs.edit { putInt(key, value) }
        }
    }

    protected inner class FloatValue(private val key: String, private val default: Float) :
        ReadWriteDelegate<Float> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
            return prefs.getFloat(key, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
            prefs.edit { putFloat(key, value) }
        }
    }

    protected inner class LongValue(private val key: String, private val default: Long) :
        ReadWriteDelegate<Long> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
            return prefs.getLong(key, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
            prefs.edit { putLong(key, value) }
        }
    }

    protected inner class BooleanValue(private val key: String, private val default: Boolean) :
        ReadWriteDelegate<Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            return prefs.getBoolean(key, default)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            prefs.edit { putBoolean(key, value) }
        }
    }

    protected inner class SerializableValue<Domain, Data>(
        private val key: String,
        private val default: Domain,
        val mapper: SerializableMapper<Domain, Data>
    ) : ReadWriteDelegate<Domain> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): Domain {
            val raw = prefs.getString(key, null) ?: return default
            return try {
                mapper.toDomain(serializableValueJson.decodeFromString(mapper.getSerializer(), raw))
            } catch (e: Exception) {
                default
            }

        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Domain) {
            val raw = try {
                serializableValueJson.encodeToString(mapper.getSerializer(), mapper.toData(value))
            } catch (e: Exception) {
                null
            }
            prefs.edit { putString(key, raw) }
        }
    }

    private val serializableValueJson = Json { this.ignoreUnknownKeys = true }
}