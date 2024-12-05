package com.api.productapi.sockets;

import com.api.productapi.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ProductStockHandler extends TextWebSocketHandler {

    private static Set<WebSocketSession> sessions = new HashSet<>();

    private final ProductService productService;

    public ProductStockHandler(ProductService productService) {
        this.productService = productService;

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // Get the message content as a string
        try {
            // Attempt to parse the message as an integer
            Integer productId = Integer.parseInt(payload);
            // If the parsing is successful, the message is a valid integer
            session.getAttributes().put("ProductId", productId);
            System.out.println("Product ID: " + session.getAttributes().get("ProductId"));
        } catch (NumberFormatException e) {
            // If a NumberFormatException is thrown, the message is not a valid number
            session.sendMessage(new TextMessage("Invalid number format."));
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendProductStock() throws Exception {
        for (WebSocketSession session : sessions) {
            if (session.isOpen() && session.getAttributes().containsKey("ProductId")) {
                try {
                    Integer productId = (Integer) session.getAttributes().get("ProductId");
                    Integer stock = productService.getProductStock(productId).orElseThrow(() -> new SocketException("Product not found"));
                    session.sendMessage(new TextMessage(stock.toString()));

                } catch (SocketException e) {
                    session.sendMessage(new TextMessage("Product not found."));
                } catch (IOException e) {
                    session.sendMessage(new TextMessage("Error sending message."));
                }
            }

        }
    }
}

