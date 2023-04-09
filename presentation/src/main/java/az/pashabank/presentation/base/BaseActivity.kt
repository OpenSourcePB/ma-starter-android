package az.pashabank.presentation.base

import android.content.Context
import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private val languageWrapper by inject<LanguageContextWrapper>()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(languageWrapper.applyLanguage(base))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach { fragment ->
            fragment.onActivityResult(requestCode, resultCode, data)
            fragment.childFragmentManager.fragments.forEach { childFragment ->
                childFragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is BaseFragment<*, *, *, *>) {
                fragment.onNewIntent(intent)
            }
            fragment.childFragmentManager.fragments.forEach { childFragment ->
                if (childFragment is BaseFragment<*, *, *, *>) {
                    childFragment.onNewIntent(intent)
                }
            }
        }
    }
}