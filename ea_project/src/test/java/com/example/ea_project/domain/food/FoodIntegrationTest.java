package com.example.ea_project.domain.food;

import com.example.ea_project.domain.food.requests.FoodRequest;
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
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class FoodIntegrationTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    // CREATE FOOD RECORD
    @Test
    public void testCreateFood(){
        // user uuid, product id from data set
        var foodRequest =new FoodRequest(100.0, LocalDate.of(2024,1,1),1L, UUID.fromString("a766e9fa-4cc7-479a-abb8-f712c035327b"));

        given()
                .contentType(ContentType.JSON)
                .body(foodRequest)
                .when()
                .post("/food")
                .then()
                .statusCode(201)
                .body("content.name",is("pecans"))
                .body("content.quantity",is(100.0F))
                .body("content.calories",is(200.0F))
                .body("content.fats",is(0.21F))
                .body("content.proteins",is(0.30F))
                .body("content.carbohydrates",is(0.42F))
                .body("content.date",is("2024-01-01"));

    }
    @Test
    public void testCreateFood_wrongInputs(){
        var foodRequest_wrongUUID =new FoodRequest(100.0, LocalDate.of(2024,1,1),1L, UUID.randomUUID());
        given()
                .contentType(ContentType.JSON)
                .body(foodRequest_wrongUUID)
                .when()
                .post("/food")
                .then()
                .statusCode(404);

        var foodRequest_wrongProductId =new FoodRequest(100.0, LocalDate.of(2024,1,1),150L, UUID.fromString("a766e9fa-4cc7-479a-abb8-f712c035327b"));
        given()
                .contentType(ContentType.JSON)
                .body(foodRequest_wrongProductId)
                .when()
                .post("/food")
                .then()
                .statusCode(404);

        var foodRequest_wrongQuantity =new FoodRequest(-100.0, LocalDate.of(2024,1,1),1L, UUID.fromString("a766e9fa-4cc7-479a-abb8-f712c035327b"));
        given()
                .contentType(ContentType.JSON)
                .body(foodRequest_wrongQuantity)
                .when()
                .post("/food")
                .then()
                .statusCode(400);
    }


    // GET DAY INFO
    @Test
    void testGetDayInfo(){
        // user id, foods from data set
        given()
                .when()
                .get("/food/dayInfo?date=2024-01-01&userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(200)
                .body("content.username",is("Misa"))
                .body("content.caloriesInTotal",is(720.0F))
                .body("content.fatsInTotal",is(0.51F))
                .body("content.proteinsInTotal",is(0.75F))
                .body("content.carbohydratesInTotal",is(0.67F))
                .body("content.date",is("2024-01-01"));
    }

    @Test
    void testGetDayInfo_badInputs(){
        // bad user id
                 when()
                .get("/food/dayInfo?date=2024-01-01&userId=11111111-1111-1111-1111-11111111111")
                .then()
                .statusCode(404);

        // bad date format
                when()
                .get("/food/dayInfo?date=2-01-01&userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(400);

        // missing date
                when()
                .get("/food/dayInfo?userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(400);

        // missing user id
                when()
                .get("/food/dayInfo?date=2024-01-01")
                .then()
                .statusCode(400);

        // future date
                when()
                .get("/food/dayInfo?date=2060-01-01&userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(409);
    }



}
