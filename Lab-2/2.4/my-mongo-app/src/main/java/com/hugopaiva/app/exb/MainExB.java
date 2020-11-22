package com.hugopaiva.app.exb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;


/**
 * Exercício 2.4
 * Guided by: https://mongodb.github.io/mongo-java-driver/3.11/
 */
public class MainExB
{

    // Collection of documents
    static MongoCollection<Document> collection;

    public static void main( String[] args )
    {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

        // Connection to Mongo database
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {

            MongoDatabase database = mongoClient.getDatabase("cbd");
            collection = database.getCollection("rest");

            // Example Fetching without indexes
            System.out.println("\nSearching all american restaurants located in Manhatan");
            long startTime = System.nanoTime();
            searchDocumentsFilter(and(eq("gastronomia", "American"), eq("localidade", "Manhattan")));
            long stopTime = System.nanoTime();
            System.out.println("Search Time: " + (stopTime - startTime));

            startTime = System.nanoTime();
            System.out.println("\nSearching all restaurants starting with Wil");
            searchDocumentsFilter(regex("nome", "^Wil"));
            stopTime = System.nanoTime();
            System.out.println("Search Time: " + (stopTime - startTime));

            // Example Fetching using indexes
            createIndex("gastronomia");
            createIndex("localidade");
            createIndexByText("nome");
            startTime = System.nanoTime();
            System.out.println("\nSearching all american restaurants located in Manhatan");
            searchDocumentsFilter(and(eq("gastronomia", "American"), eq("localidade", "Manhattan")));
            stopTime = System.nanoTime();
            System.out.println("Search Time: " + (stopTime - startTime));

            startTime = System.nanoTime();
            System.out.println("\nSearching all restaurants starting with Wil");
            searchDocumentsFilter(regex("nome", "^Wil"));
            stopTime = System.nanoTime();
            System.out.println("Search Time: " + (stopTime - startTime));



        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB database: " + e);
        }

    }



    /**
     * Fetch Documents in collectiong with the given filter
     * @param filter
     */
    public static void searchDocumentsFilter(Bson filter){
        try {
            FindIterable<Document> docs = collection.find(filter);
            /*for (Document d : docs) {
                System.out.println(d.toJson());
            }*/
        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }

    }

    /**
     * Creating ascending index for a given fieldName
     * @param filedName
     */
    public static void createIndex(String filedName) {
        try {
            collection.createIndex(Indexes.ascending(filedName));
        } catch (Exception e) {
            System.err.println("Error creating ascending index on " + filedName + "field in MongoDB collection: " + e);
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
