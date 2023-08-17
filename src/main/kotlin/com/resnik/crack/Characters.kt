package com.resnik.crack

object Characters {

    val lowercase = "abcdefghijklmnopqrstuvwxyz"
    val uppercase = lowercase.uppercase()
    val letters = lowercase + uppercase
    val numbers = "1234567890"
    val lettersNumbers = letters + numbers
    val symbols = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?`~"
    val all = lettersNumbers + symbols

}