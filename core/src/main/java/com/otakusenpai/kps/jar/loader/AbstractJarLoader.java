package com.otakusenpai.kps.jar.loader;

import com.otakusenpai.kps.api.PluginInterface;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractJarLoader {

    public AbstractJarLoader() {
        this.jarPaths = new ArrayList<String>();
        this.pluginList = new ArrayList<PluginInterface>();
        this.loader = new URLClassLoader();
    }


    abstract void loadPlugin(File file) throws MalformedURLException;
    abstract void loadPlugin(Path path);
    abstract void unloadPlugins();
    abstract void loadPlugins(File file) throws MalformedURLException;
    abstract void loadPlugins(String path);
    abstract PluginInterface createInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    protected List<String> jarPaths;
    protected List<PluginInterface> pluginList;
    protected URLClassLoader loader;
}
