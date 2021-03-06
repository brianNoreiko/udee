package com.utn.UDEE.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utn.UDEE.models.Meter;
import com.utn.UDEE.models.dto.MeterDto;
import com.utn.UDEE.utils.localdate.LocalDateDeserializer;
import com.utn.UDEE.utils.localdate.LocalDateSerializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.utn.UDEE.utils.ModelUtilsTest.aModel;

public class MeterUtilsTest {

        public static String aMeterJSON() {
            final Gson gson =  new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .setPrettyPrinting().create();
            System.out.println("");
            return gson.toJson(aMeter());

        }

        public static Meter aMeter() {
            Meter meter = new Meter();
            meter.setSerialNumber(1);
            meter.setPassword("123456");
            meter.setModel(aModel().builder().build());
            return meter;
        }

        public static Meter aNullMeter(){
            Meter nullMeter = null;
            return nullMeter;
        }

        public static Page<Meter> aMeterPage() {
            return new PageImpl<>(List.of(aMeter()));
        }

        public static Page<Meter> aMeterEmptyPage() {
            List<Meter> meterList = Collections.emptyList();
            return new PageImpl<>(meterList);
        }

        public static MeterDto aMeterDto() {
            return new MeterDto();
        }
}
