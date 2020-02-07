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

    String token="Bearer BQB8FfnJ-nz2tTz3EhragA3Zi5xDQZghYOxK2KsHm1OFww_WF0B5Q5GHY-DsEmON3Tk0-jQfN9_8e5p-QNw04jatCHAjE5QPTZSMzmz3KhUmT_bZ_7SxAWxF0YHXnhNMpdFeg-xsbG9lsbBwkcjWBS87Hy2b3udYXVm10afq1vj_yTHNdMjA-KWfunfLrmle-1rgEzwKqA62Ym4zCiNFlJAFActXCzT3W4I-W8sCmHMPfopsDDn8Abks3y6a9XsbpUtJt_IBk9vhQF6mrb_aBIFJ7Vq_CA";
    String userId="";
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
       //To get count of player list
        Response responseOfPlayerListCount = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/playlists");
        System.out.println(userId);
        ResponseBody body3 = responseOfPlayerListCount.getBody();
        Object object3 = new JsonParser().parse(body3.prettyPrint());
        String resAsString1 = responseOfPlayerListCount.asString();
        JsonPath jsonPaths = new JsonPath(resAsString);
        int count = jsonPath.getInt("total");
        System.out.println("Total count is "+count);
        Assert.assertEquals(5,count);

        /*//To add player List
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\": \"Heart Touching Songs\",\"description\": \"Latest song\",\"public\": \"false\"}")
                .when()
                .post("https://api.spotify.com/v1/users/"+userId+"/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        response.then().assertThat().statusCode(201);

        // To get count of after adding new player list into player list
        Response responseOfGetCount = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/playlists");
        ResponseBody countBody = responseOfGetCount.getBody();
        Object objects = new JsonParser().parse(countBody.prettyPrint());
        String responceString = responseOfGetCount.asString();
        JsonPath json = new JsonPath(responceString);
        int countOfPlayerList = jsonPath.getInt("total");
        System.out.println("Total count is "+countOfPlayerList);
        Assert.assertEquals(6,countOfPlayerList);*/
    }

    /*@Test
    public void givenMethod_ToGetUserProfileById() {
                Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId);
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void givenMethod_ToCreatePlaylist() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\": \"old songs\",\"description\": \"Journey to Mumbai\",\"public\": \"false\"}")
                .when()
                .post("https://api.spotify.com/v1/users/kkjrxo2ldcrtkavnglhwu6t41/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        response.then().assertThat().statusCode(201);
    }

    @Test
    public void givenMethod_ToGetCountOfPlaylist() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/kkjrxo2ldcrtkavnglhwu6t41/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        String resAsString = response.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        int count = jsonPath.getInt("total");
        System.out.println("Total count is "+count);
        Assert.assertEquals(3,count);
    }

    @Test
    public void givenMethod_ToAddNewPlaylist() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\": \"Favourite Songs\",\"description\": \"old song\",\"public\": \"false\"}")
                .when()
                .post("https://api.spotify.com/v1/users/kkjrxo2ldcrtkavnglhwu6t41/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        response.then().assertThat().statusCode(201);
    }*/

    @Test
    public void givenMethod_ToGetCountPlaylistAfterAdding() {
        Response response = RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/kkjrxo2ldcrtkavnglhwu6t41/playlists");
        ResponseBody body = response.getBody();
        Object object = new JsonParser().parse(body.prettyPrint());
        String resAsString = response.asString();
        JsonPath jsonPath = new JsonPath(resAsString);
        int count = jsonPath.getInt("total");
        System.out.println("Total count is "+count);
        Assert.assertEquals(5,count);
    }
}
