package com.hugopaiva.app.exb;

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
 */
public class MainExB
{

    // CQL Sessions
    private static Session session;

    public static void main( String[] args )
    {
        // Connection to Cassandra database
        try {
            Cluster cluster = Cluster.builder().addContactPoints("localhost").build();

            session = cluster.connect("cbd_3_2");


            //3. Todos os vídeos com a tag Aveiro
            System.out.println("\nFetching a videos id with tag 'Aveiro'...");
            directQuery("SELECT video_id FROM Tag_By_Video WHERE name = 'Aveiro';");

            //2. Lista das tags de determinado vídeo
            System.out.println("\nList of tags of a video with id = 3...");
            directQuery("SELECT tags FROM Video WHERE id = 3;");

            //7. Todos os seguidores(followers) de determinado vídeo
            System.out.println("\nList of followers of a video with id = 3...");
            directQuery("SELECT followers_emails FROM Video WHERE id = 3;");

            //5. Vídeos partilhados por determinado utilizador (maria1987, por exemplo) num determinado período de tempo (Agosto de 2017, por exemplo)
            System.out.println("\nVideos shared by 'hugofpaiva' before than '2020-12-16 00:00:00'...");
            directQuery("SELECT * FROM Video_By_Username WHERE uploader_user = 'hugofpaiva' AND upload_date < '2020-12-16 00:00:00';");

            session.close();


        } catch (Exception e) {
            System.err.println("Error connecting to Cassandra database: " + e);
        }

    }

    /**
     * Query database
     * @param query
     */
    public static void directQuery(String query){
        try {
            for (Row row : session.execute(query)) {
                System.out.println(row.toString());
            }
        }catch (Exception e){
            System.err.println("Error fetching Cassandra database: " + e);
        }

    }

}

