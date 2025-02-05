package com.marianw12.remitly_internship.repository;

import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SwiftCodeRepository extends JpaRepository<SwiftCodeEntity,String> {

    Optional<SwiftCodeEntity> findBySwiftCode(String swiftCode);

    List<SwiftCodeEntity> findByCountryIso2(String countryIso2);

    List<SwiftCodeEntity> findBySwiftCodeStartingWith(String prefix);


}
