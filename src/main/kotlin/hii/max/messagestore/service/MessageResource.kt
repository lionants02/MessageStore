package hii.max.messagestore.service

import hii.max.messagestore.dao.MessageDao
import hii.max.messagestore.dao.pgsql.PostgresqlMessageDao
import hii.max.messagestore.getLogger
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MessageResource {
    companion object {
        private val pgUrl = System.getenv("PG_URL")
        private val username = System.getenv("PG_USER")
        private val password = System.getenv("PG_PASSWORD")
        private val logger = getLogger()

        val messageDao: MessageDao by lazy { PostgresqlMessageDao(pgUrl, username, password) }
    }

    @PUT
    fun removeAdd(message: Map<String, String>) {
        require(message.size == 1)
        message.forEach { (key, value) ->
            messageDao.remove(key)
            messageDao.add(value)
        }
    }

    @DELETE
    fun remove() {
        messageDao.removeAll()
    }

    @POST
    fun insert(message: Map<String, String>) {
        require(message["message"] != null) { "Message null" }
        messageDao.add(message["message"]!!)
    }

    @GET
    fun get(): Map<String, String> {
        logger.info("Get message.")
        return messageDao.get().mapKeys {
            it.key.toString()
        }
    }
}
