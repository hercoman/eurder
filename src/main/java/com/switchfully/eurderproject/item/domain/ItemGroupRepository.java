package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemGroupRepository {
    private final Logger repositoryLogger = LoggerFactory.getLogger(ItemGroupRepository.class);

    private final Map<String, ItemGroup> itemGroupById;

    public ItemGroupRepository() {
        this.itemGroupById = new HashMap<>();
    }
    public void save(ItemGroup itemGroup) {
        itemGroupById.put(itemGroup.getId(), itemGroup);
    }

    public ItemGroup getById(String itemGroupId) {
        ItemGroup foundItemGroup = itemGroupById.get(itemGroupId);
        if(foundItemGroup == null) {
            repositoryLogger.error("No item group could be found for id " + itemGroupId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return foundItemGroup ;
    }
}
