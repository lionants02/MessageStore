package hii.max.messagestore.dao.pgsql

import org.jetbrains.exposed.sql.Table

object Message : Table() {
    val time = datetime("time")
    val message = varchar("messaged", 255)
}
