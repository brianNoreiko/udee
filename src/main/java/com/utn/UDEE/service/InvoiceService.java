package com.utn.UDEE.service;

import com.utn.UDEE.models.Address;
import com.utn.UDEE.models.Invoice;
import com.utn.UDEE.models.User;
import com.utn.UDEE.models.responses.Response;
import com.utn.UDEE.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InvoiceService {

    InvoiceRepository invoiceRepository;
    UserService userService;
    AddressService addressService;
    MeasurementService measurementService;
    MeterService meterService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, UserService userService, AddressService addressService, MeasurementService measurementService, MeterService meterService) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
        this.addressService = addressService;
        this.measurementService = measurementService;
        this.meterService = meterService;
    }

    public Page<Invoice> getInvoiceBetweenDateByUser(Integer idUser, LocalDate since, LocalDate until, Pageable pageable) {
        User user = userService.getUserById(idUser);
        return invoiceRepository.findAllByUserAndDateBetween(user, since, until, pageable);
    }

    public Page<Invoice> getUnpaidByUser(Integer idUser, Pageable pageable) {
        User user = userService.getUserById(idUser);
        return invoiceRepository.findAllByUserAndPayed(user, false, pageable);
    }

    public Invoice addNewInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoiceById(Integer idInvoice) {
        invoiceRepository.deleteById(idInvoice);
    }

    public Page<Invoice> getAllUnpaidByAddress(Integer idAddress, Pageable pageable) {
        Address address = addressService.getAddressById(idAddress);
        return invoiceRepository.findAllByAddressAndPayed(address,false,pageable);
    }
}
