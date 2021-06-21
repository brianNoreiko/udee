package com.utn.UDEE.controller.androidAppController;

import com.utn.UDEE.exception.SinceUntilException;
import com.utn.UDEE.exception.doesNotExist.UserDoesNotExist;
import com.utn.UDEE.models.Invoice;
import com.utn.UDEE.models.dto.InvoiceDto;
import com.utn.UDEE.service.InvoiceService;
import com.utn.UDEE.utils.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.utn.UDEE.utils.Utils.checkSinceUntil;

@RestController
@RequestMapping("/app/invoices")
public class InvoiceAppController {

    InvoiceService invoiceService;
    ConversionService conversionService;

    @Autowired
    public InvoiceAppController(InvoiceService invoiceService, ConversionService conversionService) {
        this.invoiceService = invoiceService;
        this.conversionService = conversionService;
    }

    //Consulta de facturas por rango de fechas
    @GetMapping("/users/{idClient}")
    public ResponseEntity<List<InvoiceDto>> getInvoiceBetweenDate(@PathVariable Integer idUser,
                                                                  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                  @RequestParam(value = "since", defaultValue = "2021-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate since,
                                                                  @RequestParam(value = "until", defaultValue = "#{T(java.time.LocalDateTime).now()}") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate until)
            throws UserDoesNotExist, SinceUntilException {
        checkSinceUntil(since, until);
        Pageable pageable = PageRequest.of(page, size);
        Page<Invoice> invoicePage = invoiceService.getInvoiceBetweenDateByUser(idUser, since, until, pageable);
        Page<InvoiceDto> invoiceDtoPage = invoicePage.map(invoice -> conversionService.convert(invoice, InvoiceDto.class));
        return EntityResponse.listResponse(invoiceDtoPage);
    }

    //Consulta de deuda (Facturas impagas)
    @GetMapping("/users/{idClient}/unpaid")
    public ResponseEntity<List<InvoiceDto>> getUnpaidByUser(@PathVariable Integer idUser,
                                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Invoice> invoicePage = invoiceService.getUnpaidByUser(idUser, pageable);
        Page<InvoiceDto> invoiceDtoPage = invoicePage.map(invoice -> conversionService.convert(invoice,InvoiceDto.class));
        return EntityResponse.listResponse(invoiceDtoPage);
    }
}