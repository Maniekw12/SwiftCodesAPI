package com.marianw12.remitly_internship.service;

import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.exception.DuplicatedSwiftCodeException;
import com.marianw12.remitly_internship.exception.NonExistingSwiftCodeException;
import com.marianw12.remitly_internship.mapper.SwiftCodeMapper;
import com.marianw12.remitly_internship.repository.SwiftCodeRepository;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeServiceTest {

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @Mock
    private SwiftCodeMapper swiftCodeMapper;


    @Test
    public void shouldReturnCorrectSwiftCodeBranchResponseWhenSwiftCodeExists() {
        //given
        String swiftCode = "CITIBGSFTRD";
        SwiftCodeEntity entity = SwiftCodeEntity
                .builder()
                .swiftCode(swiftCode)
                .isHeadquarter(false)
                .build();

        SwiftCodeBranchResponse expectedResponse = SwiftCodeBranchResponse
                .builder()
                .swiftCode(swiftCode)
                .build();

        when(swiftCodeRepository.findBySwiftCode(swiftCode.toUpperCase()))
                .thenReturn(Optional.of(entity));
        when(swiftCodeMapper.mapToResponse(entity)).thenReturn(expectedResponse);

        //when
        SwiftCodeBranchResponse response = swiftCodeService.getSwiftCode(swiftCode);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(swiftCode, response.getSwiftCode());
        verify(swiftCodeRepository).findBySwiftCode(swiftCode.toUpperCase());
        verify(swiftCodeMapper).mapToResponse(entity);
    }

    @Test
    public void shouldThrowNonExistingSwiftCodeExceptionWhenSwiftCodeNotFound() {
        //given
        String swiftCode = "TPEOPLPWOBP";
        when(swiftCodeRepository.findBySwiftCode(swiftCode.toUpperCase()))
                .thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(NonExistingSwiftCodeException.class, () -> swiftCodeService.getSwiftCode(swiftCode));
    }

    @Test
    public void shouldSaveSwiftCodeSuccessfullyWhenRequestIsValid() {
        //given
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest
                .builder()
                .swiftCode("PSATPLPWXXX")
                .countryISO2("PL")
                .countryName("Poland")
                .build();

        SwiftCodeEntity entity = SwiftCodeEntity
                .builder()
                .swiftCode("PSATPLPWXXX")
                .build();

        when(swiftCodeRepository.existsById(request.getSwiftCode().toUpperCase()))
                .thenReturn(false);
        when(swiftCodeMapper.mapToEntity(request)).thenReturn(entity);

        //when
        swiftCodeService.createSwiftCode(request);

        //then
        verify(swiftCodeRepository).save(entity);
    }

    @Test
    public void shouldThrowDuplicatedSwiftCodeExceptionWhenSwiftCodeAlreadyExists() {
        //given
        CreateSwiftCodeRequest request = CreateSwiftCodeRequest
                .builder()
                .swiftCode("TESTPLPWXXX")
                .countryISO2("PL")
                .countryName("Poland")
                .build();

        when(swiftCodeRepository.existsById(request.getSwiftCode().toUpperCase()))
                .thenReturn(true);

        //when
        //then
        Assertions.assertThrows(DuplicatedSwiftCodeException.class, () -> swiftCodeService.createSwiftCode(request));
    }

    @Test
    public void shouldDeleteSwiftCodeSuccessfully() {
        //given
        String swiftCode = "AMNMBGS1XXX";
        when(swiftCodeRepository.existsById(swiftCode.toUpperCase()))
                .thenReturn(true);

        //when
        swiftCodeService.deleteSwiftCode(swiftCode);

        //then
        verify(swiftCodeRepository).deleteById(swiftCode.toUpperCase());
    }

    @Test
    public void shouldThrowNonExistingSwiftCodeExceptionWhenAttemptToDeleteNonExistingSwiftCode() {
        //given
        String swiftCode = "KGITMTMTXXX";
        when(swiftCodeRepository.existsById(swiftCode.toUpperCase()))
                .thenReturn(false);

        //when
        //then
        Assertions.assertThrows(NonExistingSwiftCodeException.class, () -> swiftCodeService.deleteSwiftCode(swiftCode));
    }
}
