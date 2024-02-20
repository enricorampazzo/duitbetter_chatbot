package dev.ted.websocket_htmx_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextWebSocketHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.info("Websocket Text Message received from session: {}, with message: {}", session.toString(), message.toString());
        try {
            String payload = message.getPayload();
            sendNotification(session, payload, "actual-payloads");
            Map<String, String> commandMap = objectMapper.readValue(payload, Map.class);
            sendNotification(session, commandMap.get("name_of_input"), "chat-messages");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid JSON received", e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Websocket connection established, session ID: {}, session remote address: {}", session.getId(), session.getRemoteAddress());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    private void sendNotification(WebSocketSession session, String payload, String targetDomElement) throws IOException {
        session.sendMessage(new TextMessage(
                "<div id=\"" + targetDomElement + "\" hx-swap-oob='beforeend'>" +
                        "<p><code>"
                        + payload
                        + "</code></p>" +
                        "</div>"));
    }
}
