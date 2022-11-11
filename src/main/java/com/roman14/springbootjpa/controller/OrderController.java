package com.roman14.springbootjpa.controller;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.entity.Order;
import com.roman14.springbootjpa.entity.item.Item;
import com.roman14.springbootjpa.repository.OrderSearch;
import com.roman14.springbootjpa.service.ItemService;
import com.roman14.springbootjpa.service.MemberService;
import com.roman14.springbootjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderController
{
  private final OrderService orderService;
  private final ItemService itemService;
  private final MemberService memberService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String listOrder(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model)
  {
    List<Order> orderList = orderService.findOrders(orderSearch);
    model.addAttribute("orders", orderList);

    return "/orders/orderList";
  }



  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String createOrderForm(Model model)
  {
    List<Member> memberList = memberService.findMembers();
    List<Item> itemList = itemService.findAll();

    model.addAttribute("members", memberList);
    model.addAttribute("items", itemList);

    return "/orders/createOrderForm";
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public String createOrder(@RequestParam("memberId") Long memberId,
                            @RequestParam("itemId") Long itemId,
                            @RequestParam("count") int count)
  {
    Long orderId = orderService.order(memberId, itemId, count);

    return "redirect:/orders/";
  }

  @RequestMapping(value = "/{orderId}/cancel", method = RequestMethod.POST)
  public String cancelOrder(@PathVariable("orderId") Long orderId)
  {
    orderService.cancel(orderId);

    return "redirect:/orders/";
  }

  @GetMapping("/{orderId}")
  public String orderDetail(@PathVariable("orderId") Long orderId, Model model)
  {
    Order order = orderService.findOne(orderId);

    model.addAttribute("order", order);

    return "/orders/orderDetail";
  }
}
