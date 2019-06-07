package hii.max.messagestore.dao.pgsql

import hii.max.messagestore.dao.MessageDao
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not contain`
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres

@Ignore("ช้ามาก")
class PostgresqlMessageDaoTest {
    val pgsql = EmbeddedPostgres()
    lateinit var dao: MessageDao

    @Before
    fun setUp() {
        val url = pgsql.start()
        dao = PostgresqlMessageDao(url, "postgres", "postgres")
    }

    @After
    fun tearDown() {
        pgsql.stop()
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
