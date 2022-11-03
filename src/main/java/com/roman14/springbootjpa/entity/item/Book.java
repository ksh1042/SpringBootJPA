package com.roman14.springbootjpa.entity.item;

import com.roman14.springbootjpa.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// @DiscriminatorValue("Book")
@Getter @Setter
public class Book extends Item
{
  private String isbn;
  private String author;

  public static Book of(BookForm form)
  {
    Book book = new Book();
    book.setId(form.getId());
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    return book;
  }
}
