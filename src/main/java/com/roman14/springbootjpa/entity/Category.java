package com.roman14.springbootjpa.entity;

import com.roman14.springbootjpa.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category
{
  @Id @GeneratedValue
  @Column(name = "category_id")
  private Long id;
  
  private String name;
  
//  @OneToMany(mappedBy = "item")
//  private List<CategoryItem> categoryItems = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> childs = new ArrayList<>();

  @ManyToMany
  private List<Item> items = new ArrayList<>();

  public void addChild(Category category)
  {
    this.childs.add(category);
    category.setParent(this);
  }

}
