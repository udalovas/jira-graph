package app

import net.wonderbeat.JiraJiraph
import reactor.core.Environment
import com.lmax.disruptor.BlockingWaitStrategy

fun main(args : Array<String>) {
    val env = Environment()
    val jgource = JiraJiraph(env).start("https://open.jira.com")
}
