package com.imooc.netty;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Administrator
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	//���ڼ�¼�͹������пͻ��˵�channel
	private static ChannelGroup clients = 
			new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) 
			throws Exception {
		//��ȡ�ͻ��˴����������Ϣ
		String content = msg.text();
		System.out.println("���ܵ������ݣ�" +content);
		
//		for(Channel channel:clients) {
//			channel.writeAndFlush(
//					new TextWebSocketFrame("[���������յ���Ϣ��]"+LocalDateTime.now()+"���յ���Ϣ, ��ϢΪ��"+content));
//		}
		
		//��������������������forѭ����һ��
		clients.writeAndFlush(
				new TextWebSocketFrame("[���������յ���Ϣ��]"
						+LocalDateTime.now()+"���յ���Ϣ, ��ϢΪ��"+content));
	}

	/**
	 * ���ͻ��˴������ӵ�ʱ��
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		clients.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//������handlerRemoved,channelGroup���Զ��Ƴ���Ӧ�ͻ��˵�channel
		System.out.println("�ͻ��˶Ͽ���channel��Ӧ�ĳ�idΪ"+ctx.channel().id().asLongText());
		System.out.println("�ͻ��˶Ͽ���channel��Ӧ�Ķ�idΪ"+ctx.channel().id().asShortText());
	}

}
