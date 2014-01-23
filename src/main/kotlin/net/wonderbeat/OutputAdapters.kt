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
import net.wonderbeat.nodes.TimelineNode


fun Jiraph<TimelineNode, TimelineNode>.withFileAdapter(fileName: String) {
    val self = this
    object: Jiraph<TimelineNode, TimelineNode> {
        override fun start(source: TimelineNode): Stream<TimelineNode> {
            val out = self.start(source)
            val file = FileWriter(fileName)
            out.consume(Consumer<TimelineNode> {
                file.write("event")
            })
            return out
        }
    }
}

fun Jiraph<TimelineNode, TimelineNode>.withStdOutAdapter() {
    val self = this
    object: Jiraph<TimelineNode, TimelineNode> {
        override fun start(source: TimelineNode): Stream<TimelineNode> {
            val out = self.start(source)
            out.consume(Consumer<TimelineNode> {
                println(it)
            })
            return out
        }
    }
}
