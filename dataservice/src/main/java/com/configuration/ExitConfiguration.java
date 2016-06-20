package com.configuration;

import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class ExitConfiguration {

  @XmlElement
  private int pingTime = 5;

  @XmlElement
  /**
   * key : cluster (host)
   * 
   */
  private ConcurrentHashMap<String, Integer> clusterToTrafficLoadConfigurationMap = new ConcurrentHashMap<String, Integer>();

  /**
   * key : trafficLoad integer value between 0 and 100 inclusive
   * 
   * helper map for getCluster() method
   *
   **/
  private ConcurrentHashMap<Integer, String> trafficLoadProbabilityThresholdToClusterMap = new ConcurrentHashMap<Integer, String>();

  /**
   * helper thread-safe collection for getCluster() method
   *
   **/
  private Vector<Integer> trafficLoadProbabilityThresholds = new Vector<Integer>();

  public ExitConfiguration() {
  }

  public ExitConfiguration(int pingTime) {
    this.pingTime = pingTime;
  }

  public void addClusterLoadConfiguration(String cluster, Integer trafficLoad) {
    clusterToTrafficLoadConfigurationMap.put(cluster, trafficLoad);
  }

  public int getPingTime() {
    return pingTime;
  }

  /**
   * 
   * @return helper method to prepare traffic load probability values for getCluster() method
   */
  public void countTrafficLoadProbabilityThresholds() {
    Integer probabilityThreshold = 0;

    for (Entry<String, Integer> entry : clusterToTrafficLoadConfigurationMap.entrySet()) {
      probabilityThreshold += entry.getValue();
      trafficLoadProbabilityThresholds.add(probabilityThreshold);
      trafficLoadProbabilityThresholdToClusterMap.put(probabilityThreshold, entry.getKey());
    }
  }

  /**
   * 
   * @return cluster name based on traffic load values of all clusters
   */
  public String getCluster() {
    Random generator = new Random();
    int probabilityValue = generator.nextInt(100);

    for (Integer probabilityThreshold : trafficLoadProbabilityThresholds) {
      if (probabilityValue < probabilityThreshold) {
        String cluster = trafficLoadProbabilityThresholdToClusterMap.get(probabilityThreshold);
        if (cluster != null) {
          return cluster;
        }
      }
    }
    return null;
  }

  public void print() {
    System.out.println("\t\t\tpingTime: " + pingTime);
    for (Entry<String, Integer> entry : clusterToTrafficLoadConfigurationMap.entrySet()) {
      System.out.println("\t\t\t\tcluster: " + entry.getValue() + " trafficLoad: " + entry.getKey());
    }
  }
}
