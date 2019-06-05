package hii.max.messagestore.dao

import org.joda.time.DateTime

interface MessageDao {
    fun add(message: String)
    fun get(): Map<DateTime, String>
    fun remove(message: String)
    fun removeAll()
}
