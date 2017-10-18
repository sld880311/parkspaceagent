package com.parkspace.agent.parkspaceagent;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${url}")
	private String url;
	/**
	 * 设置客户端所在的小区
	 */
	@Value("${comid}")
	private String comid;
	
	private static Socket socket = null;
	//执行时间，时间单位为毫秒，不得小于等于0
	private static Long cacheTime = Long.MAX_VALUE;
	//延迟时间，时间单位为毫秒，不得小于等于0
//  private static Integer delay = 3000 * 60 * 60;
	private static Integer delay = 3000;
	
	public void open() {
		try {
			IO.Options options = new IO.Options();
			options.forceNew = true;
	        options.reconnection = true;
	        //获取socket
	        final Socket socket = IO.socket(url, options);
	        
	        /**
	         * 添加socket普通事件：主要是连接事件
	         */
	        
	        addCommonEvnet(socket);
	        socket.on("addOrderParkingSpace",new AddOrderParkingSpaceListener(socket)//预约
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
            Thread.sleep(Integer.MAX_VALUE);
		}catch(Exception e) {
			LOG.error("启动客户端失败："+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Title: addCommonEvnet
	 * <p>Description:初始化客户端socket的普通事件</p>
	 * @param     参数
	 * @return void    返回类型
	 * @throws
	 * <p>CreateDate:2017年10月18日 下午2:11:52</p>
	 */
	private void addCommonEvnet(Socket socket) {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            	//发送id到服务端
            	socket.emit("sendClientID", comid);
                LOG.info("已经连接到服务端，客户端小区编号【"+comid+"】");
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override 
            public void call(Object... args) {
            	//删除id到服务端
            	socket.emit("removeClientID", comid);
                LOG.info("连接超时，客户端小区编号【"+comid+"】");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override    
            public void call(Object... args) {
            	//删除id到服务端
            	socket.emit("removeClientID", comid);
                LOG.info("连接错误，客户端小区编号【" +comid+"】");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            	//删除id到服务端
            	socket.emit("removeClientID", comid);
                LOG.info("失去连接客户端小区编号【" +comid+"】");
            }
        });
	}
	/**
	 * 
	 * @Title: getSocket
	 * <p>Description:获取客户端的socket信息</p>
	 * @param     参数
	 * @return Socket    返回类型
	 * @throws
	 * <p>CreateDate:2017年10月18日 下午2:00:34</p>
	 */
	public static Socket getSocket() {
        return socket;
    }
	/**
	 * 
	 * @Title: startClient
	 * <p>Description:启动客户端</p>
	 * @param     参数
	 * @return void    返回类型
	 * @throws
	 * <p>CreateDate:2017年10月18日 下午2:02:22</p>
	 */
	public void startClient() {
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
          public void run() {
            //启动socket监听
              try{
                  if(SocketClient.getSocket() == null){
                      new Thread(new Runnable() {
                          @Override
                          public void run() {
                              try {
                            	  open();
                              } catch (Exception e) {
                                  e.printStackTrace();
                                  LOG.error("初始化client 的socket失败："+e.getMessage());
                              }
                          }
                      }).start();
                  }
              }catch(Exception e){
            	  e.printStackTrace();
              }
          }
        }, delay,cacheTime);// 这里设定将延时每天固定执行
	}
}
