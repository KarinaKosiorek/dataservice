package com.configuration;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Stores service configuration to handle http requests. Configuration is grouped in concurrent hash maps by clients
 * (accountCodes), devices and pluginVersions to enable fast and thread-safe access.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceConfigurationModel {

  /**
   * key : accountCode
   */
  private ConcurrentHashMap<String, ClientConfiguration> clientConfigurationsMap = new ConcurrentHashMap<String, ClientConfiguration>();

  public ServiceConfigurationModel() {
  }

  public ClientConfiguration getClientConfiguration(String accountCode) {
    return clientConfigurationsMap.get(accountCode);
  }

  public void addClientConfiguration(String accountCode, ClientConfiguration clientConfiguration) {
    clientConfigurationsMap.put(accountCode, clientConfiguration);
  }

  public ConcurrentHashMap<String, ClientConfiguration> getClientConfigurationsMap() {
    return clientConfigurationsMap;
  }

  public void setClientConfigurationsMap(ConcurrentHashMap<String, ClientConfiguration> clientConfigurationsMap) {
    this.clientConfigurationsMap = clientConfigurationsMap;
  }

  public void init() {
    for (ClientConfiguration clientConfiguration : clientConfigurationsMap.values()) {
      clientConfiguration.init();
    }
  }

  public void print() {
    for (Entry<String, ClientConfiguration> entry : clientConfigurationsMap.entrySet()) {
      System.out.println("Client: " + entry.getKey());
      entry.getValue().print();
    }
  }
}
