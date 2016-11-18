package io.jawg.netty.http;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Clement Gayvallet
 * @since 17/11/16
 */
public class HttpRoute {

  private static final Predicate<String> SPECIAL = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE).asPredicate();

  private Pattern pattern;
  private List<String> params;
  private BiConsumer<ChannelHandlerContext, Request> handler;

  public HttpRoute(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
    this.handler = handler;
    parse(path);
  }

  public PathVariables match(String path) {
    Matcher matcher = pattern.matcher(path);
    if (matcher.matches()) {
      return new PathVariables(params.stream().collect(Collectors.toMap(Function.identity(), matcher::group)));
    }
    return null;
  }

  public BiConsumer<ChannelHandlerContext, Request> handler() {
    return handler;
  }

  private void parse(String path) {
    String regex = "^";

    params = new ArrayList<>();

    String param = null;

    for (int i = 0; i < path.length(); ++i) {
      char c = path.charAt(i);

      if (param != null) {
        if (c == '}') {
          regex += "(?<" + param + ">[a-zA-Z0-9\\-_]+)";
          params.add(param);
          param = null;
          continue;
        }
        param += c;
        continue;
      }

      if (c == '{') {
        param = "";
        continue;
      }

      regex += escapeIfSpecial(c);
    }

    pattern = Pattern.compile(regex + "$");
  }

  private static String escapeIfSpecial(char c) {
    String res = String.valueOf(c);
    return SPECIAL.test(res) ? "\\" + res : res;
  }
}
