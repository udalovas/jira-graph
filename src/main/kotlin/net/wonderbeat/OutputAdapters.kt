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


fun<T> JiraGource<T>.withFileAdapter(fileName: String) {
    val self = this
    object: JiraGource<T> {
        override fun start(source: T): InputStream {
            val destination = FileOutputStream(fileName).getChannel();
            val out = self.start(source)
            return out
        }
    }
}
