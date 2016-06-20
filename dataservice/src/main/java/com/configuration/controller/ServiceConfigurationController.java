package com.configuration.controller;

import com.configuration.ClientConfiguration;
import com.configuration.DeviceConfiguration;
import com.configuration.ExitConfiguration;
import com.configuration.ServiceConfigurationModel;

/**
 * Class to control access to service configuration
 * 
 */
public class ServiceConfigurationController {

  private ServiceConfigurationModel serviceConfigurationModel = new ServiceConfigurationModel();

  public ServiceConfigurationController() {
  }

  public ServiceConfigurationModel getServiceConfiguration() {
    return serviceConfigurationModel;
  }

  public void setServiceConfigurationModel(ServiceConfigurationModel serviceConfiguration) {
    this.serviceConfigurationModel = serviceConfiguration;
  }

  public void addClientConfiguration(String accountCode, ClientConfiguration clientConfiguration) {
    serviceConfigurationModel.addClientConfiguration(accountCode, clientConfiguration);
  }

  public ClientConfiguration getClientConfiguration(String accountCode) {
    return serviceConfigurationModel.getClientConfiguration(accountCode);
  }

  public ExitConfiguration getResponseConfiguration(String accountCode, String targetDevice, String pluginVersion) {
    ClientConfiguration clientConfiguration = serviceConfigurationModel.getClientConfiguration(accountCode);
    if (clientConfiguration != null) {
      DeviceConfiguration deviceConfiguration = clientConfiguration.getDeviceConfiguration(targetDevice);
      if (deviceConfiguration != null) {
        return deviceConfiguration.getExitConfiguration(pluginVersion);
      }
    }
    return null;
  }

  public void init() {
    serviceConfigurationModel.init();
  }

  public void print() {
    serviceConfigurationModel.print();
  }
}
