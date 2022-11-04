package com.roman14.springbootjpa.controller;

import com.roman14.springbootjpa.entity.Member;
import com.roman14.springbootjpa.entity.item.Item;
import com.roman14.springbootjpa.service.ItemService;
import com.roman14.springbootjpa.service.MemberService;
import com.roman14.springbootjpa.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String createOrderForm(Model model, BindingResult result)
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

    return "redirect:/orders/" + orderId;
  }
}
