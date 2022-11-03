package com.roman14.springbootjpa.controller;

import com.roman14.springbootjpa.entity.item.Book;
import com.roman14.springbootjpa.entity.item.Item;
import com.roman14.springbootjpa.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/items")
public class ItemController
{
  private final ItemService itemService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String list(Model model)
  {
    List<Item> itemList = itemService.findAll();

    model.addAttribute("items", itemList);

    return "items/itemList";
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String createForm(Model model)
  {
    model.addAttribute("form", new BookForm());

    return "items/createItemForm";
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String create(BookForm bookForm)
  {
    Book book = Book.of(bookForm);

    itemService.save(book);

    return "redirect:/items/";
  }

  @RequestMapping(value = "/{itemId}/edit", method = RequestMethod.GET)
  public String editForm(@PathVariable("itemId") Long itemId, Model model)
  {
    Item item = itemService.findOne(itemId);

    BookForm form = BookForm.of((Book) item);

    model.addAttribute("form", form);

    return "items/editItemForm";
  }

  @RequestMapping(value = "/{itemId}/edit", method = RequestMethod.POST)
  public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute BookForm form, BindingResult result)
  {
    // 준 영속 엔티티
    Book book = Book.of(form);

    itemService.save(book);

    return "redirect:/items/";
  }
}
