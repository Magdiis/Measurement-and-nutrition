package com.example.ea_project.domain.measurement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class MeasurementIntegrationTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    // CREATE MEASUREMENT
    @Test
    public void testCreateMeasurement(){
        var measurementRequest = new MeasurementRequest(LocalDate.now(),70,UUID.fromString("a986e9fa-4cc7-479a-aee8-f700c035327b"));
        given()
                .contentType(ContentType.JSON)
                .body(measurementRequest)
                .when()
                .post("/measurements")
                .then()
                .statusCode(201);
    }

    @Test
    public void testCreateMeasurement_badInputs(){
        var measurementRequest_wrongUserId = new MeasurementRequest(LocalDate.now(),70,UUID.randomUUID());
        given()
                .contentType(ContentType.JSON)
                .body(measurementRequest_wrongUserId)
                .when()
                .post("/measurements")
                .then()
                .statusCode(409);

        var measurementRequest_weightUnderZero = new MeasurementRequest(LocalDate.now(),-70,UUID.fromString("a986e9fa-4cc7-479a-aee8-f700c035327b"));
        given()
                .contentType(ContentType.JSON)
                .body(measurementRequest_weightUnderZero)
                .when()
                .post("/measurements")
                .then()
                .statusCode(400);
    }


    @Test
    public void deleteMeasurement(){
        given()
                .when()
                .delete("http://localhost:8090/measurements/1")
                .then()
                .statusCode(204);
    }
}
