package com.switchfully.eurderproject.item.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<String, Item> itemsById;

    public ItemRepository() {
        this.itemsById = new HashMap<>();
    }

    public void save(Item item) {
        itemsById.put(item.getId(), item);
    }
}
