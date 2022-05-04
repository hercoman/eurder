package com.switchfully.eurderproject.item.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

//    public Item getItemById(String itemId) {
//        Item foundItem = itemsById.get(itemId);
//        if(foundItem == null){
//            itemRepositoryLogger.error("No item could be found for id " + itemId);
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item found for id " + itemId);
//        }
//        return foundItem;
//    }
}
