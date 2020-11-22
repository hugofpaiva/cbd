package com.hugopaiva.app.exc;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.conversions.Bson;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.regex;


public class MainExC {

    // Collection of documents
    static MongoCollection<Document> collection;

    public static void main( String[] args )
    {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        // Connection to Mongo database
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {

            MongoDatabase database = mongoClient.getDatabase("cbd");
            collection = database.getCollection("rest");

            createIndex("gastronomia");
            createIndex("localidade");
            createIndexByText("nome");

            // count distinct localities
            System.out.println("\nCounting Distinct localities:");
            System.out.println(countLocalidades());

            // count restaurants by localion
            System.out.println("\nCounting restaurants by location:");
            Map<String, Integer> map = countRestByLocalidade();
            for (String key : map.keySet()) {
                System.out.println(" -> " + key + " - " + map.get(key));
            }

            // count restaurants by localion and gastronomy
            System.out.println("\nCounting restaurants by location and gastronomy:");
            Map<String, Integer> map2 = countRestByLocalidadeByGastronomia();
            for (String key : map2.keySet()) {
                System.out.println(" -> " + key + " - " + map2.get(key));
            }

            // get restaurants names containing name
            System.out.println("\nGetting restaurants names containing 'Park' in the name:");
            List<String> list = getRestWithNameCloserTo("Park");
            for (String value : list) {
                System.out.println(" -> " + value);
            }




        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB database: " + e);
        }

    }

    /**
     * Fetch Documents in collectiong with the given filter
     * @param filter
     */

    public static FindIterable<Document> searchDocumentsFilter(Bson filter){
        FindIterable<Document> docs = null;
        try {
            docs = collection.find(filter);
        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }
        return docs;

    }


    /**
     * Count distinct localities using distinct method
     * @return count
     */
    public static int countLocalidades(){
        int count = 0;

        try{
            DistinctIterable<String> documents = collection.distinct("localidade", String.class);

            for (String document:documents){
                count++;
            }
        } catch(Exception e){
            System.err.println("Error getting countLocalidades in MongoDB collection: " + e);
        }

        return count;
    }

    /**
     * Return a map with restaurant location and the count of them
     * @return
     */
    public static Map<String, Integer> countRestByLocalidade(){
        Map<String, Integer> result = new HashMap<>();

        try {
            AggregateIterable<Document> docs = collection.aggregate(Arrays.asList(
                    group("$localidade", Accumulators.sum("sum",1))));

            for (Document d : docs) {
                result.put(d.get("_id").toString(), (int) (d.get("sum")));
            }

        } catch (Exception e) {
            System.err.println("Error getting countRestByLocalidade in MongoDB collection: " + e);
        }

        return result;
    }

    /**
     * Return a map with restaurant location and gastronomy and the count of them
     * @return
     */
    public static Map<String, Integer> countRestByLocalidadeByGastronomia(){
        Map<String, Integer> result = new HashMap<>();

        BasicDBObject obj = new BasicDBObject();
        obj.put("gastronomia", "$gastronomia");
        obj.put("localidade", "$localidade");

        try {
            AggregateIterable<Document> docs = collection.aggregate(Arrays.asList( group(obj, Accumulators.sum("sum",1))));

            for (Document d : docs) {
                Document doc = (Document) d.get("_id");
                result.put(String.format("%s | %s", doc.get("localidade"), doc.get("gastronomia")), (int) (d.get("sum")));
            }

        } catch (Exception e) {
            System.err.println("Error getting countRestByLocalidadeByGastronomia in MongoDB collection: " + e);
        }

        return result;
    }


    /**
     * Get restaurantes names that contains the name passed
     * @param name
     * @return
     */
    public static List<String> getRestWithNameCloserTo(String name){
        List<String> result = new ArrayList<String>();
        try {
            FindIterable<Document> docs = collection.find(regex("nome", String.format("(%s)", name)));
            for (Document doc : docs) {
                result.add((String) doc.get("nome"));
            }
        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }
        return result;

    }



    /**
     * Creating descending index for a given fieldName
     * @param filedName
     */
    public static void createIndex(String filedName) {
        try {
            collection.createIndex(Indexes.descending(filedName));
        } catch (Exception e) {
            System.err.println("Error creating descending index on " + filedName + "field in MongoDB collection: " + e);
        }
    }

    /**
     * Creating text index for a given fieldname
     * @param filedName
     */
    public static void createIndexByText(String filedName) {
        try {
            collection.createIndex(Indexes.text((filedName)));
        } catch (Exception e) {
            System.err.println("Error creating text index on " + filedName + "field in MongoDB collection: " + e);
        }
    }
}
