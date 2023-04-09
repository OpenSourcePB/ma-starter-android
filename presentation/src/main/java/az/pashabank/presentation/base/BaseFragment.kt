package az.pashabank.presentation.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import az.pashabank.starter.presentation.R
import az.pashabank.presentation.common.LogoutHandler
import az.pashabank.presentation.flow.main.MainActivity
import az.pashabank.presentation.tools.NavigationCommand
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<State, Effect, ViewModel : BaseViewModel<State, Effect>, B : ViewBinding> :
    Fragment(), LifecycleOwner {

    protected abstract val vmClazz: KClass<ViewModel>
    protected abstract val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> B
    protected abstract val screenName: String

    val viewModel: ViewModel by lazy { getViewModel(vmClazz) { parametersOf(arguments) } }

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingCallback.invoke(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.setArguments(arguments)
            viewModel.loadArguments()
        }
    }

    protected open val bindViews: B.() -> Unit = {}

    protected open val loadingDialog: DialogFragment? by lazy { LoadingDialog.build() }

    protected open val noInternetDialog: DialogFragment? by lazy {
        BaseBottomSheetDialog.build(
            title = R.string.no_internet_title,
            text = R.string.no_internet_error_text,
            image = R.drawable.ic_alert_no_internet,
            action = { it.dismiss() }
        )
    }

    protected open fun observeState(state: State) {
        // override when observing
    }

    protected open fun observeEffect(effect: Effect) {
        // override when observing
    }

    protected open fun showNoInternet() {
        if (noInternetDialog?.isAdded == false)
            noInternetDialog?.show("internet-error-dialog")
    }

    protected open fun showBackEndError() {
        BaseBottomSheetDialog.build(action = {
            it.dismiss()
        }).show("general-error-dialog")
    }

    protected open fun showError(
        message: Int,
        title: Int? = R.string.unknown_error_title,
        image: Int? = R.drawable.ic_alert_general
    ) {
        BaseBottomSheetDialog.build(
            text = message,
            title = title ?: R.string.unknown_error_title,
            image = image ?: R.drawable.ic_alert_general,
            action = {
                it.dismiss()
            }
        ).show("show-error-dialog")
    }

    protected open fun showLoading() {
        if (loadingDialog?.isAdded == false) loadingDialog?.show()
    }

    protected open fun hideLoading() {
        if (loadingDialog?.isAdded == true) loadingDialog?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        bindViews(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::observeState)
        viewModel.effect.observe(viewLifecycleOwner, ::observeEffect)
        viewModel.commonEffect.observe(viewLifecycleOwner) {
            when (it) {
                is NoInternet -> showNoInternet()
                is BackEndError -> showBackEndError()
                is UnknownError -> showBackEndError()
                is MessageError -> showError(it.messageId, it.titleId, it.imageId)
                is ForceLogout -> handleForceLogout()
                else -> error("Unknown BaseEffect: $it")
            }
        }

        viewModel.navigationCommands.observe(viewLifecycleOwner) { command ->
            when (command) {
                is NavigationCommand.To -> {
                    command.extras?.let { extras ->
                        findNavController().navigate(
                            command.directions,
                            extras
                        )
                    } ?: run {
                        findNavController().navigate(
                            command.directions
                        )
                    }
                }
                is NavigationCommand.BackTo -> findNavController().getBackStackEntry(command.destinationId)
                is NavigationCommand.Back -> findNavController().popBackStack()
            }
        }
    }

    protected open fun handleForceLogout() {
        (activity as? LogoutHandler)?.handleLogout()
    }

    protected fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(requireContext(), colorRes)
    }

    protected fun <T : DialogFragment> T.show(tag: String? = null): T {
        this.show(this@BaseFragment.parentFragmentManager, tag)
        return this
    }

    open fun onNewIntent(intent: Intent?) {}

    protected fun themeColor(@AttrRes id: Int, alternative: Int = Color.TRANSPARENT): Int {
        val typedValue = TypedValue()
        val theme = context?.theme ?: return alternative
        theme.resolveAttribute(id, typedValue, true)
        return typedValue.data
    }

    protected fun updateBoldSpanWithBoldFont(text: CharSequence, color: Int): SpannableString {
        val new = SpannableString(text)
        val spans = new.getSpans(0, new.length, StyleSpan::class.java)
        for (boldSpan in spans) {
            val start: Int = new.getSpanStart(boldSpan)
            val end: Int = new.getSpanEnd(boldSpan)
            new.setSpan(TypefaceSpan("gilroy_bold"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            new.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return new
    }

    protected fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(viewLifecycleOwner, block)
    }

    protected fun hideKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

    protected fun showKeyboard() {
        if (!this.isAdded) return

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusedView = requireActivity().currentFocus ?: View(requireActivity())
        imm.showSoftInput(focusedView, 0)
    }

    protected fun toMain() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().startActivity(intent)
    }
}