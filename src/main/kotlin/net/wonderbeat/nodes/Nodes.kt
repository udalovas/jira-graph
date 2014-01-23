package net.wonderbeat.nodes

import org.joda.time.DateTime

trait Node

open data class TimelineNode(name: String, time: DateTime): Node

data class Issue(name: String, time: DateTime): TimelineNode(name, time)
data class Comment(name: String, time: DateTime): TimelineNode(name, time)
data class Update(item: TimelineNode, name: String, time: DateTime): TimelineNode(name, time)
