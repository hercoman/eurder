package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.CreateItemDTO;
import com.switchfully.eurderproject.item.api.ItemDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ItemService {
    private final Logger itemLogger = LoggerFactory.getLogger(ItemService.class);

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDTO createItem(CreateItemDTO createItemDTO) {
        assertNameIsUnique(itemRepository.isNameUnique(createItemDTO.getName()));
        Item item = itemMapper.toItem(createItemDTO);
        itemRepository.save(item);
        return itemMapper.toItemDTO(item);
    }

    private void assertNameIsUnique(boolean condition) {
        if (condition) {
            itemLogger.error("Failed attempt to create item that already exists in repository");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create item: item already exists");
        }
        itemLogger.info("Successfully validated new item name to be unique");
    }
}
