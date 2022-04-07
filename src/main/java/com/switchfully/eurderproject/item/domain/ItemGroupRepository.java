package com.switchfully.eurderproject.item.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemGroupRepository {
    private final Map<String, ItemGroup> itemGroupById;

    public ItemGroupRepository() {
        this.itemGroupById = new HashMap<>();
    }
    public void save(ItemGroup itemGroup) {
        itemGroupById.put(itemGroup.getId(), itemGroup);
    }

    public ItemGroup getById(String id) {
        return null;
    }
}
