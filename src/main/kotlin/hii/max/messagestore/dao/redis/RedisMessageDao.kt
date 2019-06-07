package hii.max.messagestore.dao.redis

import hii.max.messagestore.dao.MessageDao
import hii.max.messagestore.getLogger
import org.joda.time.DateTime
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.Jedis

class RedisMessageDao(jedisClusterNodes: Set<HostAndPort>, val expireSec: Int) : MessageDao {
    companion object {
        val logger by lazy { RedisMessageDao.getLogger() }
    }

    // private val jc = JedisCluster(jedisClusterNodes)
    private val jc = Jedis(jedisClusterNodes.first())

    override fun add(message: String) {
        jc.set(message, DateTime.now().toString())
        jc.expire(message, expireSec)
    }

    override fun get(): Map<DateTime, String> {
        val scan = jc.scan("0")
        val output = hashMapOf<DateTime, String>()
        scan.result.forEach {
            output[DateTime.parse(jc[it])] = it
        }
        return output
    }

    override fun remove(message: String) {
        jc.del(message)
    }

    override fun removeAll() {
        jc.flushDB()
    }
}
