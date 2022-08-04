package com.roman14.springbootjpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class CategoryItem
{
  @Id
  @GeneratedValue
  private Long id;
//  @Id
//  @ManyToOne(fetch = FetchType.LAZY)
//  private Category category;
//
//  @Id
//  @ManyToOne(fetch = FetchType.LAZY)
//  private Item item;
}
