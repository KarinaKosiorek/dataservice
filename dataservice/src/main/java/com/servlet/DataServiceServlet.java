package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.configuration.ApplicationConfiguration;
import com.configuration.ExitConfiguration;
import com.configuration.controller.ServiceConfigurationController;
import com.configuration.xml.manager.ServiceConfigurationXmlManager;
import com.response.ResponseXmlGenerator;
import com.response.ResponseXmlPattern;

@WebServlet(urlPatterns = "/getData")
public class DataServiceServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  // concurrently shared resources
  public ServiceConfigurationController serviceConfigurationController;
  public ResponseXmlPattern responseXmlPattern;

  public void init() throws ServletException {

    // initializing shared objects
    serviceConfigurationController = new ServiceConfigurationController();
    responseXmlPattern = new ResponseXmlPattern();

    // commented code to generate and save initial configuration file
    // setInitialServiceConfiguration();
    // ServiceConfigurationXmlManager.saveConfiguration(serviceConfigurationController);

    // loading configuration from .xml file
    ServiceConfigurationXmlManager.loadConfiguration(serviceConfigurationController);

    // setting service configuration controller to be accessed in application scope
    // service configuration controller
    ServletConfig servletConfig = getServletConfig();
    ServletContext servletContext = servletConfig.getServletContext();
    servletContext.setAttribute(ApplicationConfiguration.CONFIG_SERVLETCONTEXT_ATTRIBUTE, serviceConfigurationController);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String accountCode = request.getParameter(ApplicationConfiguration.ACCOUNTCODE_PARAMNAME);
    String targetDevice = request.getParameter(ApplicationConfiguration.TARGETDEVICE_PARAMNAME);
    String pluginVersion = request.getParameter(ApplicationConfiguration.PLUGINVERSION_PARAMNAME);

    // if any of parameters is missing quit from process
    if (accountCode == null || targetDevice == null || pluginVersion == null) {
      return;
    }

    // get result configuration
    ExitConfiguration responseConfiguration = serviceConfigurationController.getResponseConfiguration(accountCode, targetDevice,
        pluginVersion);

    // if responseConfiguration is null - that is configuration does not exists for input parameters, process will not
    // proceed and return empty repsponse
    if (responseConfiguration != null) {

      // get exit configuration: cluster, pingTime, code
      String cluster = responseConfiguration.getCluster();

      String pingTime = Integer.toString(responseConfiguration.getPingTime());

      // "practically" unique codes
      String code = UUID.randomUUID().toString();

      // object to generate response - it takes response format pattern
      ResponseXmlGenerator responseXmlGenerator = new ResponseXmlGenerator(responseXmlPattern.getResponsePattern());

      response.setContentType("text/xml");
      PrintWriter out = response.getWriter();

      // System.out.println(responseXmlGenerator.getXmlResponse(cluster, pingTime, code));

      // generating the response according to given pattern
      out.println(responseXmlGenerator.getXmlResponse(cluster, pingTime, code));

      // for tests
      // request.setAttribute("response", responseXmlGenerator.getXmlResponse(cluster, pingTime, code));
      // request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}