package net.wonderbeat

import java.io.InputStream
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import com.atlassian.jira.rest.client.domain.BasicProject
import com.atlassian.util.concurrent.Effect
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler
import java.net.URI
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import org.joda.time.DateTime

trait JiraGource<T> {

    fun start(source: T): InputStream
}

public class WebJiraGource: JiraGource<String> {

    override fun start(source: String): InputStream {
        val out = ByteInputStream()
        val factory = AsynchronousJiraRestClientFactory()
        val client = factory.create(URI(source), AnonymousAuthenticationHandler())
        client!!.getProjectClient()!!.getAllProjects()!!.done( Effect<Iterable<BasicProject>> {
            it!!.forEach {
                print((DateTime().getMillis() * 10000).toString() + "|wonderbeat|A|" + it.getName() + "\n")

            }
        } )

        return out
    }
}
