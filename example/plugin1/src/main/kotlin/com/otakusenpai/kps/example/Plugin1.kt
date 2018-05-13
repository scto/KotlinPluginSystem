package com.otakusenpai.kps.example

import com.otakusenpai.kps.api.PluginInterface
import com.otakusenpai.kps.config.PluginConfig
import java.io.FileInputStream
import java.nio.file.Paths
import java.util.Properties

class Plugin1: PluginInterface() {

    lateinit var config: PluginConfig

    override fun init(config: PluginConfig) {
        this.config = config
    }

    override fun load() {
        var properties = Properties();
        try {
            var path = Paths.get(config.pluginWorkDir, "additional.properties")

            properties.load(FileInputStream (path.toFile()))
            for (key in properties.stringPropertyNames()) {
                val value = properties.getProperty(key)
                println("The grade in $key is: $value")
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun unload() {
        println("Unloaded!")
    }
}