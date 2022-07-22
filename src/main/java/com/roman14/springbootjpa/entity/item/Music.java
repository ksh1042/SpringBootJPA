package com.roman14.springbootjpa.entity.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// @DiscriminatorValue("Music")
@Getter @Setter
public class Music extends Item
{
  private String artist;
  private String etc;
}
