package com.roman14.springbootjpa.common.configuration.log.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.time.LocalDateTime;
import java.util.Locale;

public class DefaultP6SpyMessageFormattingStrategy implements MessageFormattingStrategy
{

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url)  {
    sql = formatSql(category, sql);

    String separator = " / ";
    StringBuffer sb = new StringBuffer();
    sb.append(LocalDateTime.now());
    sb.append(separator);
    sb.append("time:");
    sb.append(elapsed);
    sb.append("ms");
    sb.append(" / ");
    sb.append(sql);

    return sb.toString();
  }

  private String formatSql(String category,String sql) {
    if(sql ==null || sql.trim().equals("")) return sql;

    // Only format Statement, distinguish DDL And DML
    if ( Category.STATEMENT.getName().equals(category)) {
      String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
      if(tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
        sql = FormatStyle.DDL.getFormatter().format(sql);
      }else {
        sql = FormatStyle.BASIC.getFormatter().format(sql);
      }
    }

    return sql;
  }
}
