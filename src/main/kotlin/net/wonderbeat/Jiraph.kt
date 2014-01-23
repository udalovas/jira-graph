package net.wonderbeat

import com.atlassian.jira.rest.client.domain.BasicProject
import com.atlassian.util.concurrent.Effect
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler
import java.net.URI
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import org.joda.time.DateTime
import reactor.core.composable.Stream
import reactor.core.Environment
import reactor.core.composable.spec.Streams

trait Jiraph<T> {

    fun start(source: T): Stream<JiraAction>
}

public class JiraJiraph(val env: Environment): Jiraph<String> {

    override fun start(source: String): Stream<JiraAction> {
        val deferr = Streams.defer<JiraAction>()!!.env(env)!!.get()
        val factory = AsynchronousJiraRestClientFactory()
        val client = factory.create(URI(source), AnonymousAuthenticationHandler())
        client!!.getProjectClient()!!.getAllProjects()!!.done( Effect<Iterable<BasicProject>> {
            it!!.forEach {
                print((DateTime().getMillis() * 10000).toString() + "|wonderbeat|A|" + it.getName() + "\n")

            }
        } )

        return deferr!!.compose()!!
    }
}
