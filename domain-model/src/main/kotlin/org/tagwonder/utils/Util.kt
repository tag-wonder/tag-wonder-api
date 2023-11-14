package org.tagwonder.utils

fun <T> List<T>.hasDuplicate(): Boolean {
    return this.size != this.distinct().size
}
