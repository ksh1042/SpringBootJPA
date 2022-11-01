package com.roman14.springbootjpa.service;

import com.roman14.springbootjpa.entity.Address;
import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.entity.OrderStatus;
import com.roman14.springbootjpa.entity.item.Book;
import com.roman14.springbootjpa.exception.NotEnoughStockException;
import com.roman14.springbootjpa.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest
{
  @Autowired EntityManager em;
  @Autowired OrderService orderService;
  @Autowired OrderRepository orderRepository;

  Member member;
  Book book1;

  @BeforeEach
  void setUp()
  {
    member = createMember();
    book1 = createBook();
  }

  @Test
  void order()
  {
    // given
    final int orderCount = 3;

    // when
    Long orderId = orderService.order(member.getId(), book1.getId(), orderCount);
    Order findOrder = orderRepository.findOne(orderId);

    // then
    Assertions.assertNotNull(findOrder, "주문이 존재 해야 한다.");
    Assertions.assertEquals(1, findOrder.getOrderItems().size(), "주문한 상품 종류의 수가 정확 해야 한다.");
    Assertions.assertEquals(7, book1.getStockQuantity(), "주문 수량 만큼 재고가 줄어 들어야 한다.");
    Assertions.assertEquals(book1.getPrice() * orderCount, findOrder.getTotalPrice(), "주문 가격은 상품 가격 * 주문 수이다.");
  }

  @Test
  void cancel()
  {
    // given
    final int orderCount = 3;

    Long orderId = orderService.order(member.getId(), book1.getId(), orderCount);
    Order findOrder = orderRepository.findOne(orderId);

    // when
    orderService.cancel(orderId);

    // then
    Assertions.assertEquals(OrderStatus.CANCEL, findOrder.getStatus(), "주문 상태가 취소상태여야 한다");
    Assertions.assertEquals(10, book1.getStockQuantity(), "재고 수량이 복구 되어야 한다.");
  }

  @Test
  void orderOverflow()
  {
    // given
    final int orderCount = 11;

    // when
    // then
    Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book1.getId(), orderCount), "주문 재고수량 부족 오류가 발생해야 한다.");
  }

  @AfterEach
  void tearDown()
  {
  }

  Book createBook()
  {
    Book book1 = new Book();
    book1.setName("Spring JPA Test");
    book1.setPrice(23_000);
    book1.setStockQuantity(10);

    em.persist(book1);
    return book1;
  }

  Member createMember()
  {
    Member member = new Member();
    member.setName("Kim");
    member.setAddress(new Address("Daejeon", "Dosolro", "151-445"));

    em.persist(member);

    return member;
  }
}