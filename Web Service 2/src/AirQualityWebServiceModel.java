/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 05, 2020
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Map;

public class AirQualityWebServiceModel {

    /*
     * Method to write data into MongoDb.
     * Take the time, useragent, countryname, ip address and response as the input
     */
    public void LoadMongo(long time, String useragent, String country, String ip, String response){

        //Connection to mongodb
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://admin:admin@project4mongocluster-y8nha.mongodb.net/test?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        //Getting the database object
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        //Getting the database collection
        MongoCollection<Document> requestLogs = database.getCollection("Application Logs");


        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            //Parsing the response
            jsonObject = (JSONObject) parser.parse(response.toString());
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            JSONObject j2 = (JSONObject) jsonArray.get(0);
            JSONObject j3 = (JSONObject) jsonArray.get(1);
            JSONObject j4 = (JSONObject) jsonArray.get(2);
            JSONObject j5 = (JSONObject) jsonArray.get(3);

            //Hashmap to store all the areas for a country
            Map<String, String> namemap = new HashMap<>();
            namemap.put("name1",j2.get("name").toString());
            namemap.put("name2",j3.get("name").toString());
            namemap.put("name3",j4.get("name").toString());
            namemap.put("name4",j5.get("name").toString());
            BasicDBObject namesDBobject = new BasicDBObject(namemap);

            //Hashmap to store all the measurements for a country
            Map<String,String> measurementmap = new HashMap<>();
            measurementmap.put("m1",j2.get("count").toString());
            measurementmap.put("m2",j3.get("count").toString());
            measurementmap.put("m3",j4.get("count").toString());
            measurementmap.put("m4",j5.get("count").toString());
            BasicDBObject measurementsDBobject = new BasicDBObject(measurementmap);

            //Creating a new document
            Document doc = new Document("useragent",useragent)
                    .append("latency",time)
                    .append("searchword",country)
                    .append("areas",namesDBobject)
                    .append("measurements",measurementsDBobject)
                    .append("Ip address",ip);

            //Adding the document to the collection
            requestLogs.insertOne(doc);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
