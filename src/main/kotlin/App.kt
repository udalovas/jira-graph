package app

import net.wonderbeat.WebJiraGource

fun main(args : Array<String>) {
    val jgource = WebJiraGource().start("https://open.jira.com")
}
