package com.otakusenpai.kps.jar.loader;

import java.net.URL;

public class URLClassLoader extends java.net.URLClassLoader {

    public URLClassLoader() {
        super(new URL[0]);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

}