package net.wonderbeat

import java.io.OutputStream
import org.apache.http.impl.client.cache.FileResource
import java.io.FileOutputStream
import java.io.PipedOutputStream
import java.io.InputStream
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.StandardOpenOption
import java.nio.file.Path
import java.nio.file.FileSystems
import java.nio.file.OpenOption
import java.nio.file.Paths
import reactor.core.composable.Stream
import reactor.core.composable.Composable
import reactor.function.Consumer
import reactor.event.Event
import java.io.FileWriter


fun<T> Jiraph<T>.withFileAdapter(fileName: String) {
    val self = this
    object: Jiraph<T> {
        override fun start(source: T): Stream<JiraAction> {
            val out = self.start(source)
            val file = FileWriter(fileName)
            out.consume(Consumer<JiraAction> {
                file.write("event")
            })
            return out
        }
    }
}

fun<T> Jiraph<T>.withStdOutAdapter() {
    val self = this
    object: Jiraph<T> {
        override fun start(source: T): Stream<JiraAction> {
            val out = self.start(source)
            out.consume(Consumer<JiraAction> {
                println(it)
            })
            return out
        }
    }
}
