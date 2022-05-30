package com.init.GirlsInTeck;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.init.GirlsInTeck.dto.PointDto;
import com.init.GirlsInTeck.entity.Point;
import com.init.GirlsInTeck.service.impl.ServiceAddress;
import com.init.GirlsInTeck.service.impl.ServiceLocation;
import com.init.GirlsInTeck.service.impl.ServicePoint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class GirlsInTeckApplication {

    public static void main(String[] args) {
        SpringApplication.run(GirlsInTeckApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ServicePoint servicePoint) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<PointDto>> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/Ponits_collection.json");
            try {
                List<PointDto> points = mapper.readValue(inputStream, typeReference);

                for (PointDto u : points) {
                    try {
                        servicePoint.createPoint(u);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                System.out.println("Points Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save points: " + e.getMessage());
            }
        };
    }
}

