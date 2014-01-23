package net.wonderbeat.map.jira

import reactor.function.Function
import com.atlassian.jira.rest.client.domain.Issue
import org.joda.time.DateTime


val issueMapper = Function<Issue, net.wonderbeat.nodes.Issue> { input ->
    net.wonderbeat.nodes.Issue(input!!.getKey()!!, input.getCreationDate()!!)
}
