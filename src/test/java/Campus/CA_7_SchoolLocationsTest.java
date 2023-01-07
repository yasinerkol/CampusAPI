package Campus;

import Campus.Model.SchoolLocation;
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

public class CA_7_SchoolLocationsTest {
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
    String schoolLocationID;
    String locationName;
    String locationShortName;
    int schoolCapacity;


    @Test
    public void createSchoolLocation()
    {
        locationName =getRandomName();
        locationShortName =getRandomShortName();
        schoolCapacity=getRandomCapacity();

        SchoolLocation schoolLocation=new SchoolLocation();
        schoolLocation.setName(locationName);
        schoolLocation.setShortName(locationShortName);
        schoolLocation.setCapacity(schoolCapacity);
        schoolLocation.setActive(true);
        schoolLocation.setType("CLASS");
        schoolLocation.setSchool("5fe07e4fb064ca29931236a5");

        schoolLocationID=
           given()
                   .cookies(cookies)
                   .contentType(ContentType.JSON)
                   .body("{\n" +
                           "  \"id\": null,\n" +
                           "  \"name\": \"west\",\n" +
                           "  \"shortName\": \"east\",\n" +
                           "  \"active\": true,\n" +
                           "  \"capacity\": \"100\",\n" +
                           "  \"type\": \"CLASS\",\n" +
                           "  \"school\": {\n" +
                           "    \"id\": \"6343bf893ed01f0dc03a509a\"\n" +
                           "  }\n" +
                           "}")

                   .when()
                   .post("school-service/api/location")

                   .then()
                   .log().body()
                   .statusCode(201)
                   .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createSchoolLocation")
    public void createSchoolLocationNegative()
    {
        locationName =getRandomName();
        locationShortName =getRandomShortName();
        schoolCapacity=getRandomCapacity();

        SchoolLocation schoolLocation=new SchoolLocation();
        schoolLocation.setName(locationName);
        schoolLocation.setShortName(locationShortName);
        schoolLocation.setCapacity(schoolCapacity);
        schoolLocation.setActive(true);
        schoolLocation.setType("CLASS");
        schoolLocation.setSchool("5fe07e4fb064ca29931236a5");

        schoolLocationID=
                String.valueOf(given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "  \"id\": null,\n" +
                                "  \"name\": \"west\",\n" +
                                "  \"shortName\": \"east\",\n" +
                                "  \"active\": true,\n" +
                                "  \"capacity\": \"100\",\n" +
                                "  \"type\": \"CLASS\",\n" +
                                "  \"school\": {\n" +
                                "    \"id\": \"6343bf893ed01f0dc03a509a\"\n" +
                                "  }\n" +
                                "}")

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", equalTo("The School Location with Name \""+locationName+"\" already exists.")))
        ;
    }



    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public String getRandomShortName() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }

    public int getRandomCapacity() {return (int)(Math.random()*((500-100))+100);}







}











