package com.otakusenpai.kps.plugin

import com.otakusenpai.kps.api.PluginInterface
import com.otakusenpai.kps.exception.NullArgumentException
import com.otakusenpai.kps.exception.PluginNotFoundException
import com.otakusenpai.kps.jar.loader.DefaultJarLoader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class DefaultPluginManager(): AbstractPluginManager() {

    override fun addPlugin(name: String?, loader: DefaultJarLoader?): DefaultJarLoader {
        if (name == null) throw NullArgumentException("name")
        if (loader == null) throw NullArgumentException("loader")
        plugins[name] = loader
        return loader
    }

    @Throws(FileNotFoundException::class)
    override fun addPlugin(name: String?, jar: File, vararg dependencies: File): DefaultJarLoader {
        if (name == null)
            throw NullArgumentException("name")
        loader.loadPlugin(jar)
        for (f in dependencies)
            loader.loadPlugin(jar)
        plugins[name] = loader
        return loader
    }

    @Throws(FileNotFoundException::class, ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    override fun addPlugin(name: String, jar: File, clazz: String, vararg dependencies: File): PluginInterface {
        val loader = addPlugin(name, jar, *dependencies)
        return loader.createInstance(clazz)
    }

    @Throws(IOException::class)
    override fun removePlugin(name: String?) {
        try {
            if (name == null)
                throw NullArgumentException("name")
            var e: IOException? = null
            val loader = plugins[name]

            if (loader != null) {
                try {
                    loader.unloadPlugins()
                } catch (_e: IOException) {
                    e = _e
                }
            }
            plugins.remove(name)
            System.gc()
            if (e != null)
                throw e
        } catch(e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun unloadPlugins() {
        try {
            var e: IOException? = null
            for (loader in plugins.values) {
                try {
                    loader.unloadPlugins()
                } catch (ex: IOException) {
                    e = ex
                }

            }
            plugins.clear()
            System.gc()
            if (e != null)
                throw e
        } catch(e: Throwable) {
            e.printStackTrace()
        }
    }

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    override fun createInstance(plugin: String?, clazz: String?): PluginInterface {
        if (plugin == null) throw NullArgumentException("plugin")
        if (clazz == null) throw NullArgumentException("clazz")
        if (!plugins.containsKey(plugin))
            throw PluginNotFoundException(plugin)

        val loader = plugins[plugin]
        return loader!!.createInstance(clazz)
    }

    override fun getInstances(): List<PluginInterface> {
        val instances = ArrayList<PluginInterface>(plugins.size)
        for (loader in plugins.values) {
            instances.addAll(loader.getInstances())
        }
        return Collections.unmodifiableList(instances)
    }

    override fun destroyInstances() {
        for (loader in plugins.values)
            loader.unloadPlugins()
    }

}