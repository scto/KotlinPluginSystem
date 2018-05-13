package com.otakusenpai.kps.api

import com.otakusenpai.kps.config.PluginConfig

abstract class PluginInterface {

    abstract fun init(config: PluginConfig)

    abstract fun load()

    abstract fun unload()

}