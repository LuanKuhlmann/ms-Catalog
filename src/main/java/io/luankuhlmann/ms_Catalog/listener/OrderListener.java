/*package io.luankuhlmann.ms_Catalog.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.luankuhlmann.ms_Catalog.model.SKU;
import io.luankuhlmann.ms_Catalog.repository.SKURepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderListener {
    private final ObjectMapper objectMapper;
    private final SKURepository skuRepository;

    @Autowired
    public OrderListener(ObjectMapper objectMapper, SKURepository skuRepository) {
        this.objectMapper = objectMapper;
        this.skuRepository = skuRepository;
    }

    @RabbitListener(queues = "order-queue")
    public void receiveMessage(String message) {
        try {
            // Convert JSON message to OrderMessage object
            OrderMessage orderMessage = objectMapper.readValue(message, OrderMessage.class);

            // Process the order message
            processOrderMessage(orderMessage);

        } catch (IOException e) {
            // Handle the exception
            System.err.println("Failed to convert message to OrderMessage object: " + e.getMessage());
        }
    }

    private void processOrderMessage(OrderMessage orderMessage) {
        for (OrderMessage.SkuQuantity skuQuantity : orderMessage.getSkus()) {
            SKU sku = skuRepository.findById(skuQuantity.getId())
                    .orElseThrow(() -> new RuntimeException("SKU not found"));

            // Update SKU quantity
            sku.setQuantity(sku.getQuantity() - skuQuantity.getQuantity());
            skuRepository.save(sku);
        }
    }
}*/
