package rex.tank.online;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import rex.tank.TankFrame;

public class Client {

    public static Client INSTANCE = new Client();
    Channel channel = null;

    private Client() {}

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
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ClientChannelHandler());
                        }
                    })
                    .connect("localhost", 8888)
                    .sync();

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel = future.channel();
                    } else {
                        System.out.printf("connection is failed");
                    }
                }
            });

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static Client getInstance() { return INSTANCE; }

    public void sendMsg(Msg msg) {
        channel.writeAndFlush(msg);
    }
}

class ClientChannelHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Msg msg = new TankMsg(TankFrame.getINSTANCE().myTank);
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        msg.handle(ctx);
    }
}
