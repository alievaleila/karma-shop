package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.model.Order;

public interface TelegramService {
    void sendOrderNotification(Order order);
}
