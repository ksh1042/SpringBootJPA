package com.roman14.springbootjpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController
{
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(HttpServletRequest request)
  {
    log.info("request from : " + request.getRemoteAddr());
    return "home";
  }
}
