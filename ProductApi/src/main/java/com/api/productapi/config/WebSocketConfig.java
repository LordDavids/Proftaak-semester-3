package com.api.productapi.config;

import com.api.productapi.sockets.ProductStockHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ProductStockHandler productStockHandler;

    public WebSocketConfig(ProductStockHandler productStockHandler) {
        this.productStockHandler = productStockHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(productStockHandler, "/wbst/product-stock").setAllowedOrigins("*");
    }

}
