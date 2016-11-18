package io.jawg.netty.example;

import io.jawg.netty.http.HttpRouter;
import io.jawg.netty.http.PathVariables;
import io.jawg.netty.http.Request;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
public abstract class TileRoutes {

  public static final  HttpRouter ROUTER = HttpRouter.builder()
      .get("/{z}/{x}/{y}.{ext}", TileRoutes::handleDefault)
      .get("/{layer}/{z}/{x}/{y}@{scale}x.{ext}.mmt{size}", TileRoutes::handleLayerScaleMeta)
      .build();

  private static void handleDefault(ChannelHandlerContext ctx, Request request) {
    PathVariables pathVariables = request.pathVariables();
    String layer = "default";
    Integer z = pathVariables.getInteger("z");
    Integer x = pathVariables.getInteger("x");
    Integer y = pathVariables.getInteger("y");
    String ext = pathVariables.getString("ext");

    String message = "layer=" + layer +
        " z=" + z +
        " y=" + x +
        " y=" + y +
        " ext=" + ext;

    ByteBuf content = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
    HttpUtil.setContentLength(response, content.readableBytes());
    ctx.writeAndFlush(response);
  }

  private static void handleLayerScaleMeta(ChannelHandlerContext ctx, Request request) {
    PathVariables pathVariables = request.pathVariables();
    String layer = pathVariables.getString("layer");
    int z = pathVariables.getInteger("z");
    int x = pathVariables.getInteger("x");
    int y = pathVariables.getInteger("y");
    int scale = pathVariables.getInteger("scale");
    String ext = pathVariables.getString("ext");
    int size = pathVariables.getInteger("size");

    String message = "layer=" + layer +
        " z=" + z +
        " y=" + x +
        " y=" + y +
        " scale=" + scale +
        " ext=" + ext +
        " size=" + size;

    ByteBuf content = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
    HttpUtil.setContentLength(response, content.readableBytes());
    ctx.writeAndFlush(response);
  }
}
