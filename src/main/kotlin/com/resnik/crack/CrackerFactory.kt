package com.resnik.crack

import kotlinx.coroutines.Runnable
import java.io.File

object CrackerFactory {

    fun spreadCheese(args: CrackArguments) : Runnable {
        // Why not
        return if (args.passwordFileLocation == null
            || !File(args.passwordFileLocation).isFile
            || !File(args.passwordFileLocation).exists()) {
            println("Using brute force cracker. This is going to take a while.")
            BruteForceCracker7z(args)
        } else {
            FileCracker7z(args)
        }
    }

}