package com.switchfully.eurderproject.order.api.dto;

import com.google.common.collect.Lists;
import com.switchfully.eurderproject.item_group.api.dto.ItemGroupReportDTO;

import java.util.List;

public class OrderReportDTO {
    public List<ItemGroupReportDTO> getItemGroupReportDTOList() {
        return Lists.newArrayList(new ItemGroupReportDTO());
    }
}
