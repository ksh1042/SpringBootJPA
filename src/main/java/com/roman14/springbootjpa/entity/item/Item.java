package com.roman14.springbootjpa.entity.item;

import com.roman14.springbootjpa.entity.CategoryItem;
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

  private Long stockQuantity;

  @JoinTable

  @OneToMany(mappedBy = "item")
  private List<CategoryItem> categoryItems = new ArrayList<>();

}

