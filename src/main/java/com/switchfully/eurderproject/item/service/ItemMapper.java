package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.dto.CreateItemDTO;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item.api.dto.ItemDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    private final ItemRepository itemRepository;

    public ItemMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item toItem(CreateItemDTO createItemDTO) {
        return new Item(
                createItemDTO.getName(),
                createItemDTO.getDescription(),
                createItemDTO.getPrice(),
                createItemDTO.getAmountAvailable());
    }

    public ItemDTO toItemDTO(Item item) {
        return new ItemDTO()
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmountAvailable(item.getAmountAvailable());
    }

}
