package app

import reactor.core.Environment
import reactor.core.composable.spec.Streams
import com.atlassian.jira.rest.client.domain.Issue
import net.wonderbeat.JiraJiraph
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler
import java.net.URI

fun main(args : Array<String>) {
    val env = Environment()
    val stream = Streams.defer<Issue>()!!.env(env)!!.get()!!
    val jFactory = AsynchronousJiraRestClientFactory()
    val jClient = jFactory.create(URI("url"), AnonymousAuthenticationHandler())!!
    JiraJiraph(stream, jClient.getSearchClient()!!, jClient.getIssueClient()!!).start("wow")
}
