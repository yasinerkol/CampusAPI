package Campus;

import Campus.Model.Country;
import Campus.Model.GradeLevels;
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

public class CA_10GradeLevels {
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

    String gradeLevelID;
    String gradeLevelName;
    String gradeLevelShotName;
    String gradeLevelOrder;

    @Test
    public void createGradeLevel()
    {
        gradeLevelName=getRandomName();
        gradeLevelOrder=getRandomOrder();
        gradeLevelShotName=getRandomShotName();

        GradeLevels gradeLevels=new GradeLevels();
        gradeLevels.setName(gradeLevelName);
        gradeLevels.setShotName(gradeLevelShotName);
        gradeLevels.setOrder(gradeLevelOrder);

        gradeLevelID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "  \"id\": null,\n" +
                                "  \"name\": \"bbccbbb\",\n" +
                                "  \"shortName\": \"bcb\",\n" +
                                "  \"nextGradeLevel\": null,\n" +
                                "  \"order\": \"1\",\n" +
                                "  \"translateName\": [],\n" +
                                "  \"translateShortName\": [],\n" +
                                "  \"active\": true\n" +
                                "}")

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }



    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public String getRandomShotName() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    public String getRandomOrder() {
        return RandomStringUtils.randomAlphanumeric(2).toLowerCase();
    }


    @Test(dependsOnMethods = "createGradeLevel")
    public void createGradeLevelNegative()
    {
        //"message": "The Country with Name \"France 375\" already exists.",

        GradeLevels gradeLevels=new GradeLevels();
        gradeLevels.setName(gradeLevelName);
        gradeLevels.setShotName(gradeLevelShotName);
        gradeLevels.setOrder(gradeLevelOrder);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gradeLevels)

                .when()
                .post("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("The Grade Level with Name \""+gradeLevelName+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods = "createGradeLevel")
    public void updateGradeLevel()
    {

        gradeLevelName = getRandomName();
        gradeLevelShotName=getRandomShotName();
        gradeLevelOrder=getRandomOrder();


        GradeLevels gradeLevels=new GradeLevels();
        gradeLevels.setId(gradeLevelID);
        gradeLevels.setName(gradeLevelName);
        gradeLevels.setShotName(gradeLevelShotName);
        gradeLevels.setOrder(gradeLevelOrder);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gradeLevels)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(gradeLevelName))
        ;
    }

    @Test(dependsOnMethods = "updateGradeLevel")
    public void deleteGradeLevelById()
    {
        given()
                .cookies(cookies)
                .pathParam("gradeLevelID", gradeLevelID)

                .when()
                .delete("school-service/api/grade-levels/{gradeLevelID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteGradeLevelById")
    public void deleteGradeLevelByIdNegative()
    {
        given()
                .cookies(cookies)
                .pathParam("gradeLevelID", gradeLevelID)
                .log().uri()
                .when()
                .delete("school-service/api/grade-levels/{gradeLevelID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteGradeLevelById")
    public void updateGradeLevelNegative()
    {
        gradeLevelName = getRandomName();
        gradeLevelShotName=getRandomShotName();
        gradeLevelOrder=getRandomOrder();


        GradeLevels gradeLevels=new GradeLevels();
        gradeLevels.setId(gradeLevelID);
        gradeLevels.setName(gradeLevelName);
        gradeLevels.setShotName(gradeLevelShotName);
        gradeLevels.setOrder(gradeLevelOrder);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gradeLevels)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Grade Level  not found"))
        ;
    }




}











