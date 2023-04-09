package az.pashabank.presentation.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface BaseEffect

abstract class BaseUiError : BaseEffect
class NoInternet : BaseUiError()
class BackEndError : BaseUiError()
class UnknownError(val cause: Throwable) : BaseUiError()
class ForceLogout(val cause: Throwable) : BaseUiError()
class MessageError(@StringRes val messageId: Int, @StringRes val titleId: Int?=null, @DrawableRes val imageId: Int?=null) : BaseUiError()
