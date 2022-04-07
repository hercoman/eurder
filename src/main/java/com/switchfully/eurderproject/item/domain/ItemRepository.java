package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Logger itemRepositoryLogger = LoggerFactory.getLogger(ItemRepository.class);

    private final Map<String, Item> itemsById;

    public ItemRepository() {
        this.itemsById = new HashMap<>();
    }

    public void save(Item item) {
        itemsById.put(item.getId(), item);
    }

    public boolean isNameUnique(String name) {
        return itemsById.values().stream()
        .anyMatch(customer -> customer.getName().equalsIgnoreCase(name));
    }

    public Item getItemById(String itemId) {
        Item foundItem = itemsById.get(itemId);
        if(foundItem == null){
            itemRepositoryLogger.error("No item could be found for id " + itemId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item found for id " + itemId);
        }
        return foundItem;
    }
}
