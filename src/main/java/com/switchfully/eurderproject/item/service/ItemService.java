package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.dto.CreateItemDTO;
import com.switchfully.eurderproject.item.api.dto.ItemDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ItemService {
    private final Logger itemServiceLogger = LoggerFactory.getLogger(ItemService.class);

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDTO createItem(CreateItemDTO createItemDTO) {
        assertNameIsUnique(createItemDTO.getName());
        Item item = itemMapper.toItem(createItemDTO);
        itemRepository.save(item);
        return itemMapper.toItemDTO(item);
    }

    private void assertNameIsUnique(String name) {
        if (itemRepository.findAll().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(name))) {
            itemServiceLogger.error("Failed attempt to create item that already exists in repository");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create item: item already exists");
        }
        itemServiceLogger.info("Successfully validated new item name to be unique");
    }
}
