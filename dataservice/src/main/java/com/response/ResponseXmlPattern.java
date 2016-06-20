package com.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.configuration.ApplicationConfiguration;

public class ResponseXmlPattern {

  private String responsePattern = "";

  public ResponseXmlPattern() {
    loadPattern();
  }

  private void loadPattern() {
    try {
      InputStreamReader fileReader = new InputStreamReader(ResponseXmlPattern.class.getClassLoader().getResourceAsStream(
          ApplicationConfiguration.RESPONSEPATTERN_FILENAME));
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      StringBuilder stringBuilder = new StringBuilder();
      String line = "";
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append("\n");
      }
      responsePattern = stringBuilder.toString();
      // System.out.println("Response pattern: " + responsePattern);
      fileReader.close();
      bufferedReader.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getResponsePattern() {
    return responsePattern;
  }
}
