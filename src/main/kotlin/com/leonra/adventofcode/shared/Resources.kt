package com.leonra.adventofcode.shared

import java.io.BufferedReader
import java.io.InputStreamReader

/** Reads a given resource at [path] line by line, calling [doOnLine] on each. */
fun readResource(path: String, doOnLine: (String) -> Unit) {
    val resource = requireNotNull(object {}.javaClass.getResource(path))
    val reader = BufferedReader(InputStreamReader(resource.openStream()))
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        doOnLine(requireNotNull(line))
    }
}