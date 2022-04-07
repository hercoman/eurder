package com.switchfully.eurderproject.item.service;

import com.switchfully.eurderproject.item.api.CreateItemDTO;
import com.switchfully.eurderproject.item.api.ItemDTO;
import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.domain.Item;
import com.switchfully.eurderproject.item.domain.ItemGroup;
import com.switchfully.eurderproject.item.domain.ItemRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    public ItemDTO toDTO(Item item) {
        return new ItemDTO()
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmountAvailable(item.getAmountAvailable());
    }

    public ItemGroup toItemGroup(ItemGroupDTO itemGroupDTO) {
        String itemId = itemGroupDTO.getItemId();
        double orderPricePerUnit = itemRepository.getItemById(itemId).getPrice();
        int amountAvailable = itemRepository.getItemById(itemId).getAmountAvailable();
        LocalDate shippingDate = calculateShippingDate(itemGroupDTO, amountAvailable);
        return new ItemGroup(
                itemGroupDTO.getItemId(),
                itemGroupDTO.getAmount(),
                orderPricePerUnit,
                shippingDate);
    }

    private LocalDate calculateShippingDate(ItemGroupDTO itemGroupDTO, int amountAvailable) {
        if (itemGroupDTO.getAmount() > amountAvailable) {
            return LocalDate.now().plusDays(1);
        }
        return LocalDate.now().plusDays(7);
    }
}
