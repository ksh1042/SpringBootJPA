package com.roman14.springbootjpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Member
{
  @Id
  @GeneratedValue
  private Long id;
}
