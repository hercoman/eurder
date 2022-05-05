package com.switchfully.eurderproject.item_group.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup, String> {
//    private final Logger repositoryLogger = LoggerFactory.getLogger(ItemGroupRepository.class);

//    public ItemGroup getById(String itemGroupId) {
//        ItemGroup foundItemGroup = itemGroupById.get(itemGroupId);
//        if(foundItemGroup == null) {
//            repositoryLogger.error("No item group could be found for id " + itemGroupId);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item group could be found for id " + itemGroupId);
//        }
//        return foundItemGroup ;
//    }
}
