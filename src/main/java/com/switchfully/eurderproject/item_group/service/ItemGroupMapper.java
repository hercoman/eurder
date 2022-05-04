package com.switchfully.eurderproject.item_group.service;

import com.switchfully.eurderproject.item.domain.ItemRepository;
import com.switchfully.eurderproject.item_group.api.dto.CreateItemGroupDTO;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupDTO;
import com.switchfully.eurderproject.item_group.domain.ItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemGroupMapper {

    private final Logger itemGroupMapperLogger = LoggerFactory.getLogger(ItemGroupMapper.class);

    private final ItemRepository itemRepository;

    public ItemGroupMapper(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemGroup toItemGroup(CreateItemGroupDTO createItemGroupDTO) {
        return new ItemGroup(
                createItemGroupDTO.getItemId(),
                validateAmount(createItemGroupDTO.getAmount()),
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

    private int validateAmount(int amount) {
        if (amount < 0) {
            itemGroupMapperLogger.error("Negative amount input for item group");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create item group with negative amount");
        }
        itemGroupMapperLogger.info("Successfully validated new item group amount");
        return amount;
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
