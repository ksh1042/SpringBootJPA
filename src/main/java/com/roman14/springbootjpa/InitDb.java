package com.roman14.springbootjpa;

import com.roman14.springbootjpa.entity.*;
import com.roman14.springbootjpa.entity.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb
{
  private final InitService initService;

  @PostConstruct
  public void init()
  {
    initService.dbInit1();
    initService.dbInit2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;

    public void dbInit1()
    {
      Member member = new Member();
      member.setName("김주영");
      member.setAddress(new Address("Seoul", "Jonro", "142-332"));
      member.setAddTime(LocalDateTime.now());
      em.persist(member);

      Book book1 = new Book();
      book1.setName("JPA Basic");
      book1.setPrice(20_000);
      book1.setStockQuantity(100);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("JPA Advance");
      book2.setPrice(23_500);
      book2.setStockQuantity(100);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 15);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 20);

      Order order = Order.createOrder(member, Delivery.from(member.getAddress()), orderItem1, orderItem2);
      em.persist(order);
    }

    public void dbInit2()
    {
      Member member = new Member();
      member.setName("신정학");
      member.setAddress(new Address("Seoul", "Gangnam", "552-168"));
      member.setAddTime(LocalDateTime.now());
      em.persist(member);

      Book book1 = new Book();
      book1.setName("Spring Basic");
      book1.setPrice(30_000);
      book1.setStockQuantity(100);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("Spring Advance");
      book2.setPrice(42_500);
      book2.setStockQuantity(100);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 22);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 32);

      Order order = Order.createOrder(member, Delivery.from(member.getAddress()), orderItem1, orderItem2);
      em.persist(order);
    }
  }
}
