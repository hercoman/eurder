package com.switchfully.eurderproject.item_group.service;

import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupReportDTO;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemGroupMapper {

    public ItemGroup toItemGroup(Item item, int amount) {
        return new ItemGroup(item, amount);
    }

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

    public ItemGroupReportDTO toItemGroupReportDTO(ItemGroup itemGroup) {
        return new ItemGroupReportDTO(
                itemGroup.getItem().getName(),
                itemGroup.getAmount(),
                itemGroup.getAmount() * itemGroup.getPricePerUnit()
        );
    }

    public List<ItemGroupReportDTO> toItemGroupReportDTO(List<ItemGroup> itemGroupList) {
        return itemGroupList.stream()
                .map(this::toItemGroupReportDTO)
                .collect(Collectors.toList());
    }
}
