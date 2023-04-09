package az.pashabank.starter

import android.annotation.SuppressLint
import android.app.Application
import az.pashabank.starter.appinitializers.AppInitializers
import az.pashabank.starter.di.appComponent
import org.koin.core.context.startKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

open class MainApp : Application() {

    private val initializers: AppInitializers by inject()

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(androidContext = this@MainApp)
            properties(
                mapOf(
                    "host" to "https://6214983b89fad53b1f18329c.mockapi.io/",
                    "isDebug" to (BuildConfig.DEBUG || BuildConfig.VERSION_NAME.contains("-dev")).toString(),
                )
            )
            modules(appComponent)
        }

        initializers.init(this)
    }

}
