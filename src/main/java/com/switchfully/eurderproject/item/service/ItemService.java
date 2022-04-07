package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.customer.api.CustomerDTO;
import com.switchfully.eurderproject.customer.domain.Customer;
import com.switchfully.eurderproject.item.api.CreateItemDTO;
import com.switchfully.eurderproject.item.api.ItemDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public ItemService(ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    public ItemDTO createItem(CreateItemDTO createItemDTO) {
        Item item = itemMapper.toItem(createItemDTO);
        itemRepository.save(item);
        return itemMapper.toDTO(item);
    }
}
