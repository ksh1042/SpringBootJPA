package com.roman14.springbootjpa.common.configuration.log.p6spy;

import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class P6SpyConfig
{
  @PostConstruct
  public void setLogMessageFormat()
  {
    P6SpyOptions
      .getActiveInstance()
      .setLogMessageFormat(DefaultP6SpyMessageFormattingStrategy.class.getName());
  }
}
