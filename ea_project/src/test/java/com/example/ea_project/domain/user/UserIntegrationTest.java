package com.example.ea_project.domain.user;

import com.example.ea_project.domain.user.requests.UserRequest;
import com.example.ea_project.utils.enums.PhysicalActivity;
import com.example.ea_project.utils.enums.Sex;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class UserIntegrationTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    // CREATE USER
    @Test
    public void testCreateUser(){
        var userRequest = new UserRequest(170, PhysicalActivity.hard,"Niki", Sex.woman,30);
        String id = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("content.height",is(170))
                .body("content.id",notNullValue())
                .body("content.physicalActivity",is("hard"))
                .extract()
                .path("content.id");

    }

    @Test
    public void testCreateUser_badInputs(){
        var userRequest_notUniqueUsername = new UserRequest(170, PhysicalActivity.hard,"Anna",Sex.woman,30);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_notUniqueUsername)
                .when()
                .post("/users")
                .then()
                .statusCode(409);

        var userRequest_heightUnderZero = new UserRequest(-180, PhysicalActivity.hard,"Iva",Sex.woman,30);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_heightUnderZero)
                .when()
                .post("/users")
                .then()
                .statusCode(400);

        var userRequest_blankUsername = new UserRequest(180, PhysicalActivity.easy,"",Sex.woman,30);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_blankUsername)
                .when()
                .post("/users")
                .then()
                .statusCode(400);

        var userRequest_underAge = new UserRequest(180, PhysicalActivity.easy,"Iva",Sex.woman,10);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_underAge)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    // GET ALL USERS
    @Test
    public void testGetAllUsers(){
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("count",is(2));

    }


    // GET AVERAGE BMI FOR MONTH
    @Test
    void testAverageBMI(){
        // from base data

        given()
                .when()
                .get("users/averageBMI/a766e9fa-4cc7-479a-abb8-f712c035327b/2024/1")
                .then()
                .statusCode(200)
                .body("content.averageWeight",is(56.0F))
                .body("content.descriptionBMI",is("normal"))
                .body("content.averageBMI",is(20.08F));
    }

    @Test
    void testAverageBMI_wrongInputs(){
        // future year
        given()
                .when()
                .get("users/averageBMI/a766e9fa-4cc7-479a-abb8-f712c035327b/2050/1")
                .then()
                .statusCode(409);
        // wrong month
        given()
                .when()
                .get("users/averageBMI/a766e9fa-4cc7-479a-abb8-f712c035327b/2023/111")
                .then()
                .statusCode(409);
        // wrong uuid
        given()
                .when()
                .get("users/averageBMI/a766e9fa-1111-1111-abb8-f712c035327b/2023/1")
                .then()
                .statusCode(404);
    }


    // GET USER METABOLISM INFO
    @Test
    void testUserMetabolismInfo(){
        given()
                .when()
                .get("users/metabolismInfo?userId=a766e9fa-4cc7-479a-abb8-f712c035327b&currentWeight=60")
                .then()
                .statusCode(200)
                .body("content.username",is("Misa"))
                .body("content.currentWeight",is(60))
                .body("content.basalMetabolism",is(1358.0F))
                .body("content.recommendedCalorieIntake",is(1616.02F))
                .body("content.recommendedFats",is(44.89F))
                .body("content.recommendedProteins",is(101.0F))
                .body("content.recommendedCarbohydrates",is(202.0F));
    }

    @Test
    void testUserMetabolismInfo_wrongInputs(){
        // bad ID
        given()
                .when()
                .get("users/metabolismInfo?userId=a766e9fa-aaaa-aaaa-aaaa-f712c035327b&currentWeight=60")
                .then()
                .statusCode(404);
        // bad weight
        given()
                .when()
                .get("users/metabolismInfo?userId=a766e9fa-4cc7-479a-abb8-f712c035327b&currentWeight=0")
                .then()
                .statusCode(409);
    }

    // UPDATE USER
    @Test
    public void testUpdateUser(){
        var userRequest = new UserRequest(170, PhysicalActivity.hard,"Niki", Sex.woman,30);
        String id = given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .put("/users/a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(202)
                .body("content.height",is(170))
                .body("content.id",notNullValue())
                .body("content.physicalActivity",is("hard"))
                .extract()
                .path("content.id");

    }

    @Test
    public void testUpdateUser_wrongInputs(){
        //wrong id
        var userRequest = new UserRequest(170, PhysicalActivity.hard,"Niki", Sex.woman,30);
       given()
                .contentType(ContentType.JSON)
                .body(userRequest)
                .when()
                .put("/users/a766e9fa-aaaa-aaaa-aaaa-f712c035327b")
                .then()
                .statusCode(404);

        //wrong height
        var userRequest_wrongHeight = new UserRequest(-10, PhysicalActivity.hard,"Niki", Sex.woman,30);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_wrongHeight)
                .when()
                .put("/users/a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(400);

        //empty name
        var userRequest_emptyName = new UserRequest(180, PhysicalActivity.hard,"", Sex.woman,30);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_emptyName)
                .when()
                .put("/users/a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(400);

        //wrong age
        var userRequest_wrongAge = new UserRequest(180, PhysicalActivity.hard,"aaa", Sex.woman,-20);
        given()
                .contentType(ContentType.JSON)
                .body(userRequest_wrongAge)
                .when()
                .put("/users/a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(400);
    }

    @Test
    void testDeleteUser(){
        // test that user has measurements and foods
        given()
                .when()
                .get("/food/dayInfo?date=2024-01-01&userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(200)
                .body("content.username",is("Misa"))
                .body("content.caloriesInTotal",is(720.0F))
                .body("content.date",is("2024-01-01"));

        // delete
        given()
                .when()
                .delete("/users/a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(204);

        // check that lists (foods, measurements) are deleted also
        given()
                .when()
                .get("/food/dayInfo?date=2024-01-01&userId=a766e9fa-4cc7-479a-abb8-f712c035327b")
                .then()
                .statusCode(404);
    }

    @Test
    void testDeleteUser_wrongInputs() {
        // bad id
        given()
                .when()
                .delete("/users/a76aaaafa-aaaa-aaaa-abb8-f712c035327b")
                .then()
                .statusCode(400);
    }


}
