package hii.max.messagestore.dao

import org.jetbrains.exposed.sql.Database

class PostgresqlDao(pgUrl: String, username: String, password: String) {
    init {
        Database.connect(
            url = pgUrl,
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }
}
