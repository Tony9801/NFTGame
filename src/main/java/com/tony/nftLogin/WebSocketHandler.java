package com.tony.nftLogin;

import com.google.protobuf.InvalidProtocolBufferException;
import com.tony.nftLogin.service.InventoryService;
import com.tony.nftLogin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private LoginService loginService;

    @Autowired
    private InventoryService inventoryService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // Assuming the message payload is the byte representation of the protobuf message
            Message.WrapperMessage wrapperMessage = Message.WrapperMessage.parseFrom(message.asBytes());
            int msgId = wrapperMessage.getMsgId();
            if(msgId == 12){

                Message.LoginRequest loginRequest = wrapperMessage.getLoginRequest();
                Message.LoginResponse response = loginService.authenticate(loginRequest);

                Message.WrapperMessage wrapper = Message.WrapperMessage.newBuilder()
                        .setMsgId(13)
                        .setLoginResponse(response).build();

                // Sending the response back to the client
                session.sendMessage(new TextMessage(wrapper.toByteArray()));
            }else if (msgId == 14){
                Message.GetInventoryRequest getInventoryReq = wrapperMessage.getGetInventoryReq();
                Message.GetInventoryResponse getInventoryRes = inventoryService.getInventoryForUser(getInventoryReq.getUserId());
                Message.WrapperMessage wrapper = Message.WrapperMessage.newBuilder()
                        .setMsgId(15)
                        .setGetInventoryRes(getInventoryRes).build();
                session.sendMessage(new TextMessage(wrapper.toByteArray()));
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("Error processing the request"));
        }
    }
}
