package com.otakusenpai.kps.exception

import java.io.File
import java.util.jar.JarFile

class ManifestNotFoundException(file: File) : Exception(file.absolutePath)
