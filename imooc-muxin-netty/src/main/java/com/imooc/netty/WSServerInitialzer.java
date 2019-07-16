package com.imooc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		//websocket ����httpЭ�飬����Ҫ��http�������
		pipeline.addLast(new HttpServerCodec());
		//�Դ���������֧��
		pipeline.addLast(new ChunkedWriteHandler());
		//��httpMessage���оۺϣ��ۺϳ�FullHttpRequest��FullHttpResponse
		//������netty�еı�̣�����ʹ�õ���handler
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		
		//****************************������֧��httpЭ��*********************************
		
		//****************************������֧��httpwebsocketЭ��*********************************
		/**
		 * websocket �����������Э�飬����ָ�����ͻ������ӷ��ʵ�·�ɣ�/ws
		 * ��handler����㴦��һЩ���صĸ��ӵ���
		 * ����㴦�����ֶ�����handshaking(close,ping,pong) ping + pong = ����
		 * ����websocket������������frames���д���ģ���ͬ���������Ͷ�Ӧ��framesҲ��ͬ
		 */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		
		//�Զ����handler
		pipeline.addLast(new ChatHandler());
	}

}
