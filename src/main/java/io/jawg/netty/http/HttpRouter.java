package io.jawg.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 *
 * The name of route parameters must be made up of "word characters" ([A-Za-z0-9_\-]).
 */
public class HttpRouter {

  private Map<HttpMethod, List<HttpRoute>> routes;

  private HttpRouter() {
    routes = new HashMap<>();
    routes.put(HttpMethod.GET, new ArrayList<>());
    routes.put(HttpMethod.POST, new ArrayList<>());
    routes.put(HttpMethod.PUT, new ArrayList<>());
    routes.put(HttpMethod.DELETE, new ArrayList<>());
    routes.put(HttpMethod.OPTIONS, new ArrayList<>());
    routes.put(HttpMethod.CONNECT, new ArrayList<>());
    routes.put(HttpMethod.PATCH, new ArrayList<>());
    routes.put(HttpMethod.TRACE, new ArrayList<>());
    routes.put(HttpMethod.HEAD, new ArrayList<>());
  }

  List<HttpRoute> routes(HttpMethod method) {
    return routes.get(method);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private HttpRouter router = new HttpRouter();

    public Builder get(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.GET, path, handler);
      return this;
    }

    public Builder post(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.POST, path, handler);
      return this;
    }

    public Builder put(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.PUT, path, handler);
      return this;
    }

    public Builder delete(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.DELETE, path, handler);
      return this;
    }

    public Builder options(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.OPTIONS, path, handler);
      return this;
    }

    public Builder connect(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.CONNECT, path, handler);
      return this;
    }

    public Builder patch(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.PATCH, path, handler);
      return this;
    }

    public Builder trace(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.TRACE, path, handler);
      return this;
    }

    public Builder head(String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      register(HttpMethod.HEAD, path, handler);
      return this;
    }

    public HttpRouter build() {
      return router;
    }

    private void register(HttpMethod method, String path, BiConsumer<ChannelHandlerContext, Request> handler) {
      router.routes.get(method).add(new HttpRoute(path, handler));
    }

  }

}
