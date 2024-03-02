package com.tony.nftLogin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketHandlerTest {

    @LocalServerPort
    private int port;

    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile String receivedMessage;

    @Test
    public void testWebSocketHandler() throws Exception {

        StandardWebSocketClient client = new StandardWebSocketClient();

        // Handler for client to process server responses
        AbstractWebSocketHandler handler = new AbstractWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessage = message.getPayload();
                latch.countDown();
            }
        };

        // Connect client to server
        WebSocketSession session = client.doHandshake(handler, "ws://localhost:" + port + "/ws").get();

        // Create LoginRequest protobuf message
        Message.LoginRequest loginRequest = Message.LoginRequest.newBuilder()
                .setUsername("sampleUser")
                .setPassword("samplePassword")
                .build();
        Message.WrapperMessage wrapperMessage = Message.WrapperMessage.newBuilder()
                .setMsgId(12)
                .setLoginRequest(loginRequest)
                .build();

        Message.GetInventoryRequest getInventoryRequest = Message.GetInventoryRequest.newBuilder()
                .setUserId(1).build();
        Message.WrapperMessage getInventoryMsg = Message.WrapperMessage.newBuilder()
                .setMsgId(14)
                .setGetInventoryReq(getInventoryRequest)
                .build();

        // Send protobuf message as bytes wrapped in TextMessage
        session.sendMessage(new TextMessage(wrapperMessage.toByteArray()));


        // Wait for response with a timeout
        latch.await(5, TimeUnit.SECONDS);

        // Assertions
        assertNotNull(receivedMessage);
        // Convert receivedMessage back to a protobuf object and assert its fields
        // Parse the response back to LoginResponse
        Message.WrapperMessage wrapper = Message.WrapperMessage.parseFrom(receivedMessage.getBytes());
        assertEquals(13, wrapper.getMsgId());
        Message.LoginResponse response = wrapper.getLoginResponse();
        System.out.println("The token is " + response.getToken());
        assertEquals(1, response.getUserId());

        session.sendMessage(new TextMessage(getInventoryMsg.toByteArray()));

        session.close();
    }

    @Test
    public void testWebSocketHandlerGetInventory() throws Exception {

        StandardWebSocketClient client = new StandardWebSocketClient();

        // Handler for client to process server responses
        AbstractWebSocketHandler handler = new AbstractWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessage = message.getPayload();
                latch.countDown();
            }
        };

        // Connect client to server
        WebSocketSession session = client.doHandshake(handler, "ws://localhost:" + port + "/ws").get();

        // Create GetInventoryRequest protobuf message
        Message.GetInventoryRequest getInventoryRequest = Message.GetInventoryRequest.newBuilder()
                .setUserId(1).build();
        Message.WrapperMessage getInventoryMsg = Message.WrapperMessage.newBuilder()
                .setMsgId(14)
                .setGetInventoryReq(getInventoryRequest)
                .build();

        // Send protobuf message as bytes wrapped in TextMessage
        session.sendMessage(new TextMessage(getInventoryMsg.toByteArray()));


        // Wait for response with a timeout
        latch.await(5, TimeUnit.SECONDS);

        // Assertions
        assertNotNull(receivedMessage);
        // Convert receivedMessage back to a protobuf object and assert its fields
        // Parse the response back to LoginResponse
        Message.WrapperMessage wrapper = Message.WrapperMessage.parseFrom(receivedMessage.getBytes());
        assertEquals(15, wrapper.getMsgId());
        Message.GetInventoryResponse response = wrapper.getGetInventoryRes();
        System.out.println(response.getItemList());
        assertEquals(1, response.getUserId());

        session.close();
    }
}