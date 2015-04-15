package org.specspike.todo.reminders.boundary;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 *
 * @author airhacks.com
 */
public class ToDosResourceIT {

    private Client client;
    private WebTarget tut;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target("http://localhost:8080/todo/resources/todos");
    }

    @Test
    public void crud() {
        //find all
        JsonArray allReminders = this.tut.request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertNotNull(allReminders);
        //create new
        final String description = "discuss the app";
        JsonObject newToDo = Json.createObjectBuilder().
                add("description", description).
                build();
        Response newToDoResponse = this.tut.
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(newToDo));
        assertThat(newToDoResponse.getStatus(), is(201));

        String location = newToDoResponse.getHeaderString("Location");
        assertNotNull(location);
        //find existing with id
        JsonObject foundReminder = this.client.target(location).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonObject.class);
        System.out.println("foundReminder = " + foundReminder);
        assertNotNull(foundReminder);
        assertThat(foundReminder.getString("description"), is(description));
        assertNotNull(foundReminder.getString("createdBy"));
        assertTrue(foundReminder.get("changedBy") == null || foundReminder.isNull("changedBy"));
        //find all again
        //find all
        allReminders = this.tut.request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertNotNull(allReminders);
        assertThat(allReminders, hasItem(foundReminder));

        final String updatedDescription = "implement Java EE 8 features";
        //update
        JsonObject updated = Json.createObjectBuilder().
                add("id", foundReminder.getInt("id")).
                add("description", updatedDescription).
                add("createdBy", foundReminder.getString("createdBy")).
                build();
        Response updatedToDoResponse = this.tut.
                request(MediaType.APPLICATION_JSON).
                put(Entity.json(updated));

        assertThat(updatedToDoResponse.getStatus(), is(200));
        JsonObject updatedToDo = updatedToDoResponse.readEntity(JsonObject.class);
        assertThat(updatedToDo.getString("createdBy"), is(foundReminder.getString("createdBy")));
        assertNotNull(updatedToDo.getString("changedBy"));
        assertThat(updatedToDo.getString("description"), is(updatedDescription));

        //delete
        Response deleteResponse = this.tut.path(String.valueOf(foundReminder.getJsonNumber("id").longValue())).
                request().
                delete();
        assertThat(deleteResponse.getStatus(), is(204));

        //should no more exist
        allReminders = this.tut.request().
                accept(MediaType.APPLICATION_JSON).
                get(JsonArray.class);
        assertNotNull(allReminders);
        assertThat(allReminders, not(hasItem(foundReminder)));

        //find existing with id
        Response deletedResponse = this.client.target(location).
                request().
                accept(MediaType.APPLICATION_JSON).
                get();
        assertThat(deletedResponse.getStatus(), is(204));
    }

}
