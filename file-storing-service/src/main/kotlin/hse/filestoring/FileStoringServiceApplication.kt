package hse.filestoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FileStoringServiceApplication

fun main(args: Array<String>) {
    runApplication<FileStoringServiceApplication>(*args)
}