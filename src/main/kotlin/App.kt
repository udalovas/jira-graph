package app

import net.wonderbeat.WebJiraGource
import reactor.core.Environment
import com.lmax.disruptor.BlockingWaitStrategy

fun main(args : Array<String>) {
    val env = Environment()
    val jgource = WebJiraGource(env).start("https://open.jira.com")
}
