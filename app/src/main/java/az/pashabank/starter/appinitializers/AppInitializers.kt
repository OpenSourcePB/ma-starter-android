package az.pashabank.starter.appinitializers

import android.app.Application

/**
* Initializer to be called in MainApps's onCreate() method
*/
open class AppInitializers(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}
