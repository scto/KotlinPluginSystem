package com.otakusenpai.kps.example

import com.otakusenpai.kps.config.PluginConfig
import java.io.File
import com.otakusenpai.kps.jar.JarProbe
import com.otakusenpai.kps.plugin.DefaultPluginManager


fun main(args: Array<String>) {
    try {
        val probe = JarProbe(File("build/plugins/plugin1-0.1.0.jar"))
        val pluginClass = probe.getManifestAttribute("pluginClass")
        if(pluginClass == null)
            throw NullPointerException("Plugin-Class attribute can't be null!")
        val pluginName = "TestPlugin"
        val pluginDir = "./build/plugins"

        val pm = DefaultPluginManager()

        val plugin = pm.addPlugin(pluginName, probe.file, pluginClass)
        plugin.init(PluginConfig(pluginDir))
        plugin.load()
        pm.removePlugin(pluginName) // Removing a plugin allows you to hotswap it or load it again later on
        // plugin.onUnload(); is automatically called thanks to DefaultPluginManager

        // This destroys the PluginManager for when you're done with it. This is crucial!
        pm.unloadPlugins()
    } catch(e: Throwable) {
        e.printStackTrace()
    }
}