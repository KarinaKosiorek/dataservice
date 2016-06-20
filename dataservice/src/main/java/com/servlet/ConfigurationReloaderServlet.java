package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.configuration.ApplicationConfiguration;
import com.configuration.controller.ServiceConfigurationController;
import com.configuration.xml.manager.ServiceConfigurationXmlManager;

/**
 * To reload configuration, request GET or POST need to be sent to the server with "xmlconfiguration" parameter having
 * text which is xml tags as in /target/dataservice/configuration/configuration.xml file.
 *
 */
@WebServlet(urlPatterns = "/reloadConfiguration")
public class ConfigurationReloaderServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ServletConfig servletConfig = getServletConfig();
    ServletContext servletContext = servletConfig.getServletContext();

    ServiceConfigurationController serviceConfigurationController = (ServiceConfigurationController) servletContext
        .getAttribute(ApplicationConfiguration.CONFIG_SERVLETCONTEXT_ATTRIBUTE);

    if (serviceConfigurationController == null) {
      // DataServiceServlet will initialize and set serviceConfigurationController in servlet context
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/getData");
      requestDispatcher.forward(request, response);

      // try again
      serviceConfigurationController = (ServiceConfigurationController) servletContext
          .getAttribute(ApplicationConfiguration.CONFIG_SERVLETCONTEXT_ATTRIBUTE);
    }

    // if serviceConfigurationController is set, reload configuration
    if (serviceConfigurationController != null) {
      String xmlConfiguration = request.getParameter("xmlconfiguration");
      if (xmlConfiguration != null) {
        ServiceConfigurationXmlManager.reloadConfiguration(xmlConfiguration, serviceConfigurationController);
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
