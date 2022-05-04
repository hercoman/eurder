package com.switchfully.eurderproject.item_group.service;

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

    public ItemGroup toItemGroup(CreateItemGroupDTO createItemGroupDTO) {
        return new ItemGroup(
                createItemGroupDTO.getItemId(),
                createItemGroupDTO.getAmount(),
                itemRepository.getById(createItemGroupDTO.getItemId()).getPrice(),
                calculateShippingDate(createItemGroupDTO.getAmount(), itemRepository.getById(createItemGroupDTO.getItemId()).getAmountAvailable())
                );
    }

    public List<ItemGroup> toItemGroup(Collection<CreateItemGroupDTO> createItemGroupDTOCollection) {
        return createItemGroupDTOCollection.stream()
                .map(this::toItemGroup)
                .collect(Collectors.toList());
    }

    private LocalDate calculateShippingDate(int amount, int amountAvailable) {
        if (amount <= amountAvailable) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusDays(7);
    }

    public ItemGroupDTO toItemGroupDTO(com.switchfully.eurderproject.item_group.domain.ItemGroup itemGroup) {
        return new ItemGroupDTO()
                .setId(itemGroup.getId())
                .setItemId(itemGroup.getItemId())
                .setAmount(itemGroup.getAmount())
                .setPricePerUnit(itemGroup.getPricePerUnit())
                .setShippingDate(itemGroup.getShippingDate());
    }

    public List<ItemGroupDTO> toItemGroupDTO(Collection<com.switchfully.eurderproject.item_group.domain.ItemGroup> itemGroupCollection) {
        return itemGroupCollection.stream()
                .map(this::toItemGroupDTO)
                .collect(Collectors.toList());
    }
}
