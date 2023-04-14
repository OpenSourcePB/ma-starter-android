package az.pashabank.starter.test.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import az.pashabank.starter.MainApp
import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.CucumberOptions
import java.io.File

class CustomCucumberAndroidJUnitRunner : CucumberAndroidJUnitRunner() {

    override fun onCreate(bundle: Bundle) {
        bundle.putString("plugin", getPluginConfigurationString())
        // we programmatically create the plugin configuration
        // it crashes on Android R without it
        File(getAbsoluteFilesPath()).mkdirs()
        super.onCreate(bundle)
    }

    /**
     * Since we want to check out the external storage directory programmatically, we create the plugin configuration
     * here, instead of the [CucumberOptions] annotation.
     *
     * @return the plugin string for the configuration, which contains XML, HTML, and JSON paths
     */
    private fun getPluginConfigurationString(): String {
        val cucumber = "cucumber"
        val separator = "--"
        return "junit:" + getCucumberXml(cucumber) + separator +
                "html:" + getCucumberHtml(cucumber)
    }

    private fun getCucumberHtml(cucumber: String) = getAbsoluteFilesPath() + "/" + cucumber + ".html"

    private fun getCucumberXml(cucumber: String) = getAbsoluteFilesPath() + "/" + cucumber + ".xml"

    /**
     * The path which is used for the report files.
     * @return the absolute path for the report files
     */
    private fun getAbsoluteFilesPath(): String {
        val directory = targetContext.getExternalFilesDir(null)
        return File(directory, "reports").absolutePath
    }

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application? {
        return super.newApplication(cl, MainApp::class.java.name, context)
    }

}