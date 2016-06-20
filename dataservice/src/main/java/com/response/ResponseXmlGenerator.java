package com.response;

public class ResponseXmlGenerator {

  private String pattern = "";

  public ResponseXmlGenerator(String pattern) {
    this.pattern = pattern;
  }

  public String getXmlResponse(String cluster, String pingTime, String code) {
    return pattern.replaceAll("#cluster#", cluster != null ? cluster : "")
        .replaceAll("#pingTime#", pingTime != null ? pingTime : "").replaceAll("#code#", code != null ? code : "");
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}
