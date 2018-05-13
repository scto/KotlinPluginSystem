package com.otakusenpai.kps.plugin

import com.otakusenpai.kps.api.PluginInterface
import com.otakusenpai.kps.exception.NullArgumentException
import com.otakusenpai.kps.exception.PluginNotFoundException
import com.otakusenpai.kps.jar.loader.DefaultJarLoader

import java.io.IOException
import java.io.FileNotFoundException
import java.io.File
import java.util.*

abstract class AbstractPluginManager() {
    protected var plugins = HashMap<String, DefaultJarLoader>()
    val loader = DefaultJarLoader()

    abstract fun addPlugin(name: String?, loader: DefaultJarLoader?): DefaultJarLoader

    @Throws(FileNotFoundException::class)
    abstract fun addPlugin(name: String?, jar: File, vararg dependencies: File): DefaultJarLoader

    @Throws(FileNotFoundException::class, ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    abstract fun addPlugin(name: String, jar: File, clazz: String, vararg dependencies: File): PluginInterface

    @Throws(IOException::class)
    abstract fun removePlugin(name: String?)

    abstract fun unloadPlugins()

    /**
     * Returns a PluginInterface instance.
     * @throws NullArgumentException when either plugin or clazz is null.
     * @throws PluginNotFoundException when plugins hashmap doesn't have
     * the plugin asked for.
     */

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    abstract fun createInstance(plugin: String?, clazz: String?): PluginInterface

    /**
     * Returns instances of all the plugins
     */

    abstract fun getInstances(): List<PluginInterface>

    /**
     * Destroys all instances of PluginInterface
     */
    abstract fun destroyInstances()
}
