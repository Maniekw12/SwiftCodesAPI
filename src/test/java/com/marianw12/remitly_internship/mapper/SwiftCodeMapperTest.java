package com.marianw12.remitly_internship.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.request.SwiftCodeHeadquarterResponse;
import com.marianw12.remitly_internship.helpers.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwiftCodeMapperTest {

    private SwiftCodeMapper swiftCodeMapper = new SwiftCodeMapper();

    @Test
    public void shouldMapEntityToResponse() {
        //given
        SwiftCodeEntity entity = SwiftCodeEntity.builder()
                .swiftCode("MSDMPLP2XXX")
                .isHeadquarter(true)
                .bankName("MICHAEL STROM DOM MAKLERSKI S.A.")
                .countryIso2("PL")
                .address("ALEJE JEROZOLIMSKIE 100, FLOOR 6  WARSZAWA, MAZOWIECKIE, 00-807")
                .countryName("Poland")
                .build();

        //when
        SwiftCodeBranchResponse response = swiftCodeMapper.mapToResponse(entity);

        //then
        assertNotNull(response);
        assertEquals("MSDMPLP2XXX", response.getSwiftCode());
        //assertTrue(response.isHeadquarter());
        assertEquals("MICHAEL STROM DOM MAKLERSKI S.A.", response.getBankName());
        assertEquals("PL", response.getCountryISO2());
        assertEquals("ALEJE JEROZOLIMSKIE 100, FLOOR 6  WARSZAWA, MAZOWIECKIE, 00-807", response.getAddress());
        assertEquals("Poland", response.getCountryName());
    }

    @Test
    public void shouldMapRequestToEntity() {
        //given
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest.builder()
                .swiftCode("LLBBLV2X222")
                .countryISO2("LV")
                .countryName("Latvia")
                .address("ANTONIJAS 3  RIGA, RIGA, LV-1010")
                .isHeadquarter(false)
                .bankName("SIGNET BANK AS")
                .build();

        //when
        SwiftCodeEntity entity = swiftCodeMapper.mapToEntity(request);

        //then
        assertNotNull(entity);
        assertEquals("LLBBLV2X222", entity.getSwiftCode());
        assertEquals("LV", entity.getCountryIso2());
        assertEquals("LATVIA", entity.getCountryName());
        assertEquals("ANTONIJAS 3  RIGA, RIGA, LV-1010", entity.getAddress());
        assertFalse(entity.isHeadquarter());
        assertEquals("SIGNET BANK AS", entity.getBankName());
    }

    @Test
    public void shouldMapEntityWithBranchesToSwiftCodeHeadquarterResponse() throws JsonProcessingException {
        //given
        SwiftCodeEntity headquarter = SwiftCodeEntity.builder()
                .swiftCode("TESTPLPWXXX")
                .isHeadquarter(true)
                .bankName("Test Bank")
                .countryIso2("PL")
                .address("HQ Address")
                .countryName("Poland")
                .build();

        SwiftCodeEntity branch = SwiftCodeEntity.builder()
                .swiftCode("TESTPLPW")
                .isHeadquarter(false)
                .bankName("Test Bank")
                .countryIso2("PL")
                .address("Branch Address")
                .countryName("Poland")
                .build();

        List<SwiftCodeEntity> branches = List.of(branch);

        //when
        SwiftCodeHeadquarterResponse response = swiftCodeMapper.mapToResponse(headquarter, branches);

        //then
        assertNotNull(response);
        assertEquals("TESTPLPWXXX", response.getSwiftCode());
        assertTrue(TestHelper.extractIsHeadquarter(response));
        assertEquals(1, response.getBranches().size());
        assertEquals("TESTPLPW", response.getBranches().get(0).getSwiftCode());
    }

    @Test
    public void shouldReturnBranchResponseForNonHeadquarterEntity() throws JsonProcessingException {
        //given
        SwiftCodeEntity headquarter = SwiftCodeEntity.builder()
                .swiftCode("TESTPLPWXXX")
                .isHeadquarter(true)
                .bankName("Test Bank")
                .countryIso2("PL")
                .address("HQ Address")
                .countryName("Poland")
                .build();

        SwiftCodeEntity branch = SwiftCodeEntity.builder()
                .swiftCode("TESTPLPW")
                .isHeadquarter(false)
                .bankName("Test Bank")
                .countryIso2("PL")
                .address("Branch Address")
                .countryName("Poland")
                .build();

        List<SwiftCodeEntity> branches = List.of(branch);

        //when
        SwiftCodeBranchResponse response = swiftCodeMapper.mapToResponse(branch);

        //then
        assertNotNull(response);
        assertEquals("TESTPLPW", response.getSwiftCode());
        assertFalse(TestHelper.extractIsHeadquarter(response));

    }

    @Test
    public void shouldReturnEmptyListWhenMappingEmptyList() {
        // given
        List<SwiftCodeEntity> emptyList = List.of();

        // when
        List<SwiftCodeBranchResponse> responses = swiftCodeMapper.mapToResponse(emptyList);

        // then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }


}