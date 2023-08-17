package com.resnik.crack

class Incrementer {

    val size: Int
    val base: Int
    val arrayRepresentation: IntArray

    constructor(size: Int, base: Int, start: IntArray) {
        this.size = size
        this.base = base
        this.arrayRepresentation = if(size != start.size) IntArray(size) else start
    }

    constructor(size: Int, base: Int) : this(size, base, IntArray(size))

    fun hasNext(): Boolean {
        return hasNext(0)
    }

    fun hasNext(index: Int): Boolean {
        if(index >= arrayRepresentation.size) return false
        if (arrayRepresentation[index] <  base - 1) {
            return true
        } else {
            return hasNext(index + 1)
        }
    }

    fun increment() {
        increment(0)
    }

    fun increment(index: Int){
        if (index >= arrayRepresentation.size) {
            this.arrayRepresentation.indices.forEach { index -> this.arrayRepresentation[index] = 0 }
            return
        }
        if(arrayRepresentation[index] < base - 1) {
            arrayRepresentation[index]++
        } else {
            arrayRepresentation[index] = 0
            increment(index + 1)
        }
    }

    fun <T> parse(array : Array<T>) : List<T> {
        return this.arrayRepresentation.map { array[it] }
    }

    fun parse(string : String): String {
        return String(parse(string.toCharArray().toTypedArray()).toCharArray())
    }

}