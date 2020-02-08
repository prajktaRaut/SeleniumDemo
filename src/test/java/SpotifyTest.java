import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpotifyTest {

    String token="Bearer BQBjWCUAlbt8cKOZHcx50T24UwChlKhz7z_nEQuWXq8XzPxD-HJmA88TiwOo_Zl6jjGT5Ord3tENQH8iZaL8qIF5aUiU_ci5qH_ntGFQHrqLtrfUC-0Oewg0TflmlENZdqJ5R6qpMyAtuuVCsOJTzR0iso57y22YumE1NGQgQO8fgE81OGE3S4YAYHn0xVloDzdSjFP7A83EZGkVDLp6V3qdM0DQW-GayM0MZPPAkaDJDi249njF2Nnw5MuSy8Js5lLbSe5IvgyLQfewB2c6QghuiUEN_Q";
    String userId="";

    @Test
    public void givenMethod_ToGetCountPlaylistBeforeAdding() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        String resAsString = response.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        int count = jsonPath.getInt("total");
        System.out.println("Total count is "+count);
        Assert.assertEquals(6,count);
    }

    @Test
    public void givenMethod_ToGetUserProfile() {
        // To get the user profile
        Response responseOfUserProfile = RestAssured.given()
                            .header("Accept","application/json")
                            .header("Content-Type","application/json")
                            .header("Authorization",token)
                            .when()
                            .get("https://api.spotify.com/v1/me");
        ResponseBody userProfileBody = responseOfUserProfile.getBody();
        JsonObject object1 = (JsonObject) new JsonParser().parse(userProfileBody.prettyPrint());
        String resAsString = responseOfUserProfile.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        userId = jsonPath.getString("id");
        System.out.println("User id is "+userId);
        responseOfUserProfile.then().assertThat().statusCode(200);

        System.out.println("-----------------------------------------------------------");

        //To get user profile by using id
        Response responseOfUserById = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId);
        ResponseBody userIdBody = responseOfUserById.getBody();
        Object object2 = new JsonParser().parse(userIdBody.prettyPrint());
        responseOfUserProfile.then().assertThat().statusCode(200);

        System.out.println("-----------------------------------------------------------");

        //To add player List
        Response responseForList = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\": \"Heart  Songs\",\"description\": \"Latest song\",\"public\": \"false\"}")
                .when()
                .post("https://api.spotify.com/v1/users/"+userId+"/playlists");
        ResponseBody responseBody = responseForList.getBody();
        Object jsonObject = new JsonParser().parse(responseBody.prettyPrint());
        responseForList.then().assertThat().statusCode(201);
    }

    @Test
    public void givenMethod_ToGetCountPlaylistAfterAdding() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        String resAsString = response.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        int count = jsonPath.getInt("total");
        System.out.println("Total count is "+count);
        Assert.assertEquals(6,count);
    }

    //To get playlist Id And Update Playlist
    @Test
    public void toGetPlaylistIdAndUpdatePlaylistId() {
        //Get playlist id
        System.out.println("-------- get playlist Id --------");
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        String resAs = response.asString();
        JsonPath json = new JsonPath(resAs);
        String playlistId = json.getString("items[1].id");
        System.out.println("Playlist id is "+playlistId);

        // Updating playlist
        Response responseOfPlaylist = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\": \"Sonu nigam Songs\",\"description\": \"Latest song\",\"public\": false}")
                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistId);
        ResponseBody responseBody = responseOfPlaylist.getBody();
        Object jsonElement = new JsonParser().parse(responseBody.prettyPrint());
        responseOfPlaylist.then().assertThat().statusCode(200);
        System.out.println("--------playlist is Updated--------");

        //Get updated playlist
        System.out.println("-------- Updated playlist --------");
        Response res = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        ResponseBody bodyToGetPlaylist = res.getBody();
        Object object1 = new JsonParser().parse(bodyToGetPlaylist.prettyPrint());
    }
}
