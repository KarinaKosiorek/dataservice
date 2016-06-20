package com.configuration;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClientConfiguration {

  /**
   * key : targetDevice
   */
  private ConcurrentHashMap<String, DeviceConfiguration> deviceConfigurationsMap = new ConcurrentHashMap<String, DeviceConfiguration>();

  public ClientConfiguration() {
  }

  public void init() {
    for (DeviceConfiguration deviceConfiguration : deviceConfigurationsMap.values()) {
      deviceConfiguration.init();
    }
  }

  public void addDeviceConfiguration(String targetDevice, DeviceConfiguration deviceConfiguration) {
    deviceConfigurationsMap.put(targetDevice, deviceConfiguration);
  }

  public DeviceConfiguration getDeviceConfiguration(String targetDevice) {
    return deviceConfigurationsMap.get(targetDevice);
  }

  public void print() {
    for (Entry<String, DeviceConfiguration> entry : deviceConfigurationsMap.entrySet()) {
      System.out.println("\tDevice: " + entry.getKey());
      entry.getValue().print();
    }
  }
}
