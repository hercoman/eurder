package com.switchfully.eurderproject.infrastructure.service;

import com.switchfully.eurderproject.infrastructure.api.dto.PostalCodeDTO;
import com.switchfully.eurderproject.infrastructure.domain.PostalCode;
import org.springframework.stereotype.Component;

@Component
public class PostalCodeMapper {

    public PostalCode toPostalCode(PostalCodeDTO postalCode) {
        return new PostalCode(
                postalCode.getZipcode(),
                postalCode.getCity());
    }

    public PostalCodeDTO toDTO(PostalCode postalCode) {
        return new PostalCodeDTO(
                postalCode.getZipcode(),
                postalCode.getCity());
    }
}
