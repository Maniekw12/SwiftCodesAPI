package com.marianw12.remitly_internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.parser.DataParser;
import com.marianw12.remitly_internship.repository.SwiftCodeRepository;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.request.SwiftCodeHeadquarterResponse;
import com.marianw12.remitly_internship.service.SwiftCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class SwiftCodeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Test
    void shouldCreateNewSwiftCodeSuccessfully() throws Exception {
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest.builder()
                .swiftCode("RBFTAWAF")
                .countryISO2("PL")
                .address("KONSTRUKTORSKA 12A  WARSZAWA, MAZOWIECKIE, 02-673")
                .bankName("PROSERVICE FINTECO SP Z O.O.")
                .countryName("POLAND")
                .isHeadquarter(false)
                .build();

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestOnCreateWhenSwiftCodeInvalid() throws Exception {
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest.builder()
                .swiftCode("PSATPLPWXXXX")
                .countryISO2("PL")
                .address("KONSTRUKTORSKA 12A  WARSZAWA, MAZOWIECKIE, 02-673")
                .bankName("PROSERVICE FINTECO SP Z O.O.")
                .countryName("Poland")
                .isHeadquarter(false)
                .build();

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnCreateWhenCountryIso2Invalid() throws Exception {
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest.builder()
                .swiftCode("PSATPLPWXXX")
                .countryISO2("XD")
                .address("KONSTRUKTORSKA 12A  WARSZAWA, MAZOWIECKIE, 02-673")
                .bankName("PROSERVICE FINTECO SP Z O.O.")
                .countryName("Poland")
                .isHeadquarter(false)
                .build();

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestOnCreateWhenCountyNameInvalid() throws Exception {
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest.builder()
                .swiftCode("PSATPLPWXXX")
                .countryISO2("PL")
                .address("KONSTRUKTORSKA 12A  WARSZAWA, MAZOWIECKIE, 02-673")
                .bankName("PROSERVICE FINTECO SP Z O.O.")
                .countryName("Boland")
                .isHeadquarter(false)
                .build();

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetExistingSwiftCodeBranchSuccess() throws Exception {
        //given
        swiftCodeRepository.save(SwiftCodeEntity.builder()
                .swiftCode("CITIBGSFTRD")
                .countryIso2("BG")
                .address("SITNYAKOVO BLVD 48 SERDIKA OFFICES, FLOOR 10 SOFIA, SOFIA, 1505")
                .bankName("CITIBANK EUROPE PLC, BULGARIA BRANCH")
                .countryName("BULGARIA")
                .build());

        SwiftCodeBranchResponse expected = SwiftCodeBranchResponse
                .builder()
                .swiftCode("CITIBGSFTRD")
                .countryISO2("BG")
                .address("SITNYAKOVO BLVD 48 SERDIKA OFFICES, FLOOR 10 SOFIA, SOFIA, 1505")
                .bankName("CITIBANK EUROPE PLC, BULGARIA BRANCH")
                .countryName("BULGARIA")
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/v1/swift-codes/CITIBGSFTRD"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        SwiftCodeBranchResponse actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SwiftCodeBranchResponse.class);

        assertEquals(expected,actual);
    }

    @Test
    void shouldGetExistingSwiftCodeHeadquarterSuccess() throws Exception {
        // given
        swiftCodeRepository.save(SwiftCodeEntity.builder()
                .swiftCode("CESDMTM1XXX")
                .countryIso2("MT")
                .address("27 PIETRO FLORIANI STREET  FLORIANA, IL-FURJANA, FRN 1060")
                .bankName("CENTRAL SECURITIES DEPOSITORY, THE")
                .countryName("MALTA")
                .isHeadquarter(true)
                .build());
        swiftCodeRepository.save(SwiftCodeEntity.builder()
                .swiftCode("CESDMTM1ABC")
                .countryIso2("MT")
                .address("27 PIETRO FLORIANI STREET  FLORIANA, IL-FURJANA, FRN 1060")
                .bankName("CENTRAL SECURITIES DEPOSITORY, THE")
                .countryName("MALTA")
                .build());
        swiftCodeRepository.save(SwiftCodeEntity.builder()
                .swiftCode("CESDMTM1DEF")
                .countryIso2("MT")
                .address("27 PIETRO FLORIANI STREET  FLORIANA, IL-FURJANA, FRN 1060")
                .bankName("CENTRAL SECURITIES DEPOSITORY, THE")
                .countryName("MALTA")
                .build());

        // when
        MvcResult mvcResult = mockMvc.perform(get("/v1/swift-codes/CESDMTM1XXX"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        SwiftCodeHeadquarterResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                SwiftCodeHeadquarterResponse.class
        );

        assertNotNull(response);
        assertEquals("CESDMTM1XXX", response.getSwiftCode());
        assertTrue(response.isHeadquarter());
        assertNotNull(response.getBranches());
        assertEquals(2, response.getBranches().size());
    }


    @Test
    void shouldDeleteExistingSwiftCodeSuccess() throws Exception {
        //given
        swiftCodeRepository.save(SwiftCodeEntity.builder()
                .swiftCode("DMAFPLP1XXX")
                .countryIso2("PL")
                .address("PANSKA CORNER PANSKA 73 WARSZAWA, MAZOWIECKIE, 00-834")
                .bankName("DOM MAKLERSKI AFS SP. Z O.O.")
                .countryName("POLAND")
                .build());

        //when
        mockMvc.perform(delete("/v1/swift-codes/DMAFPLP1XXX"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingSwiftCode() throws Exception {
        //when
        mockMvc.perform(delete("/v1/swift-codes/DMAFPLP1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenDeletingInvalidSwiftCode() throws Exception {
        // when
        mockMvc.perform(delete("/v1/swift-codes/DMAFPLP1safafagaedgsdgsdg"))
                .andExpect(status().isBadRequest());

    }

}
