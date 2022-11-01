package com.roman14.springbootjpa.entity.item;

import com.roman14.springbootjpa.entity.Category;
import com.roman14.springbootjpa.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //(name = "DTYPE")
@Getter @Setter
public abstract class Item
{
  @Id @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;

  private int price;

  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

//  @JoinTable
//  @OneToMany(mappedBy = "item")
//  private List<CategoryItem> categoryItems = new ArrayList<>();

  /**
   * 재고수량 증가
   * @param quantity
   */
  public void addStockQuantity(int quantity)
  {
    this.stockQuantity += quantity;
  }

  public void minusStockQuantity(int quantity)
  {
    if(this.stockQuantity > quantity)
    {
      this.stockQuantity -= quantity;
    }
    else
    {
      throw new NotEnoughStockException("need more stock");
    }
  }
}

