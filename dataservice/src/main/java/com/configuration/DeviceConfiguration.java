package com.configuration;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceConfiguration {

  /**
   * key : pluginVersion
   */
  private ConcurrentHashMap<String, ExitConfiguration> pluginVersionToExitConfigurationMap = new ConcurrentHashMap<String, ExitConfiguration>();

  public DeviceConfiguration() {
  }

  public void init() {
    for (ExitConfiguration responseConfiguration : pluginVersionToExitConfigurationMap.values()) {
      responseConfiguration.countTrafficLoadProbabilityThresholds();
    }
  }

  public void addExitConfiguration(String pluginVersion, ExitConfiguration pluginConfiguration) {
    pluginVersionToExitConfigurationMap.put(pluginVersion, pluginConfiguration);
  }

  public ExitConfiguration getExitConfiguration(String pluginVersion) {
    return pluginVersionToExitConfigurationMap.get(pluginVersion);
  }

  public void print() {
    for (Entry<String, ExitConfiguration> entry : pluginVersionToExitConfigurationMap.entrySet()) {
      System.out.println("\t\tPlugin: " + entry.getKey());
      entry.getValue().print();
    }
  }
}
