package be.switchfully.uno_shark.controllers;import be.switchfully.uno_shark.domain.parking.Division;import be.switchfully.uno_shark.domain.parking.ParkingCategory;import be.switchfully.uno_shark.domain.parking.Price;import be.switchfully.uno_shark.domain.parking.divisionDto.ShowDivisionDto;import be.switchfully.uno_shark.domain.parking.divisionDto.SingleDivisionDto;import be.switchfully.uno_shark.domain.parking.parkingLotDto.ParkingLotDto;import be.switchfully.uno_shark.domain.person.Person;import be.switchfully.uno_shark.domain.person.address.Address;import be.switchfully.uno_shark.domain.person.address.PostalCode;import be.switchfully.uno_shark.repositories.ParkingLotRepository;import be.switchfully.uno_shark.services.ParkingLotService;import io.restassured.RestAssured;import io.restassured.http.ContentType;import org.junit.jupiter.api.Test;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;import org.springframework.boot.test.context.SpringBootTest;import org.springframework.boot.test.web.server.LocalServerPort;import org.springframework.http.HttpStatus;import java.util.ArrayList;import java.util.Arrays;import java.util.Currency;import java.util.List;import static org.assertj.core.api.Assertions.assertThat;import static org.junit.jupiter.api.Assertions.assertEquals;@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)@AutoConfigureTestDatabaseclass ParkingLotControllerIntegrationTest {    @Autowired    ParkingLotRepository parkingLotRepository;    @Autowired    ParkingLotService parkingLotService;    @LocalServerPort    private int port;    @Test    void createParkinglot_HappyPath() {//todo fix Json to represent CreateDivisionDto        //ARRANGE        String requestBody = """                         {                             "name": "rue Jaune",                             "parkingCategory": "UNDERGROUND",                             "division": {                                 "parentName": "No Parent Division",                                 "name": "Pathé",                                 "originalName": "BNP",                                 "director": "Freddy"                             },                             "capacity": 600,                             "address": {                                 "streetName": "Rue haute",                                 "houseNumber": "78",                                 "postalCode": {                                     "postalCode": "5000",                                     "city": "Luik"                                 },                                 "country": "Belgium"                             },                             "pricePerHour": {                                 "amount": 6.0,                                 "currency": "EUR"                             },                             "person": {                                 "firstName": "Harold",                                 "lastName": "van Doorslaer",                                 "address": {                                     "streetName": "Rue courte",                                     "houseNumber": "22",                                     "postalCode": {                                         "postalCode": "4500",                                         "city": "Namur"                                     },                                     "country": "Belgium"                                 },                                 "phoneNumber": "022500005",                                 "mobileNumber": "04700005",                                 "emailAddress": "haroldvandoo@switchfully.com"                             }                         }                }""";/*        ParkingLotDto expected = new ParkingLotDto(1L, "Goeminne", ParkingCategory.UNDERGROUND,                new SingleDivisionDto(1, "No Parent Division", "UGC", "KBC", "Freddy", List.of("sub1","sub2","sub3")),400,                new Person("Freddy", "Broeckx",                        new Address(2L, "Nieuwestraat", "44", new PostalCode("1000", "Brussel"), "Belgium"),                        "032261018", "0473876578", "testing3@email.com"),                new Address(1L, "Langestraat", "22", new PostalCode("3000", "Leuven"), "Belgium"),                new Price(8.0, Currency.getInstance("EUR"))); */        //ACT        ParkingLotDto result =                RestAssured                        .given()                        .contentType(ContentType.JSON)                        .baseUri("http://localhost")                        .port(port)                        .body(requestBody)                        .when()                        .accept(ContentType.JSON)                        .post("/parkinglots")                        .then()                        .assertThat()                        .statusCode(HttpStatus.CREATED.value())                        .extract()                        .as(ParkingLotDto.class);        //ASSESS        assertThat(result).isNotNull();        // assertThat(result).isEqualTo(expected);        //assertEquals(result, expected);    }    @Test    void getAllParkingLots_HappyPath() {        ParkingLotDto[] result = RestAssured                .given()                .contentType(ContentType.JSON)                .baseUri("http://localhost")                .port(port)                .when()                .accept(ContentType.JSON)                .get("/parkinglots")                .then()                .assertThat()                .statusCode(HttpStatus.OK.value())                .extract()                .as(ParkingLotDto[].class);        assertThat(result.length).isEqualTo(parkingLotRepository.count());    }}