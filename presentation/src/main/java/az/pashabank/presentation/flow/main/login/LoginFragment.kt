package az.pashabank.presentation.flow.main.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import az.pashabank.starter.presentation.R
import az.pashabank.starter.presentation.databinding.FragmentLoginBinding
import az.pashabank.presentation.base.BaseFragment
import kotlin.reflect.KClass

class LoginFragment : BaseFragment<Nothing, LoginEffect, LoginViewModel, FragmentLoginBinding>() {

    override val vmClazz: KClass<LoginViewModel>
        get() = LoginViewModel::class
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate
    override val screenName: String
        get() = "login_page"

    override val bindViews: FragmentLoginBinding.() -> Unit = {
        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            viewModel.validateInput(email, password)
        }
    }

    override fun observeEffect(effect: LoginEffect) {
        when (effect) {
            LoginEffect.Authorized -> {
                findNavController().navigate(LoginFragmentDirections.toMain())
            }
            LoginEffect.InvalidParams -> showError(R.string.enter_valid_emai_and_password)
            LoginEffect.NotAuthorized -> showError(R.string.enter_valid_emai_and_password)
        }
    }


}