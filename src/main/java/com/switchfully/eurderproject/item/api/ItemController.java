package com.switchfully.eurderproject.item.api;

import com.switchfully.eurderproject.item.api.dto.CreateItemDTO;
import com.switchfully.eurderproject.item.api.dto.ItemDTO;
import com.switchfully.eurderproject.item.service.ItemService;
import com.switchfully.eurderproject.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.switchfully.eurderproject.security.Feature.*;

@RestController
@RequestMapping(path = "items")
public class ItemController {

    private final ItemService itemService;
    private final SecurityService securityService;

    public ItemController(ItemService itemService, SecurityService securityService) {
        this.itemService = itemService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO createItem(@RequestHeader String authorization, @RequestBody CreateItemDTO createItemDTO) {
        securityService.validateAuthorization(authorization, ADD_ITEM);
        return itemService.createItem(createItemDTO);
    }
}
