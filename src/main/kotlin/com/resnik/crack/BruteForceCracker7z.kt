package com.resnik.crack

import kotlinx.coroutines.*
import kotlinx.coroutines.Runnable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BruteForceCracker7z(val arguments: CrackArguments) : Runnable {

    override fun run() {
        val dispatcher = Executors.newFixedThreadPool(100).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)
        var found = false
        (arguments.minLength .. arguments.maxLength).forEach { currentLength ->
            println("Working on length: $currentLength...")
            val incrementer = Incrementer(currentLength, arguments.characters.length)
            val jobs = mutableListOf<Job>()
            while(incrementer.hasNext() && !found) {
                val currPassword = incrementer.parse(arguments.characters)
                val job = scope.launch(dispatcher) {
                    if (arguments.print) println("Current password: $currPassword")
                    val process = Runtime.getRuntime()
                        .exec("${arguments.location7zip}\\7z.exe t \"${arguments.targetFilePath}\" -p\"$currPassword\"")
                    process.waitFor(60, TimeUnit.SECONDS)
                    val exitCode = process.exitValue()
                    if (exitCode == 0) {
                        println("Found password:$currPassword")
                        found = true
                    }
                }
                jobs.add(job)
                incrementer.increment()
                if (incrementer.arrayRepresentation[0] == 0) {
                    runBlocking(dispatcher) { jobs.joinAll() }
                }
            }
            runBlocking(dispatcher) { jobs.joinAll() }
        }
    }


}