package com.marianw12.remitly_internship.parser;


import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.util.CountryValidator;
import com.marianw12.remitly_internship.util.SwiftCodeValidator;
import lombok.RequiredArgsConstructor;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import com.marianw12.remitly_internship.repository.SwiftCodeRepository;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataParser {
    private final Logger LOGGER = LoggerFactory.getLogger(DataParser.class);
    private final SwiftCodeRepository swiftCodeRepository;
    private static final String SWIFT_CODE_DATA_FILE = "swift_codes.csv";
    private static final int COUNTRY_ISO_CODE_COLUMN = 0;
    private static final int SWIFT_CODE_COLUMN = 1;
    private static final int BANK_NAME_COLUMN = 3;
    private static final int ADDRESS_COLUMN = 4;
    private static final int HOME_TOWN_COLUMN = 5;
    private static final int COUNTRY_NAME_COLUMN = 6;

    @EventListener
    public void initializeRepository(final ContextRefreshedEvent event) throws IOException {
        List<SwiftCodeEntity> swiftCodes = new ArrayList<>();
        ClassPathResource csvFileResource = new ClassPathResource(SWIFT_CODE_DATA_FILE);

        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (
                Reader reader = new InputStreamReader(csvFileResource.getInputStream());
                CSVParser parser = new CSVParser(reader, csvFormat)
        ) {

            LOGGER.info(String.format("Initiating database with records from file: %s", csvFileResource.getFilename()));

            for (CSVRecord record : parser.getRecords()) {
                String swiftCode = record.get(SWIFT_CODE_COLUMN);
                String countryIso2 = record.get(COUNTRY_ISO_CODE_COLUMN);
                String countryName = record.get(COUNTRY_NAME_COLUMN);
                String bankName = record.get(BANK_NAME_COLUMN);
                String address = record.get(ADDRESS_COLUMN);
                String homeTown = record.get(HOME_TOWN_COLUMN);

                if (!SwiftCodeValidator.isValid(swiftCode)) {
                    LOGGER.info(String.format("Ignoring record with invalid swift code: %s", swiftCode));
                    continue;
                }
                if (!CountryValidator.isValidIso2Code(countryIso2)) {
                    LOGGER.info(String.format("Ignoring record with invalid country ISO2 code: %s", countryIso2));
                    continue;
                }
                if (!CountryValidator.isValidCountryName(countryName)) {
                    LOGGER.info(String.format("Ignoring record with invalid country name: %s", countryName));
                    continue;
                }
                if (StringUtils.isEmpty(bankName)) {
                    LOGGER.info(String.format("Ignoring record with empty bank name: %s", bankName));
                    continue;
                }

                if (StringUtils.isEmpty(address)) {
                    if (StringUtils.isNotEmpty(homeTown)) {
                        address = homeTown;
                    } else {
                        LOGGER.info(String.format("Ignoring record with missing address for swift code: %s", swiftCode));
                        continue;
                    }
                }

                SwiftCodeEntity swiftCodeEntity = SwiftCodeEntity.builder()
                        .swiftCode(swiftCode.toUpperCase())
                        .countryIso2(countryIso2.toUpperCase())
                        .bankName(bankName.toUpperCase())
                        .address(address.toUpperCase())
                        .countryName(countryName.toUpperCase())
                        .isHeadquarter(SwiftCodeValidator.isHeadquarter(swiftCode))
                        .build();

                swiftCodes.add(swiftCodeEntity);
            }
            swiftCodeRepository.saveAll(swiftCodes);
            LOGGER.info(String.format("Successfully persisted %s records", swiftCodes.size()));
        } catch (Exception e) {
            LOGGER.error("Exception while initializing database", e);
        }


    }


}
