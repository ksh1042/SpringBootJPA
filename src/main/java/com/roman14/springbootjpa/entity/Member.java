package com.roman14.springbootjpa.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Entity
@Getter @Builder
public class Member
{
  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  private LocalDateTime addTime;

  @Embedded
  private Address address;

  @Lob
  private String description;
}
