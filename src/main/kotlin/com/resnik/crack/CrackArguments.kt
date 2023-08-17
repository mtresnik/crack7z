package com.resnik.crack

data class CrackArguments(val targetFilePath : String,
                          val characters: String = Characters.all,
                          val minLength: Int = 3,
                          val maxLength: Int = 10,
                          val print: Boolean = false,
                          val location7zip: String="C:\\Program Files\\7-Zip",
                          val passwordFileLocation: String? = null
)