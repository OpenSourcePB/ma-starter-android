package az.pashabank.presentation.flow.main.login

import az.pashabank.domain.usecase.login.LoginUseCase
import az.pashabank.presentation.base.BaseViewModel
import az.pashabank.presentation.extensions.isValidEmail

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<Nothing, LoginEffect>() {


    fun validateInput(email: String, password: String) {
        val isValidEmail = email.isValidEmail()
        val isValidPassword = password.isNotEmpty()
        when {
            isValidEmail && isValidPassword -> login(email, password)
            else -> postEffect(LoginEffect.InvalidParams)
        }
    }

    private fun login(email: String, password: String) {
        loginUseCase.launch(LoginUseCase.Param(email, password)) {
            onSuccess = {authorized->
                if (authorized) {
                    postEffect(LoginEffect.Authorized)
                } else {
                    postEffect(LoginEffect.NotAuthorized)
                }
            }
        }
    }
}

sealed interface LoginEffect {
    object Authorized : LoginEffect
    object NotAuthorized : LoginEffect
    object InvalidParams : LoginEffect
}