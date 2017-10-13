package com.parkspace.agent.parkspaceagent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.parkspace.agent.model.SocketDataModel;
import com.parkspace.agent.util.JsonUtils;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @Title: CancelOrderParkingSpaceListener.java
 * @Package com.parkspace.agent.parkspaceagent
 * <p>Description:取消订单</p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月13日 上午11:12:16</p>
*/

public class CancelOrderParkingSpaceListener implements Emitter.Listener{
	/**
     * 获取日志接口.
     */
    private static final Log LOG = LogFactory.getLog(CancelOrderParkingSpaceListener.class);
	private Socket socket;

	public CancelOrderParkingSpaceListener() {
		super();
	}
	public CancelOrderParkingSpaceListener(Socket socket) {
		this.socket = socket;
	}
	@Override
    public void call(Object... args){
        String data = (String)args[0];
        LOG.info("删除订单，服务端：************"+data.toString());
        //取消订单之后需要删除临时权限信息，返回处理结果
        SocketDataModel socketDataModel = JsonUtils.str2Object(data, SocketDataModel.class);
        /**
         * 伪代码
         */
        socketDataModel.setSuccess(true);
        socket.emit("cancelOrderParkingSpace", JsonUtils.object2String(socketDataModel));
    }
}
