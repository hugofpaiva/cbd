package com.hugopaiva.app.exa;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;


/**
 * Exercício 2.4
 * Guided by: https://mongodb.github.io/mongo-java-driver/3.11/
 */
public class MainExA
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

            // Example inserting one restaurant
            System.out.println("\nInserting restaurant...");
            Document restaurant1 = new Document("_id", new ObjectId())
                    .append("address",new Document()
                        .append("building", "50")
                        .append("coord", asList(-74.45321, 40.12321))
                        .append("rua", "Rua Professor Luis")
                        .append("zipcode", "34213")
                    )
                    .append("localidade", "Aveiro")
                    .append("gastronomia", "Portuguesa")
                    .append("grades", asList(
                            new Document("date", new Date())
                            .append("grade", "A")
                            .append("socre", 10)
                        )
                    )
                    .append("nome", "Restaurante do Manel")
                    .append("restaurant_id", "34656653");
            //insertDocument(restaurant1);

            // Example Fetching
            System.out.println("\nSearching all restaurants...");
            searchAllDocuments();
            System.out.println("\nSearching all american restaurants with score between 80 and 100");
            searchDocumentsFilter(and(gte("grades.score", 80), lt("grades.score", 100), eq("gastronomia", "American")));

            // Update Document
            System.out.println("\nUpdating restaurant...");
            updateDocument(eq("nome", "Restaurante do Manel"),
                    Updates.set("nome","Restaurante do Tia Maria"));
            searchDocumentsFilter(eq("nome", "Restaurante do Tia Maria"));


        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB database: " + e);
        }

    }

    /**
     * Insert a document into collection
     * @param docToInsert
     */
    public static void insertDocument(Document docToInsert){
        try {
            collection.insertOne(docToInsert);
        }catch (Exception e){
            System.err.println("Error inserting into MongoDB collection: " + e);
        }

    }

    /**
     * Fetch Documents in collectiong with the given filter
     * @param filter
     */
    public static void searchDocumentsFilter(Bson filter){
        try {
            FindIterable<Document> docs = collection.find(filter);
            for (Document d : docs) {
                System.out.println(d.toJson());
            }
        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }

    }

    /**
     * Fetch all Documents in collection
     */
    public static void searchAllDocuments(){
        try {
            FindIterable<Document> docs = collection.find();
            for (Document d : docs) {
                System.out.println(d.toJson());
            }
        }catch (Exception e){
            System.err.println("Error reading from MongoDB collection: " + e);
        }

    }

    /**
     * Updates a document that matches a given filter with new args passed
     * @param filter
     * @param updateArgs
     */
    public static void updateDocument(Bson filter, Bson updateArgs) {
        try {
            collection.updateOne(filter,updateArgs);
        } catch (Exception e) {
            System.err.println("Error updating on MongoDB collection: " + e);
        }
    }

}
