package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.dto.CreateItemDTO;
import com.switchfully.eurderproject.item.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item.api.dto.ItemDTO;
import com.switchfully.eurderproject.item.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    public ItemGroup toItemGroup(CreateItemGroupDTO createItemGroupDTO) {
        return new ItemGroup(itemRepository.getItemById(createItemGroupDTO.getItemId()), createItemGroupDTO.getAmount());
    }

    public List<ItemGroup> toItemGroup(Collection<CreateItemGroupDTO> createItemGroupDTOCollection) {
        return createItemGroupDTOCollection.stream()
                .map(this::toItemGroup)
                .collect(Collectors.toList());
    }

    public ItemGroupDTO toItemGroupDTO(ItemGroup itemGroup) {
        return new ItemGroupDTO()
                .setId(itemGroup.getId())
                .setItemId(itemGroup.getItemId())
                .setAmount(itemGroup.getAmount())
                .setPricePerUnit(itemGroup.getPricePerUnit())
                .setShippingDate(itemGroup.getShippingDate());
    }

    public List<ItemGroupDTO> toItemGroupDTO(Collection<ItemGroup> itemGroupCollection) {
        return itemGroupCollection.stream()
                .map(this::toItemGroupDTO)
                .collect(Collectors.toList());
    }

}
