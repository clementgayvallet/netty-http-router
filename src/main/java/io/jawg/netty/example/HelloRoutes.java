package io.jawg.netty.example;

import io.jawg.netty.http.HttpRouter;
import io.jawg.netty.http.Request;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
public abstract class HelloRoutes {

  public static final HttpRouter ROUTER = HttpRouter.builder()
      .get("/hello", HelloRoutes::sayHello)
      .get("/hello/{name}", HelloRoutes::sayHello)
      .post("/hello", HelloRoutes::postHello)
      .put("/hello", HelloRoutes::postHello)
      .delete("/hello", HelloRoutes::deleteHello)
      .build();

  private static void sayHello(ChannelHandlerContext ctx, Request request) {
    String name = request.pathVariables().getString("name");
    Integer age = request.queryParameters().getInteger("age");
    List<Float> array = request.queryParameters().getFloats("array");

    String greetings = "Hello, " +
        (name == null ? "world" : name) +
        (age == null ? "" : " age=" + age) +
        (array == null ? "" : " array=" + array);

    // Prepare the response
    ByteBuf message = Unpooled.copiedBuffer(greetings, CharsetUtil.UTF_8);
    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, message);
    response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
    HttpUtil.setContentLength(response, message.readableBytes());

    // Send the response
    ctx.writeAndFlush(response);
  }

  private static void postHello(ChannelHandlerContext ctx, Request request) {
    String message = request.content().toString(CharsetUtil.UTF_8);
    System.out.println(message);
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    HttpUtil.setContentLength(response, 0);
    ctx.writeAndFlush(response);
  }


  private static void deleteHello(ChannelHandlerContext ctx, Request request) {
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    HttpUtil.setContentLength(response, 0);
    ctx.writeAndFlush(response);
  }
}
