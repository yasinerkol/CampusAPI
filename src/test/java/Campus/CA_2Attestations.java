package Campus;

import Campus.Model.Attestation;
import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CA_2Attestations {
    Cookies cookies;

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        //.log().cookies()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String attestationsID;
    String attestationsName;

    @Test
    public void createAttestations()
    {
        attestationsName=getRandomName();


        Attestation attestation=new Attestation();
        attestation.setName(attestationsName);


        attestationsID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestation)

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
        System.out.println("attestationsID = " + attestationsID);
    }



    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }




    @Test(dependsOnMethods = "createAttestations")
    public void createAttestationsNegative()
    {

        Country country=new Country();
        country.setName(attestationsName);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("The Attestation with Name \""+attestationsName+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods = "createAttestations")
    public void updateAttestations()
    {
        attestationsName = getRandomName();

        Country country=new Country();
        country.setId(attestationsID);
        country.setName(attestationsName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(attestationsName))
        ;
    }

    @Test(dependsOnMethods = "updateAttestations")
    public void deleteAttestationsById()
    {
        given()
                .cookies(cookies)
                .pathParam("attestationsID", attestationsID)

                .when()
                .delete("school-service/api/attestation/{attestationsID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteAttestationsById")
    public void deleteAttestationsByIdNegative()
    {
        given()
                .cookies(cookies)
                .pathParam("attestationsID", attestationsID)
                .log().uri()
                .when()
                .delete("school-service/api/attestation/{attestationsID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteAttestationsById")
    public void updateAttestationsNegative()
    {
        attestationsName = getRandomName();

        Country country=new Country();
        country.setId(attestationsID);
        country.setName(attestationsName);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .put("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Attestation not found"))
        ;
    }




}
