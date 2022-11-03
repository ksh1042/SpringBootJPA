package com.roman14.springbootjpa.entity;

import com.roman14.springbootjpa.controller.MemberForm;
import lombok.Getter;

import javax.persistence.Embeddable;


@Embeddable
@Getter
public class Address
{
  private String city;
  private String street;
  private String zipcode;

  protected Address() {}

  public Address(String city, String street, String zipcode)
  {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }

  public static Address of(MemberForm memberForm)
  {
    return new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
  }
}
