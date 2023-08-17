package com.resnik.crack

import java.util.concurrent.TimeUnit

object CrackerHelper {

    fun check7zip(arguments: CrackArguments): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("${arguments.location7zip}\\7z.exe")
            process.waitFor(60, TimeUnit.SECONDS)
            val exitCode = process.exitValue()
            exitCode == 0
        } catch (ex: Exception) {
            false
        }
    }

}