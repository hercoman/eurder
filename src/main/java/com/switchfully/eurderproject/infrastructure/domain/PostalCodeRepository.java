package com.switchfully.eurderproject.infrastructure.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {
}
