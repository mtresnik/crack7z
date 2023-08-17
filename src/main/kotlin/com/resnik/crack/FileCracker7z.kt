package com.resnik.crack

import kotlinx.coroutines.Runnable
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.util.concurrent.TimeUnit

class FileCracker7z(val args: CrackArguments) : Runnable {

    override fun run() {
        checkNotNull(args.passwordFileLocation)
        val inputStream = FileInputStream(File(args.passwordFileLocation))
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        try {
            var found = false
            while (!found && bufferedReader.ready()) {
                val currPassword = bufferedReader.readLine()
                if (args.print) println("Current Password: $currPassword")
                val process = Runtime.getRuntime()
                    .exec("${args.location7zip}\\7z.exe t \"${args.targetFilePath}\" -p\"$currPassword\"")
                process.waitFor(60, TimeUnit.SECONDS)
                val exitCode = process.exitValue()
                if (exitCode == 0) {
                    println("Found password:$currPassword")
                    found = true
                }
            }
        } catch (ex: Exception){
            error(ex.message ?: ex.toString())
        }

    }

}