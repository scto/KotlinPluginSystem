package com.otakusenpai.kps.exception

class NullArgumentException(param: String?) : IllegalArgumentException(String.format("Parameter \"%s\" cannot be null", param))
