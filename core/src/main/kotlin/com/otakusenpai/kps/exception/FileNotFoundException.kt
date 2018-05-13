package com.otakusenpai.kps.exception

import java.io.File

class FileNotFoundException(path: File?) : java.io.FileNotFoundException(String.format("No such file found: %s", if (path == null) "path is null" else path.absolutePath))
