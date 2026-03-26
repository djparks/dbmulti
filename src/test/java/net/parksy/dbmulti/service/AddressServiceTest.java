package net.parksy.dbmulti.service;

import net.parksy.dbmulti.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    void testCreateAndGetAddress() {
        // Given
        String street = "123 Main St";
        String city = "Springfield";
        String zipCode = "62704";

        // When
        Address savedAddress = addressService.createAddress(street, city, zipCode);

        // Then
        assertThat(savedAddress.getId()).isNotNull();
        assertThat(savedAddress.getStreet()).isEqualTo(street);

        // And When
        List<Address> foundAddresses = addressService.getAddressesByCity(city);

        // Then
        assertThat(foundAddresses).isNotEmpty();
        assertThat(foundAddresses.get(0).getZipCode()).isEqualTo(zipCode);
    }
}
