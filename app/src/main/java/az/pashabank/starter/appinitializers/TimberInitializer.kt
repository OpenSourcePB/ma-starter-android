package az.pashabank.starter.appinitializers

import android.app.Application
import az.pashabank.starter.BuildConfig
import timber.log.Timber

class TimberInitializer : AppInitializer {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
//                 Adds the line number to the tag
                override fun createStackElementTag(element: StackTraceElement) =
                    "${super.createStackElementTag(element)}:${element.lineNumber}"
            })
        }
    }
}
