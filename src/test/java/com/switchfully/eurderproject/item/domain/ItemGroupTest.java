package com.switchfully.eurderproject.item.domain;

import com.switchfully.eurderproject.item.service.ItemMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemGroupTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemGroupRepository itemGroupRepository;

    @Test
    void createItemGroup_givenAnItemGroupToCreate_thenTheNewlyCreatedItemGroupIsSavedCorrectly() {
        ItemGroup itemGroup = new ItemGroup(10, 0.125, LocalDate.now().plusDays(1));
        itemGroupRepository.save(itemGroup);

        Assertions.assertThat(itemGroupRepository.getById(itemGroup.getId())).isEqualTo(itemGroup);
        Assertions.assertThat(itemGroup.getAmount()).isEqualTo(10);
        Assertions.assertThat(itemGroup.getPricePerUnit()).isEqualTo(0.125);
        Assertions.assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    void createItemGroup_givenAnItemGroupWithNegativeAmount_thenThrowResponseStatusException() {
        Assertions.assertThatThrownBy(() -> new ItemGroup(-10, 0.125, LocalDate.now().plusDays(1)))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Unable to create item group with negative amount");
    }

    @Test
    void createItemGroup_givenAnItemGroupWithNegativePricePerUnit_thenThrowResponseStatusException() {
        Assertions.assertThatThrownBy(() -> new ItemGroup(10, -0.125, LocalDate.now().plusDays(1)))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Unable to create item group with negative price per unit");
    }
}