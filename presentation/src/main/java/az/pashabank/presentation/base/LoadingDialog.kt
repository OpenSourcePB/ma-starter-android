package az.pashabank.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import az.pashabank.starter.presentation.R
import az.pashabank.starter.presentation.databinding.DialogLoadingBinding
import az.pashabank.presentation.delegate.viewBinding

class LoadingDialog : DialogFragment() {

    private val binding by viewBinding(DialogLoadingBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_loading, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(TITLE)?.let {
            binding.title.text = it
        }
        arguments?.getString(TEXT)?.let {
            binding.text.text = it
        }
        isCancelable = false
    }

    companion object {
        private const val TITLE = "title"
        private const val TEXT = "text"

        fun build(title: String? = null, text: String? = null) = LoadingDialog().apply {
            arguments = bundleOf(TITLE to title, TEXT to text)
        }
    }
}