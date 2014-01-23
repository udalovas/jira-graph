package net.wonderbeat

import reactor.core.composable.Stream
import com.atlassian.jira.rest.client.IssueRestClient
import com.atlassian.jira.rest.client.domain.Issue
import com.atlassian.jira.rest.client.SearchRestClient
import reactor.core.composable.Deferred

trait Jiraph<T, K> {

    fun start(source: T): Stream<K>
}

public class JiraJiraph(val deferred: Deferred<Issue, Stream<Issue>>,
                        val searchClient: SearchRestClient,
                        val issueClient: IssueRestClient): Jiraph<String, Issue> {

    /**
     * Accepts project name as source
     */
    override fun start(source: String): Stream<Issue> {
        searchClient.searchJql("project = " + source)!!.done {
            it!!.getIssues()!!.forEach {
                issueClient.getIssue(it.getKey())!!.done {
                    deferred.accept(it)
                }
            }
        }
        return deferred.compose()!!
    }
}
