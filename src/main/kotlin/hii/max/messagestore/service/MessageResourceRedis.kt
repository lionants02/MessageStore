package hii.max.messagestore.service

import hii.max.messagestore.dao.MessageDao
import hii.max.messagestore.dao.redis.RedisMessageDao
import hii.max.messagestore.getLogger
import redis.clients.jedis.HostAndPort
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/message/redis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MessageResourceRedis {
    companion object {
        private val host = System.getenv("RE_HOST")
        private val port = System.getenv("RE_PORT").toInt()
        private val logger = getLogger()

        val messageDao: MessageDao by lazy { RedisMessageDao(setOf(HostAndPort(host, port))) }
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
