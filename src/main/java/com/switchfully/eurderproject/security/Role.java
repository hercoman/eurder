package com.switchfully.eurderproject.security;

import java.util.ArrayList;
import java.util.List;

import static com.switchfully.eurderproject.security.Feature.*;

public enum Role {
    ADMIN(new ArrayList<>(List.of(
            ADD_ITEM,
            ORDER_ITEMS,
            VIEW_CUSTOMERS,
            VIEW_CUSTOMER_BY_ID
    ))),
    CUSTOMER(new ArrayList<>(List.of(
            ORDER_ITEMS
    )));

    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
