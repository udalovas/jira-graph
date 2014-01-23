package net.wonderbeat

import com.atlassian.jira.rest.client.IssueRestClient
import com.atlassian.jira.rest.client.SearchRestClient
import com.atlassian.jira.rest.client.domain.BasicIssue
import com.atlassian.jira.rest.client.domain.Issue
import com.atlassian.jira.rest.client.domain.SearchResult
import com.atlassian.util.concurrent.Promises
import reactor.core.Environment
import reactor.core.composable.Deferred
import reactor.core.composable.Stream
import reactor.core.composable.spec.Streams
import spock.lang.Specification

class JiraphTest extends Specification {

    Deferred<Issue, Stream<Issue>> deferred = Streams.defer().synchronousDispatcher().env(new Environment()).get()

    def 'Should request issues on method call'() {
        given:
        IssueRestClient issueClient = Mock()
        SearchRestClient searchClient = Mock()

        when:
        def jiraph = new JiraJiraph(deferred, searchClient, issueClient)
        jiraph.start('WOW DOGE')

        then:
        1 * searchClient.searchJql({ it.contains('WOW')}) >> Promises.rejected(new NoSuchMethodException(),
                SearchResult.class)
    }

    def 'Should query issues in project'() {
        given:
        IssueRestClient issueClient = Mock()
        SearchRestClient searchClient = Mock()

        when:
        def jiraph = new JiraJiraph(deferred, searchClient, issueClient)
        jiraph.start('WOW DOGE')

        then:
        1 * searchClient.searchJql(_) >> Promises.promise(new SearchResult(0, 20, 20, [new BasicIssue(null, 'key')]))
        1 * issueClient.getIssue('key') >> Promises.rejected(new NoSuchMethodException(), Issue.class)
    }

    def 'Should write queries to stream'() {
        given:
        IssueRestClient issueClient = Mock()
        SearchRestClient searchClient = Mock()
        Issue issue = Mock() // sorry, this should not be mocked. But Jira issue constructor contains more that 9000
        // fields

        when:
        def value = deferred.compose().tap()
        def jiraph = new JiraJiraph(deferred, searchClient, issueClient)
        def stream = jiraph.start('WOW DOGE')


        then:
        1 * issueClient.getIssue(_) >> Promises.promise(issue)
        1 * searchClient.searchJql(_) >> Promises.promise(new SearchResult(0, 20, 20, [new BasicIssue(null, 'key')]))
        assert value.get() != null
    }
}
