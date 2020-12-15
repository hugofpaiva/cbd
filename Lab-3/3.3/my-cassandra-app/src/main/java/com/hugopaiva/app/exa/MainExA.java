package com.hugopaiva.app.exa;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Exercício 3.3
 * Guided by: https://www.baeldung.com/cassandra-with-java
 */
public class MainExA
{

    // CQL Sessions
    private static Session session;

    public static void main( String[] args )
    {
        // Connection to Cassandra database
        try {
            Cluster cluster = Cluster.builder().addContactPoints("localhost").build();

            session = cluster.connect("cbd_3_2");


            // Example inserting one video
            System.out.println("\nInserting a video...");
            Set<String> tagSet =
                    Stream.of("'Tagggs'")
                            .collect(Collectors.toSet());
            Set<String> followersEmail =
                    Stream.of("'hugofpaiva@ua.pt'")
                            .collect(Collectors.toSet());
            insertVideo(20, "Teste", "hugofpaiva@ua.pt","Teste description", tagSet, followersEmail, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            // Example Fetching
            System.out.println("\nSearching all videos...");
            searchAllVideos();

            System.out.println("\nSearching a video with id 2");
            searchVideoById(2);

            // Update Video name with an id = 2
            System.out.println("\nUpdating Video name with id 2...");
            updateVideoByIdUploaderDate(2, "Nome do video 2", "pateta@ua.pt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            System.out.println("\nSearching a video with id 2");
            searchVideoById(2);

            session.close();


        } catch (Exception e) {
            System.err.println("Error connecting to Cassandra database: " + e);
        }

    }

    /**
     * Insert an entry into database on table Video
     * @param id
     * @param name
     * @param uploader_email
     * @param description
     * @param tags
     * @param followers_emails
     * @param upload_date
     */
    public static void insertVideo(Integer id, String name, String uploader_email, String description, Set<String> tags, Set<String> followers_emails, String upload_date){
        try {
            Integer count = 0;
            String tags_string = "{";
            String followers_emails_string = "{";
            for (String tag : tags) {
                if (count == 0) {
                    tags_string += tag;
                    count = 1;
                }
                else
                    tags_string += ","+tag;
            }
            count=0;
            for (String email : followers_emails) {
                if (count == 0) {
                    followers_emails_string += email;
                    count = 1;
                }
                else
                    followers_emails_string += ","+email;
            }
            tags_string += "}";
            followers_emails_string += "}";


            StringBuilder sb = new StringBuilder("INSERT INTO ")
                    .append("Video").append("(id, name, uploader_email, description, tags, followers_emails, upload_date) ")
                    .append("VALUES (").append(id).append(", '" + name + "'").append(", '" + uploader_email + "'").append(", '" + description + "'")
                    .append("," + tags_string).append("," + followers_emails_string).append(", '" + upload_date+"'").append(");");
            String query = sb.toString();
            session.execute(query);
        }catch (Exception e){
            System.err.println("Error inserting into Cassandra database: " + e);
        }

    }

    /**
     * Fetch all Videos in database
     */
    public static void searchAllVideos(){
        try {
            String cqlStatement = "SELECT * FROM Video";
            for (Row row : session.execute(cqlStatement)) {
                System.out.println(row.toString());
            }
        }catch (Exception e){
            System.err.println("Error reading from Cassandra database: " + e);
        }

    }

    /**
     * Fetch videos in database with an id
     * @param id
     */
    public static void searchVideoById(Integer id){
        try {
            String cqlStatement = "SELECT * FROM Video WHERE id = "+id;
            for (Row row : session.execute(cqlStatement)) {
                System.out.println(row.toString());
            }
        }catch (Exception e){
            System.err.println("Error reading from Cassandra database: " + e);
        }

    }

    /**
     * Update a video Name giving it's id, uploader_email and date
     * @param id
     * @param newname
     * @param uploader_email
     * @param date
     */
    public static void updateVideoByIdUploaderDate(Integer id, String newname, String uploader_email, String date){
        try {

            StringBuilder sb = new StringBuilder("UPDATE ")
                    .append("Video ").append("SET name=")
                    .append("'" + newname + "'").append(" WHERE id=").append(id).append(" AND uploader_email=").append("'"+uploader_email+"'")
                    .append(" AND upload_date=").append("'"+date+"'").append(";");
            String query = sb.toString();
            session.execute(query);
        }catch (Exception e){
            System.err.println("Error updating into Cassandra database: " + e);
        }

    }

}
