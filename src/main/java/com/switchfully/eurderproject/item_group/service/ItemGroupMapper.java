package com.switchfully.eurderproject.item_group.service;

import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemGroupMapper {

    private final ItemRepository itemRepository;

    public ItemGroupMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemGroup toItemGroup(Item item, int amount) {
        return new ItemGroup(item, amount);
    }

//    public ItemGroup toItemGroup(CreateItemGroupDTO createItemGroupDTO, double pricePerUnit, int amountAvailableInStock) {
//        return new ItemGroup(

//                createItemGroupDTO.getItemId(),
//                createItemGroupDTO.getAmount(),
//                pricePerUnit,
//                amountAvailableInStock);
//    }

    public ItemGroupDTO toItemGroupDTO(ItemGroup itemGroup) {
        return new ItemGroupDTO()
                .setId(itemGroup.getId())
                .setItemId(itemGroup.getItem().getId())
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
