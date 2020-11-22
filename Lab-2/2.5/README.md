# Exercise 2.5 - Movies Database

**Source**: https://www.kaggle.com/mandeeplannister/imdb-movies-dataset

## Import

Import **collections.json** file into MongoDB, like the restaurants exercise using the following command:

  ```
  mongoimport --db cbd --collection movies --drop --file <path/>collection.json
  ```

![Import proof](./import.png)



## Document Structure

```
{
  "_id": {
    "$oid": "573a1398f29313caabcea315"
  },
  "title": "Top Gun",
  "year": {
    "$numberInt": "1986"
  },
  "runtime": {
    "$numberInt": "110"
  },
  "released": {
    "$date": {
      "$numberLong": "516585600000"
    }
  },
  "cast": [
    "Tom Cruise",
    "Kelly McGillis",
    "Val Kilmer",
    "Anthony Edwards"
  ],
  "poster": "https://m.media-amazon.com/images/M/MV5BMTY3ODg4OTU3Nl5BMl5BanBnXkFtZTYwMjI1Nzg4._V1_SX300.jpg",
  "plot": "As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.",
  "fullplot": "Lieutenant Pete \"Maverick\" Mitchell is an expert United States Naval Aviator. When he encounters a pair of MiGs over the Persian Gulf, his wingman is clearly outflown and freaks. On almost no fuel, Maverick is able to talk him back down to the carrier. When his wingman turns in his wings, Maverick is moved up in the standings and sent to the Top Gun Naval Flying School. There he fights the attitudes of the other pilots and an old story of his father's death in combat that killed others due to his father's error. Maverick struggles to be the best pilot, stepping on the toes of his other students and in another way to Charlie Blackwood, a civilian instructor to whom he is strongly attracted.",
  "awards": "Won 1 Oscar. Another 9 wins & 5 nominations.",
  "lastupdated": "2015-08-23 00:18:22.953000000",
  "type": "movie",
  "languages": [
    "English"
  ],
  "directors": [
    "Tony Scott"
  ],
  "writers": [
    "Jim Cash",
    "Jack Epps Jr.",
    "Ehud Yonay (magazine article \"Top Guns\")"
  ],
  "imdb": {
    "rating": {
      "$numberDouble": "6.8"
    },
    "votes": {
      "$numberInt": "201161"
    },
    "id": {
      "$numberInt": "92099"
    }
  },
  "countries": [
    "USA"
  ],
  "rated": "PG",
  "genres": [
    "Action",
    "Drama",
    "Romance"
  ],
  "num_mflix_comments": {
    "$numberInt": "1"
  },
  "comments": [
    {
      "name": "Theon Greyjoy",
      "email": "alfie_allen@gameofthron.es",
      "movie_id": {
        "$oid": "573a1398f29313caabcea315"
      },
      "text": "Delectus exercitationem dolorem dignissimos distinctio. Magni a ipsa aut repellendus. Dignissimos tempora maiores nihil maxime.",
      "date": {
        "$date": {
          "$numberLong": "414254578000"
        }
      },
      "_id": "573a1398f29313caabcea315-Theon Greyjoy-414272578.0"
    }
  ]
}
```

## Queries using find() operator
1. List all portuguese Drama movies
2. Search all English movies of Action or Drama genres
3. Search all movies from USA released between march 2010 and september 2013
4. List 5 movies directed by Christopher Nolan
5. Search all movies where Leonardo DiCaprio acted but not directed in.
6. List all Thriller movies with at least 7.0 rating ordered by rating

## Queries using aggregations
1. Search 10 top movies according IMDB rating
2. Search 20 worst movies rated of Romance genre
3. List the number of Spanish movies by genre
4. List actores did not appear in the same movies as star Tom Cruise (i.e. never worked with him)
5. How many Drama movies Natalie Portman participated
6. Show top 20 movies ordered by rating that were at least 10000 votes
7. How many movies Emma Watson acting with Daniel Radcliffe
8. How many 007 movies were produced with a different main actor. For each main actor, list all 007 movies acted in.
