package xformation.foodorderingsystem.model.lunch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import xformation.foodorderingsystem.model.Product;

@XmlAccessorType(XmlAccessType.FIELD)
public class Lunch extends Product {

  private MainCourse mainCourse;
  private Dessert dessert;

  public Lunch(MainCourse mainCourse, Dessert dessert) {
    this.mainCourse = mainCourse;
    this.dessert = dessert;
  }

  public MainCourse getMainCourse() {
    return mainCourse;
  }

  public void setMainCourse(MainCourse mainCourse) {
    this.mainCourse = mainCourse;
  }

  public Dessert getDessert() {
    return dessert;
  }

  public void setDessert(Dessert dessert) {
    this.dessert = dessert;
  }
}
