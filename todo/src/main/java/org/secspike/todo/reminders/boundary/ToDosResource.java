package org.secspike.todo.reminders.boundary;

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.secspike.todo.reminders.entity.ToDo;

/**
 *
 * @author airhacks.com
 */
@Path("todos")
@Stateless
public class ToDosResource {

    @Inject
    ToDoManager manager;

    @PUT
    public Response save(ToDo input, @Context UriInfo info) {
        ToDo result = manager.update(input);
        if (input.isNew()) {
            URI uri = info.getAbsolutePathBuilder().path("/" + result.getId()).build();
            return Response.created(uri).build();
        } else {
            return Response.ok(result).build();
        }
    }

    @GET
    public List<ToDo> all() {
        return this.manager.all();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        this.manager.delete(id);
    }

    @GET
    @Path("{id}")
    public ToDo find(@PathParam("id") long id) {
        return this.manager.find(id);
    }

}
