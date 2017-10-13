package com.parkspace.agent.parkspaceagent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.parkspace.agent.model.SocketDataModel;
import com.parkspace.agent.util.JsonUtils;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @Title: SocketClient.java
 * @Package com.parkspace.socket
 * <p>Description:</p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月12日 上午11:10:21</p>
*/
@Service("socketClient")
public class SocketClient {
	/**
     * 获取日志接口.
     */
    private static final Log LOG = LogFactory.getLog(SocketClient.class);
	private String url = "http://localhost:9092";
	public void open() {
		try {
			IO.Options options = new IO.Options();
			options.forceNew = true;
	        options.reconnection = true;
	        //获取socket
	        final Socket socket = IO.socket(url, options);
	        
	        
	        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LOG.info("已经连接到服务端");
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override 
                public void call(Object... args) {
                    LOG.info("连接超时");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override    
                public void call(Object... args) {
                    LOG.info("连接错误");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {    
                    LOG.info("失去连接");
                }
            }).on("addOrderParkingSpace",new AddOrderParkingSpaceListener(socket)//预约
            ).on("cancelOrderParkingSpace", new CancelOrderParkingSpaceListener(socket)//取消订单
            ).on("sendMessage", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					String data = (String)args[0];
					LOG.info("普通消息记录："+data);
				}
			}).on("checkInCommunity", new Emitter.Listener() {//进入小区
				@Override
				public void call(Object... args) {
					String data = (String)args[0];
					SocketDataModel socketDataModel = JsonUtils.str2Object(data, SocketDataModel.class);
					LOG.info("车辆进入小区服务端返回信息："+socketDataModel);
				}
			}).on("checkOutCommunity", new Emitter.Listener() {//离开小区
				@Override
				public void call(Object... args) {
					String data = (String)args[0];
					SocketDataModel socketDataModel = JsonUtils.str2Object(data, SocketDataModel.class);
					/**
					 * 如果返回不成功是否需要回滚
					 */
					LOG.info("车辆离开小区服务端返回信息："+socketDataModel);
				}
			});
            socket.open();
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		client.open();
	}

}
