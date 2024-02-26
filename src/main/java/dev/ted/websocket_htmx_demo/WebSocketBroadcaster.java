package dev.ted.websocket_htmx_demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketBroadcaster is a class that handles broadcasting WebSocket messages
 * to multiple client sessions, keeping track of which clients are currently connected.
 */
@RestController
public class WebSocketBroadcaster extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketBroadcaster.class);

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;
    private boolean active;

    @Autowired
    public WebSocketBroadcaster(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Sends a WebSocket message to all connected client sessions.
     *
     * @param message the message to be sent
     */
    public void send(String message) throws IOException {
//        sessionMap.values().forEach(session -> {
//            try {
//                session.sendMessage(new TextMessage(message));
//            } catch (ClosedChannelException cce) {
//                LOGGER.debug("Got Closed Channel Exception, but don't care if we're shutting down", cce);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : sessionMap.values()) {
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(), session);
        LOGGER.debug("Session connected: {}", session);
        broadcastUpdate();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
        LOGGER.debug("Session DISconnected: {}", session);
        broadcastUpdate();
    }

    @Override
    // language=html
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.debug("Text Message received: {}", message);
        String payload = message.getPayload();
        Map<String, Object> payloadAsMap = objectMapper.readValue(payload, new TypeReference<>() {
        });

        if (payloadAsMap.containsKey("websocket-command")) {
            active = !active;
            String updateCurrentState = """
                <swap-target id="ws-post-result-current-state" hx-swap-oob="innerHTML">
                %s
                </swap-target>
                """.formatted(active ? "Currently Active" : "Currently Inactive");

            String updateButtonText = """
                <swap-target id="ws-post-result-state-button" hx-swap-oob="innerHTML">
                %s
                </swap-target>
                """.formatted(active ? "Deactivate" : "Activate");
            send(updateCurrentState + updateButtonText);
            return;
        }

        send("""
                     <swap-target id="ws-post-receive-current-state" hx-swap-oob="innerHTML">
                     %s (from %s)
                     </swap-target>
                     """.formatted(payloadAsMap, session.getId()));
    }

    // language=html
    private void broadcastUpdate() throws IOException {
        send("""
                     <swap-target id="current-state-clients-connected" hx-swap-oob="innerHTML">
                     There are %d clients connected.
                     </swap-target>"""
                     .formatted(sessionMap.size()));
    }


    @PostMapping("/next-state-websocket")
    // language=html
    public void postToggleActive() throws IOException {
        active = !active;

        String updateCurrentState = """
                <swap-target id="post-result-current-state" hx-swap-oob="innerHTML">
                %s
                </swap-target>
                """.formatted(active ? "Currently Active" : "Currently Inactive");

        String updateButtonText = """
                <swap-target id="post-result-state-button" hx-swap-oob="innerHTML">
                %s
                </swap-target>
                """.formatted(active ? "Deactivate" : "Activate");
        send(updateCurrentState + updateButtonText);
    }

}
