/**
 * Author: Kabir Soneja
 * Andrew ID: ksoneja
 * Last Modified: April 05, 2020
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;

/*
 * This class has methods to query the database and return the result.
 */
public class DashboardQuery {

    MongoClientURI uri = new MongoClientURI(
            "mongodb+srv://admin:admin@project4mongocluster-y8nha.mongodb.net/test?retryWrites=true&w=majority");

    /*
     * Method for calculating the total number of searches.
     */
    public long getTotalSearches()
    {
        MongoClient mongoClient = new MongoClient(uri);                                         //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> collection = database.getCollection("Application Logs");
        Long count = collection.count();
        mongoClient.close();                                                                    //Closing the collection
        return count;
    }

    /*
     * Method for getting all the logs.
     */
    public List<Document> getLogs() {

        MongoClient mongoClient = new MongoClient(uri);                                         //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> collection = database.getCollection("Application Logs");
        MongoCursor<Document> requestCursor = collection.find().iterator();
        List<Document> resultList = new ArrayList<>();
        while(requestCursor.hasNext()) {
            resultList.add(requestCursor.next());
        }
        mongoClient.close();                                                                     //Closing the collection
        return resultList;
    }

    /*
     * Method for getting the average latency.
     */
    public long getavgLatency() {

        MongoClient mongoClient = new MongoClient(uri);                                            //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> collection = database.getCollection("Application Logs");
        MongoCursor<Document> cursor = collection.find().iterator();
        long totalLatency = 0l;
        while(cursor.hasNext()) {
            totalLatency += Long.parseLong(cursor.next().get("latency").toString());
        }
        long result = totalLatency/collection.countDocuments();
        mongoClient.close();                                                                        //Closing the collection
        return result;
    }

    /*
     * Method for getting the maximum latency.
     */
    public long getMaximumLatency() {

        MongoClient mongoClient = new MongoClient(uri);                                             //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> collection = database.getCollection("Application Logs");
        FindIterable<Document> cursor = collection.find().sort(new BasicDBObject("latency",-1)).limit(1);
        MongoCursor<Document> itr = cursor.iterator();
        long max = 0l;
        while(itr.hasNext()) {
            max = (Long)itr.next().get("latency");
        }
        mongoClient.close();                                                                         //Closing the collection
        return max;
    }

    /*
     * Method for getting minimum latency.
     */
    public long getMinimumLatency() {

        MongoClient mongoClient = new MongoClient(uri);                                             //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> collection = database.getCollection("Application Logs");
        FindIterable<Document> cursor = collection.find().sort(new BasicDBObject("latency",1)).limit(1);
        MongoCursor<Document> itr = cursor.iterator();
        long min = 0l;
        while(itr.hasNext()) {
            min = (Long)itr.next().get("latency");
        }
        mongoClient.close();                                                                         //Closing the collection
        return min;
    }

    /*
     * Method for getting all the areas for each country.
     */
    public List<String> getareas() {

        MongoClient mongoClient = new MongoClient(uri);                                              //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> areaLog = database.getCollection("Application Logs");
        MongoCursor<Document> cursor = areaLog.find().iterator();
        List<String> alist = new ArrayList<>();
        while (cursor.hasNext()) {
            String s = new String();
            Document areas = (Document) cursor.next().get("areas");
            int count = areas.size();
            for (int i = 0; i < count; i++) {
                String key = "name"+String.valueOf(count-i);
                s = s + "," + areas.get(String.valueOf(key));
            }
            s = s.substring(1);
            alist.add(s);
        }
        mongoClient.close();                                                                        //Closing the collection
        return alist;
    }

    /*
     * Method for getting all the measurements for each country.
     */
    public List<String> getmeasurements() {

        MongoClient mongoClient = new MongoClient(uri);                                              //Creating a new mongodb client
        MongoDatabase database = mongoClient.getDatabase("Project4Task2");
        MongoCollection<Document> measurementLog = database.getCollection("Application Logs");
        MongoCursor<Document> cursor = measurementLog.find().iterator();
        List<String> mlist = new ArrayList<>();
        while (cursor.hasNext()) {
            String s = new String();
            Document measurements = (Document) cursor.next().get("measurements");
            int count = measurements.size();
            for (int i = 0; i < count; i++) {
                String key = "m"+String.valueOf(count-i);
                s = s + "," + measurements.get(String.valueOf(key));
            }
            s = s.substring(1);
            mlist.add(s);
        }
        mongoClient.close();                                                                        //Closing the collection
        return mlist;
    }

}
