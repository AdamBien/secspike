package org.secspike.todo.reminders.control;

import java.security.Principal;
import javax.inject.Inject;

/**
 *
 * @author airhacks.com
 */
public class AccessGuard {

    @Inject
    Principal principal;

    public boolean isPermitted() {
        //more sophisticated use cases tbd
        return "duke".equalsIgnoreCase(principal.getName());
    }

    public String getUserName() {
        return principal.getName();
    }

}
