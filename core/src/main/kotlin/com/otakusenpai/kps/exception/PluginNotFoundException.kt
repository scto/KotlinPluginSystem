package com.otakusenpai.kps.exception

class PluginNotFoundException(pluginName: String) : RuntimeException("No such plugin: $pluginName")
