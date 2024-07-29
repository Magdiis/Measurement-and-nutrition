package com.example.ea_project.utils.data;

import com.example.ea_project.domain.measurement.Measurement;
import com.example.ea_project.domain.measurement.MeasurementService;
import com.example.ea_project.domain.product.Product;
import com.example.ea_project.domain.product.ProductService;
import com.example.ea_project.domain.user.model.User;
import com.example.ea_project.domain.user.UserService;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

//@Service
//@Slf4j
//public class Seeder {
//    private final UserService userService;
//    private final MeasurementService measurementService;
//
//    private final ProductService productService;
//
//    public Seeder(UserService userService, MeasurementService measurementService, ProductService productService){
//        this.measurementService = measurementService;
//        this.userService = userService;
//        this.productService = productService;
//    }
//
//    private boolean shouldSeedData(){
//        return userService.getAllUsers().isEmpty();
//    }
//
//    @PostConstruct
//    public void  seedDefaultData(){
//        if (!shouldSeedData()) {
//            log.info("--- Default data already seeded ---");
//            return;
//        }
//        User user = new User(167,"Novakova", PhysicalActivity.normal,30, Sex.woman);
//        userService.createUser(user);
//
//        Measurement measurement1 = new Measurement(1L, LocalDate.of(2024,1,1),56.0,user);
//        Measurement measurement2 = new Measurement(2L, LocalDate.of(2024,1,2),56.0,user);
//
//        measurementService.createMeasurement(measurement1);
//        measurementService.createMeasurement(measurement2);
//
//        Product product1 = new Product("pecans", 200, 0.21,0.30,0.42);
//        Product product2 = new Product("eggs", 520, 0.3,0.45,0.25);
//        Product product3 = new Product("bread", 320, 0.48,0.27,0.65);
//        Product product4 = new Product("carrot", 90, 0.09,0.1,0.08);
//        Product product5 = new Product("cheese", 480, 0.78,0.66,0.44);
//        Product product6 = new Product("salami", 370, 0.66,0.5,0.39);
//
//        productService.createProduct(product1);
//        productService.createProduct(product2);
//        productService.createProduct(product3);
//        productService.createProduct(product4);
//        productService.createProduct(product5);
//        productService.createProduct(product6);
//        log.info("--- Default data seeded ---");
//        }
//}
