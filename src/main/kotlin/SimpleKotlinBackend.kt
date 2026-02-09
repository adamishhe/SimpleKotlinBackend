package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleKotlinBackend

fun main(args: Array<String>) {
    runApplication<SimpleKotlinBackend>(*args)
}
