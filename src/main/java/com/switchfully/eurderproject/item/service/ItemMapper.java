package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.CreateItemDTO;
import com.switchfully.eurderproject.item.api.ItemDTO;
import com.switchfully.eurderproject.item.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item toItem(CreateItemDTO createItemDTO) {
        return new Item(
                createItemDTO.getName(),
                createItemDTO.getDescription(),
                createItemDTO.getPrice(),
                createItemDTO.getAmountAvailable());
    }

    public ItemDTO toDTO(Item item) {
        return new ItemDTO()
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmountAvailable(item.getAmountAvailable());
    }
}
