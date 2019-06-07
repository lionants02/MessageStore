package hii.max.messagestore.dao.redis

import com.github.fppt.jedismock.RedisServer
import hii.max.messagestore.dao.MessageDao
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not contain`
import org.junit.After
import org.junit.Before
import org.junit.Test
import redis.clients.jedis.HostAndPort

class RedisMessageDaoTest {
    private lateinit var server: RedisServer
    private lateinit var dao: MessageDao

    @Before
    fun setUp() {
        server = RedisServer.newRedisServer()
        server.start()
        dao = RedisMessageDao(setOf(HostAndPort(server.host, server.bindPort)))
    }

    @After
    fun tearDown() {
        server.stop()
    }

    @Test
    fun add() {
        dao.add("test1")
        dao.add("test2")
    }

    @Test
    fun get() {
        dao.add("dog")
        dao.add("cat")
        val get = dao.get()

        get.size `should equal` 2
        get.values `should contain` "dog"
        get.values `should contain` "cat"
    }

    @Test
    fun remove() {
        dao.add("dog")
        dao.add("cat")
        dao.remove("dog")
        val get = dao.get()

        get.size `should equal` 1
        get.values `should not contain` "dog"
        get.values `should contain` "cat"
    }

    @Test
    fun removeAll() {
        dao.add("dog")
        dao.add("cat")
        dao.removeAll()

        dao.get().size `should equal` 0
    }
}
