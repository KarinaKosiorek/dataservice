package xformation.foodorderingsystem.model;

import java.math.BigDecimal;

public abstract class Product {

  private BigDecimal price;

  private String name;

  public BigDecimal getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
