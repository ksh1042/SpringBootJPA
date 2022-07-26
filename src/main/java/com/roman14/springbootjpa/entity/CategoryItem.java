package com.roman14.springbootjpa.entity;

import com.roman14.springbootjpa.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class CategoryItem
{
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  private Item item;
}
