package com.utn.UDEE.service;

import com.utn.UDEE.exception.DeleteException;
import com.utn.UDEE.exception.PrimaryKeyViolationException;
import com.utn.UDEE.exception.ResourceAlreadyExistException;
import com.utn.UDEE.exception.ResourceDoesNotExistException;
import com.utn.UDEE.models.Address;
import com.utn.UDEE.models.Meter;
import com.utn.UDEE.models.Rate;
import com.utn.UDEE.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class AddressService {

    AddressRepository addressRepository;
    MeterService meterService;
    RateService rateService;

    @Autowired
    public AddressService(AddressRepository addressRepository, MeterService meterService, RateService rateService) {
        this.addressRepository = addressRepository;
        this.meterService = meterService;
        this.rateService = rateService;
    }

    public Page<Address> getAllAddresses(Pageable pageable){
        return addressRepository.findAll(pageable);
    }

    public Address getAddressById(Integer id) throws ResourceDoesNotExistException {
        return addressRepository.findById(id).orElseThrow(()->new ResourceDoesNotExistException("Address doesn't exist"));
    }

    public Address addNewAddress(Address address) throws ResourceAlreadyExistException, ResourceDoesNotExistException {
        boolean addressExists = addressRepository.existsById(address.getId());
        if(addressExists == false){
            return addressRepository.save(address);
        }else{
            throw new ResourceAlreadyExistException("Address already exists");
        }
    }


    public Address updateAddress(Integer id, Address newAddress) throws PrimaryKeyViolationException, ResourceDoesNotExistException {
        Address actualAddress = getAddressById(id);
        if(!(actualAddress.getId().equals(newAddress.getId()))) {
            throw new PrimaryKeyViolationException("Primary key (id) cannot be changed");
        }
        return addressRepository.save(newAddress);
    }

    public Address addMeterToAddress(Integer idAddress, Integer idMeter) throws ResourceDoesNotExistException {
        Address address = getAddressById(idAddress);
        Meter meter = meterService.getMeterById(idMeter);
        if(address == null || meter == null){
            throw new ResourceDoesNotExistException("Address or Meter doesn't exist");
        }else{
        address.setMeter(meter);
        return addressRepository.save(address);
        }
    }

    public Address addRateToAddress(Integer id, Integer idRate) throws ResourceDoesNotExistException {
        Address address = getAddressById(id);
        Rate rate = rateService.getRateById(idRate);
        if(address == null || rate == null){
            throw new ResourceDoesNotExistException("Address or Rate doesn't exist");
        }else{
        address.setRate(rate);
        return addressRepository.save(address);
        }
    }

    public void deleteAddressById(Integer id) throws DeleteException, ResourceDoesNotExistException {
        Address address = getAddressById(id);
        if(address == null){
            throw new ResourceDoesNotExistException("Address doesn't exist");
        }
        if(isNull(address.getMeter())) {
            addressRepository.deleteById(id);
        } else {
            throw new DeleteException("It cannot be deleted because another object depends on it");
        }
    }
}
