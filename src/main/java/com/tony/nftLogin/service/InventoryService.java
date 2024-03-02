package com.tony.nftLogin.service;

import com.tony.nftLogin.Message;
import com.tony.nftLogin.model.Inventory;
import com.tony.nftLogin.model.Item;
import com.tony.nftLogin.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Message.GetInventoryResponse getInventoryForUser(Long userId) {
        List<Inventory> relations = inventoryRepository.findByUserUserId(userId);

        Message.GetInventoryResponse.Builder responseBuilder = Message.GetInventoryResponse.newBuilder();
        responseBuilder.setUserId(userId);

        relations.forEach(relation -> {
            Item item = relation.getItem();
            Message.InventoryItem inventoryItem = Message.InventoryItem.newBuilder()
                    .setItemId(item.getItemId())
                    .setAmount(relation.getQuantity())
                    .build();
            responseBuilder.addItem(inventoryItem);
        });

        return responseBuilder.build();
    }
}
