package com.configuration.xml.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.configuration.ApplicationConfiguration;
import com.configuration.ClientConfiguration;
import com.configuration.DeviceConfiguration;
import com.configuration.ExitConfiguration;
import com.configuration.ServiceConfigurationModel;
import com.configuration.controller.ServiceConfigurationController;

public class ServiceConfigurationXmlManager {

  public static void loadConfiguration(ServiceConfigurationController serviceConfigurationController) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ServiceConfigurationModel.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      ServiceConfigurationModel serviceConfigurationModel = (ServiceConfigurationModel) jaxbUnmarshaller
          .unmarshal(ServiceConfigurationXmlManager.class.getClassLoader().getResource(
              ApplicationConfiguration.CONFIGURATION_FILENAME));

      if (serviceConfigurationModel != null) {
        serviceConfigurationController.setServiceConfigurationModel(serviceConfigurationModel);
        serviceConfigurationController.init();
        System.out.println("Configuration loaded.");
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  public static void reloadConfiguration(String xmlConfiguration, ServiceConfigurationController serviceConfigurationController) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ServiceConfigurationModel.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      StreamSource streamSource = new StreamSource(new StringReader(xmlConfiguration));
      JAXBElement<ServiceConfigurationModel> jaxbElement = jaxbUnmarshaller
          .unmarshal(streamSource, ServiceConfigurationModel.class);
      ServiceConfigurationModel serviceConfigurationModel = (ServiceConfigurationModel) jaxbElement.getValue();

      if (serviceConfigurationModel != null) {
        synchronized (xmlConfiguration) {
          serviceConfigurationController.setServiceConfigurationModel(serviceConfigurationModel);
          serviceConfigurationController.init();
        }
        System.out.println("Configuration reloaded.");
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  /*
   * helper to save initial configuration, not used in working app
   */
  public static void saveConfiguration(ServiceConfigurationController configurationDataController) {
    setInitialServiceConfiguration(configurationDataController);

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ServiceConfigurationModel.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      ServiceConfigurationModel serviceConfigurationModel = configurationDataController.getServiceConfiguration();

      File file = new File(ServiceConfigurationXmlManager.class.getClassLoader()
          .getResource(ApplicationConfiguration.CONFIGURATION_FILENAME).getPath());

      // File file = new File(
      // "C:/Users/karina.kosiorek/Downloads/DataService/DataService/src/main/resources/configuration/configuration.xml");

      FileOutputStream fileOutputStream = new FileOutputStream(file);
      jaxbMarshaller.marshal(serviceConfigurationModel, fileOutputStream);
      fileOutputStream.close();

      System.out.println("Configuration saved");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * helper to generate initial configuration file
   */
  private static void setInitialServiceConfiguration(ServiceConfigurationController serviceConfigurationController) {

    ClientConfiguration clientConfiguration1 = new ClientConfiguration();
    ClientConfiguration clientConfiguration2 = new ClientConfiguration();
    DeviceConfiguration deviceConfiguration1 = new DeviceConfiguration();
    DeviceConfiguration deviceConfiguration2 = new DeviceConfiguration();
    DeviceConfiguration deviceConfiguration3 = new DeviceConfiguration();
    ExitConfiguration responseConfiguration1 = new ExitConfiguration(10);
    responseConfiguration1.addClusterLoadConfiguration("clusterA.com", 70);
    responseConfiguration1.addClusterLoadConfiguration("clusterB.com", 30);

    ExitConfiguration responseConfiguration2 = new ExitConfiguration(5);
    responseConfiguration2.addClusterLoadConfiguration("clusterB.com", 100);

    ExitConfiguration responseConfiguration3 = new ExitConfiguration(5);
    responseConfiguration3.addClusterLoadConfiguration("clusterA.com", 50);
    responseConfiguration3.addClusterLoadConfiguration("clusterB.com", 50);

    clientConfiguration1.addDeviceConfiguration("XBox", deviceConfiguration1);
    clientConfiguration1.addDeviceConfiguration("Panasonic", deviceConfiguration2);
    deviceConfiguration1.addExitConfiguration("3.3.1", responseConfiguration1);
    deviceConfiguration2.addExitConfiguration("3.3.2", responseConfiguration2);

    clientConfiguration2.addDeviceConfiguration("osmf", deviceConfiguration3);
    deviceConfiguration3.addExitConfiguration("3.3.1", responseConfiguration3);

    serviceConfigurationController.addClientConfiguration("clienteA", clientConfiguration1);
    serviceConfigurationController.addClientConfiguration("clienteB", clientConfiguration2);

    serviceConfigurationController.print();
  }
}
