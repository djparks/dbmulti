package net.parksy.dbmulti.service;

import lombok.RequiredArgsConstructor;
import net.parksy.dbmulti.entity.Address;
import net.parksy.dbmulti.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address createAddress(String street, String city, String zipCode) {
        Address address = Address.builder()
                .street(street)
                .city(city)
                .zipCode(zipCode)
                .build();
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByCity(String city) {
        return addressRepository.findByCity(city);
    }
}
