package com.otakusenpai.kps.jar.loader;

import com.otakusenpai.kps.api.PluginInterface;
import com.otakusenpai.kps.exception.NullArgumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Policy;
import java.util.Collections;
import java.util.List;

public class DefaultJarLoader extends AbstractJarLoader {

    @Override
    public void loadPlugin(File file) {
        try {
            if (file == null)
                throw new NullArgumentException(file.toString());
            if (!file.isFile())
                throw new FileNotFoundException(file.toString());
            if(jarPaths.contains(file.getAbsolutePath()))
                return;
             loader.addURL(file.toURI().toURL());
             jarPaths.add(file.getAbsolutePath());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPlugin(Path path) {
        try {
            if (path == null)
                if (path == null)
                    throw new NullPointerException(path.toString());
            if (!path.toFile().isFile())
                throw new FileNotFoundException(path.toString());
            if(jarPaths.contains(path.toFile().getAbsolutePath()))
                return;
            loader.addURL(path.toFile().toURI().toURL());
            jarPaths.add(path.toFile().getAbsolutePath());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unloadPlugins() {
        pluginList.clear();
        System.gc();
    }

    @Override
    public void loadPlugins(File file) throws MalformedURLException {
        File[] files = file.listFiles();
        for(File file1 : files) {
            if(file1.isFile() && file1.getName().endsWith("jar")) {
                loader.addURL(file1.toURI().toURL());
                jarPaths.add(file1.getAbsolutePath());
            }
        }
    }

    @Override
    public void loadPlugins(String path) {
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(path))) {
            for(Path p: dirStream) {
                jarPaths.add(p.toString());
                loader.addURL(p.toFile().toURI().toURL());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PluginInterface createInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        PluginInterface instance;
        try {
            if (className == null)
                throw new NullArgumentException("className");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instance = (PluginInterface) loader.loadClass(className).newInstance();
            pluginList.add(instance);
            return instance;
        }
    }

    public List<PluginInterface> getInstances() {
        return Collections.unmodifiableList(pluginList);
    }

}
