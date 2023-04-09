package az.pashabank.presentation.delegate

import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> viewBinding(binding: (View) -> T): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingDelegate(binding)
}

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val factory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    internal var viewBinding: T? = null

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        viewBinding?.let { return it }
        val view = thisRef.requireView()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(BindingLifecycleObserver())

        return factory(view).also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            viewBinding = null
        }
    }
}