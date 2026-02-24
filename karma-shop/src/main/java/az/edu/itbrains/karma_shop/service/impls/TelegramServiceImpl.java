package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.Order;
import az.edu.itbrains.karma_shop.model.OrderItem;
import az.edu.itbrains.karma_shop.service.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramServiceImpl implements TelegramService {

    private static final Logger log = LoggerFactory.getLogger(TelegramServiceImpl.class);

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chatId}")
    private String chatId;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendOrderNotification(Order order) {
        try {
            String message = buildOrderMessage(order);
            String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

            Map<String, Object> request = new HashMap<>();
            request.put("chat_id", chatId);
            request.put("text", message);
            request.put("parse_mode", "HTML");

            restTemplate.postForObject(url, request, String.class);
            log.info("Telegram notification sent for order: {}", order.getOrderNumber());
        } catch (Exception e) {
            log.error("Failed to send Telegram notification for order: {}", order.getOrderNumber(), e);
        }
    }

    private String buildOrderMessage(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append("🛒 <b>Yeni Sifariş!</b>\n\n");
        sb.append("📋 <b>Sifariş №:</b> ").append(order.getOrderNumber()).append("\n");
        sb.append("📅 <b>Tarix:</b> ").append(order.getCreatedAt()).append("\n\n");

        sb.append("👤 <b>Müştəri Məlumatları:</b>\n");
        sb.append("Ad: ").append(order.getFirstName()).append(" ").append(order.getLastName()).append("\n");
        sb.append("Email: ").append(order.getEmail()).append("\n");
        sb.append("Telefon: ").append(order.getPhone()).append("\n");
        if (order.getCompany() != null && !order.getCompany().isEmpty()) {
            sb.append("Şirkət: ").append(order.getCompany()).append("\n");
        }

        sb.append("\n📍 <b>Ünvan:</b>\n");
        sb.append(order.getAddressLine1()).append("\n");
        if (order.getAddressLine2() != null && !order.getAddressLine2().isEmpty()) {
            sb.append(order.getAddressLine2()).append("\n");
        }
        sb.append(order.getCity());
        if (order.getDistrict() != null && !order.getDistrict().isEmpty()) {
            sb.append(", ").append(order.getDistrict());
        }
        sb.append(", ").append(order.getCountry()).append("\n");
        if (order.getZipCode() != null && !order.getZipCode().isEmpty()) {
            sb.append("Poçt kodu: ").append(order.getZipCode()).append("\n");
        }

        if (order.isShipToDifferentAddress()) {
            sb.append("\n📦 <b>Çatdırılma Ünvanı:</b>\n");
            sb.append(order.getShippingFirstName()).append(" ").append(order.getShippingLastName()).append("\n");
            sb.append(order.getShippingAddressLine1()).append("\n");
            if (order.getShippingAddressLine2() != null && !order.getShippingAddressLine2().isEmpty()) {
                sb.append(order.getShippingAddressLine2()).append("\n");
            }
            sb.append(order.getShippingCity());
            if (order.getShippingDistrict() != null && !order.getShippingDistrict().isEmpty()) {
                sb.append(", ").append(order.getShippingDistrict());
            }
            sb.append(", ").append(order.getShippingCountry()).append("\n");
        }

        sb.append("\n🛍 <b>Məhsullar:</b>\n");
        for (OrderItem item : order.getOrderItems()) {
            sb.append("• ").append(item.getProductName());
            sb.append(" x").append(item.getQuantity());
            sb.append(" — $").append(String.format("%.2f", item.getTotal())).append("\n");
        }

        sb.append("\n💰 <b>Ödəniş:</b>\n");
        sb.append("Ara cəm: $").append(String.format("%.2f", order.getSubtotal())).append("\n");
        sb.append("Çatdırılma: $").append(String.format("%.2f", order.getShippingCost())).append("\n");
        if (order.getDiscount() != null && order.getDiscount() > 0) {
            sb.append("Endirim: -$").append(String.format("%.2f", order.getDiscount()));
            if (order.getCouponCode() != null) {
                sb.append(" (").append(order.getCouponCode()).append(")");
            }
            sb.append("\n");
        }
        sb.append("<b>Cəmi: $").append(String.format("%.2f", order.getTotal())).append("</b>\n");
        if (order.getPaymentMethod() != null && !order.getPaymentMethod().toString().equals("CHECK_PAYMENTS")) {
            sb.append("Ödəniş üsulu: ").append(order.getPaymentMethod()).append("\n");
        }

        if (order.getOrderNotes() != null && !order.getOrderNotes().isEmpty()) {
            sb.append("\n📝 <b>Qeyd:</b> ").append(order.getOrderNotes()).append("\n");
        }

        return sb.toString();
    }
}
