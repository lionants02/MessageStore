package hii.max.messagestore.service

import hii.max.messagestore.getLogger
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/util")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PingResource {
    @Context
    private var context: SecurityContext? = null

    private val logger = getLogger()

    @GET
    @Path("/ping/{message:(.+)}")
    fun ping(@PathParam("message") message: String): String {
        logger.info("Ping message $message")
        return message
    }
}
