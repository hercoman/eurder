package com.switchfully.eurderproject.item.domain;

import com.switchfully.eurderproject.item.api.ItemGroupDTO;
import com.switchfully.eurderproject.item.service.ItemMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemGroupTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemGroupRepository itemGroupRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void createItemGroup_givenAnItemGroupToCreate_thenTheNewlyCreatedItemGroupIsSavedCorrectly() {
        Item item = new Item("Tomato", "A clean, round tomato with lots of vitamins", 0.125, 10);
        itemRepository.save(item);
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO()
                .setItemId(item.getId())
                .setAmount(5);
        ItemGroup itemGroup = itemMapper.toItemGroup(itemGroupDTO);
        itemGroupRepository.save(itemGroup);

        Assertions.assertThat(itemGroupRepository.getById(itemGroup.getId())).isEqualTo(itemGroup);
        Assertions.assertThat(itemGroup.getAmount()).isEqualTo(5);
        Assertions.assertThat(itemGroup.getPricePerUnit()).isEqualTo(0.125);
        Assertions.assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(1));
    }
}