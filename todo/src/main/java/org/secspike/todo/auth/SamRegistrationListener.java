package org.secspike.todo.auth;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.omnifaces.security.jaspic.authmodules.BasicAuthModule;
import org.omnifaces.security.jaspic.core.Jaspic;
import org.omnifaces.security.jaspic.listeners.BaseServletContextListener;

@WebListener
public class SamRegistrationListener extends BaseServletContextListener {
  
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	// Change here to TokenAuthModule if that's needed
        Jaspic.registerServerAuthModule(new BasicAuthModule(), sce.getServletContext());
    }
}