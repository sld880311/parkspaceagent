package com.parkspace.agent.parkspaceagent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.parkspace.agent.model.SocketDataModel;
import com.parkspace.agent.util.JsonUtils;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @Title: AddOrderParkingSpaceLinstener.java
 * @Package com.parkspace.agent.parkspaceagent
 * <p>Description:
 * 增加订单监听
 * 1.开通临时车的权限
 * 2.发送信息给服务端是否开通成功
 * 3.等待服务器响应，如果服务器返回false则回滚数据
 * </p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月13日 上午11:07:02</p>
*/

public class AddOrderParkingSpaceListener implements Emitter.Listener{
	/**
     * 获取日志接口.
     */
    private static final Log LOG = LogFactory.getLog(AddOrderParkingSpaceListener.class);
	private Socket socket;

	public AddOrderParkingSpaceListener() {
		super();
	}
	public AddOrderParkingSpaceListener(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void call(Object... args) {
		String data = (String)args[0];
        LOG.info("新增订单，服务端：************"+data.toString());
        SocketDataModel socketDataModel = JsonUtils.str2Object(data, SocketDataModel.class);
        //预约需要登记临时信息，返回是否登记成功
        /**
         * 伪代码
         */
        //给服务端发送信息
        socketDataModel.setSuccess(true);
        socket.emit("addOrderParkingSpace", JsonUtils.object2String(socketDataModel), new Ack() {
			@Override
			public void call(Object... args) {
				 String data1 = (String)args[0];
                 LOG.info("新增订单，服务端：11111111************"+data1.toString());
                 
                 /**
                  * 返回信息失败，需要删除预定信息
                  */
                 if("false".equalsIgnoreCase(data1)) {
                	 
                 }
			}
        });		
	}
}
