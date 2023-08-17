package com.resnik.crack

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val parser = DefaultParser()
            val options = Options().apply {
                addOption("f", "file", true, "valid path to a .7z file.")
                addOption("c", "characters", false, "characters to use")
                addOption("min", "minLength", false, "minimum password length (default=3).")
                addOption("max", "maxLength", false, "maximum password length (default=10).")
                addOption("print", "print", false, "output passwords to console.")
                addOption("h", "help", false, "displays the help screen.")
                addOption("7zip", "7zipLocation", false, "location of 7zip on machine (default=C:\\Program Files\\7-Zip)")
                addOption("passwordsFile", "passwordsFile", true, "location of passwords to use")
            }
            try {
                val line = parser.parse(options, args)
                if (line.hasOption("help")) {
                    printHelp(options)
                    exitProcess(0)
                }

                if (!line.hasOption("file")) throw IllegalStateException()
                val targetFilePath = line.getOptionValue("file").replace("\"", "")
                if (!targetFilePath.endsWith(".7z")) {
                    println("Cannot crack file, only 7z files are supported.")
                    exitProcess(1)
                }
                val file = File(targetFilePath)
                if (!file.exists()) {
                    println("File does not exist at location: ${file.absolutePath} \nExiting...")
                    exitProcess(1)
                }

                val characters = line.getOptionValue("c", Characters.all)
                val minLength = line.getOptionValue("min", "3").toIntOrNull()
                if (minLength == null || minLength <= 0) {
                    println("Min length must be a positive integer.")
                    exitProcess(1)
                }
                val maxLength = line.getOptionValue("max", "10").toIntOrNull()
                if (maxLength == null || maxLength < minLength) {
                    println("Max length must be a positive integer and greater than or equal to min length.")
                    exitProcess(1)
                }
                val print = line.hasOption("print")
                val location7zip = line.getOptionValue("7zip", "C:\\Program Files\\7-Zip")

                val locationPasswords = line.getOptionValue("passwordsFile", null)

                val arguments = CrackArguments(
                    characters = characters,
                    targetFilePath = targetFilePath,
                    minLength = minLength,
                    maxLength = maxLength,
                    print = print,
                    location7zip = location7zip,
                    passwordFileLocation = locationPasswords
                )
                val has7zip = CrackerHelper.check7zip(arguments)

                if (!has7zip) {
                    println("7zip isn't added to the PATH.")
                    println("Download 7zip here: https://www.7-zip.org/download.html")
                    println("Exiting program...")
                    exitProcess(1)
                } else {
                    println("Found 7zip location at: $location7zip")
                }

                println("Proceeding with arguments: $arguments ...")
                val cracker7z = CrackerFactory.spreadCheese(arguments)
                cracker7z.run()
            } catch (ex: Exception) {
                println("Error parsing command line arguments. Please consult the help menu below.")
                error(ex.message ?: ex.toString())
                printHelp(options)
            }
        }

        private fun printHelp(options: Options) {
            println("7z Cracker Help Menu:")
            options.options.forEach { option ->
                println("--${option.opt} / --${option.longOpt} : ${option.description} : required=${option.hasArg()}")
            }
        }
    }

}