package hii.max.messagestore.dao.pgsql

import hii.max.messagestore.dao.MessageDao
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class PostgresqlMessageDao(pgUrl: String, username: String, password: String) : MessageDao {
    init {
        Database.connect(
            url = pgUrl,
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }

    override fun add(message: String) {
        transaction {
            SchemaUtils.create(Message)
            Message.insert {
                it[time] = DateTime.now()
                it[Message.message] = message
            }
        }
    }

    override fun get(): Map<DateTime, String> {
        val output = hashMapOf<DateTime, String>()
        transaction {
            for (message in Message.selectAll()) {
                output[message[Message.time]] = message[Message.message]
            }
        }
        return output.toMap()
    }

    override fun remove(message: String) {
        transaction {
            Message.deleteWhere {
                Message.message like message
            }
        }
    }

    override fun removeAll() {
        transaction {
            Message.deleteAll()
        }
    }
}
