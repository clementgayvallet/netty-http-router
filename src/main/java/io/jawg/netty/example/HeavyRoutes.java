package io.jawg.netty.example;

import io.jawg.netty.http.HttpRouter;
import io.jawg.netty.http.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
public abstract class HeavyRoutes {

  public static HttpRouter ROUTER;

  static {
    HttpRouter.Builder builder = HttpRouter.builder();
    for (int i = 0; i < 1000; ++i) {
      builder.get("/{layer}/{z}/{x}/{y}@{scale}x.{ext}.mmt{size}", HeavyRoutes::handle);
    }
    builder.get("/{layer}/{z}/{x}/{y}@{scale}x.{ext}.mmt{size}/last-route", HeavyRoutes::handleLastRoute);
    ROUTER = builder.build();
  }

  private static void handle(ChannelHandlerContext ctx, Request request) {
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    HttpUtil.setContentLength(response, 0);
    ctx.writeAndFlush(response);
  }

  private static void handleLastRoute(ChannelHandlerContext ctx, Request request) {
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    HttpUtil.setContentLength(response, 0);
    ctx.writeAndFlush(response);
  }
}
