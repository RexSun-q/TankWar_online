package rex.tank.online;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import rex.tank.Dir;
import rex.tank.Group;
import rex.tank.Tank;
import rex.tank.TankFrame;

import java.util.UUID;

public class Client {

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        try {
            ChannelFuture future = bs.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TankMsgEncoder())
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ClientChannelHandler());
                        }
                    })
                    .connect("localhost", 8888)
                    .sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}

class ClientChannelHandler extends SimpleChannelInboundHandler<TankMsg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        TankMsg msg = new TankMsg(TankFrame.getINSTANCE().myTank);
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankMsg msg) throws Exception {
        int x = msg.x;
        int y = msg.y;
        Dir dir = msg.dir;
        Group g = msg.group;
        UUID id = msg.uuid; // TODO:
        boolean isMoving = msg.moving;
        TankFrame.getINSTANCE().enemyTanks.add(new Tank(x, y, dir, g, isMoving, TankFrame.getINSTANCE()));
    }
}
