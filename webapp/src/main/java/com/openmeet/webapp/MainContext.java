package com.openmeet.webapp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Initializes the DataSource for the Db Connection just once in the lifetime
 * of the application when it starts up.
 */
@WebListener
public class MainContext implements ServletContextListener {

  private static final Logger logger = Logger.getLogger(MainContext.class.getName());

  public void contextInitialized(ServletContextEvent sce) {
    ServletContext sc = sce.getServletContext();
    DataSource ds;
    Context initCtx;

    try {
      initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      ds = (DataSource) envCtx.lookup("jdbc/OpenMeet");
      sc.setAttribute("DataSource", ds);
    } catch (NamingException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

  public void contextDestroyed(ServletContextEvent sce) {

    ServletContext cont = sce.getServletContext();
    cont.removeAttribute("DataSource");
  }
}
