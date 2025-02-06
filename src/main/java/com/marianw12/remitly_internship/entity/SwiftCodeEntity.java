package com.marianw12.remitly_internship.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "swift_codes",
        indexes = @Index(name = "country_iso2_idx", columnList = "countryIso2") //For faster lookup by countryIso2
)
public class SwiftCodeEntity {
    @Id
    @Column(unique = true, nullable = false, length = 11)
    private String swiftCode;
    private String address;
    private String bankName;
    @Column(nullable = false, length = 2)
    private String countryIso2;
    private String countryName;
    private boolean isHeadquarter;
}
