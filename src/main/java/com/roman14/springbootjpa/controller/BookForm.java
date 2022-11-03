package com.roman14.springbootjpa.controller;

import com.roman14.springbootjpa.entity.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm
{
  private Long id;
  private String name;
  private int price;
  private int stockQuantity;

  private String author;
  private String isbn;

  public static BookForm of(Book book)
  {
    BookForm form = new BookForm();
    form.setId(book.getId());
    form.setName(book.getName());
    form.setPrice(book.getPrice());
    form.setStockQuantity(book.getStockQuantity());
    form.setAuthor(book.getAuthor());
    form.setIsbn(book.getAuthor());

    return form;
  }
}
