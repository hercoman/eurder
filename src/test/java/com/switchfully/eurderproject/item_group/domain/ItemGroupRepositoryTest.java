package com.switchfully.eurderproject.item_group.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ItemGroupRepositoryTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemGroupRepository itemGroupRepository;

    @Test
    void createItemGroup_givenAnItemGroupToCreate_thenTheNewlyCreatedItemGroupIsSavedCorrectly() {
        ItemGroup itemGroup = new ItemGroup("123", 10, 0.25, LocalDate.now().plusDays(1));
        itemGroupRepository.save(itemGroup);

        ItemGroup savedItemGroup = itemGroupRepository.getById(itemGroup.getId());
        Assertions.assertThat(savedItemGroup.getItemId()).isEqualTo("123");
        Assertions.assertThat(savedItemGroup.getAmount()).isEqualTo(10);
        Assertions.assertThat(savedItemGroup.getPricePerUnit()).isEqualTo(0.25);
        Assertions.assertThat(savedItemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(1));
    }
}