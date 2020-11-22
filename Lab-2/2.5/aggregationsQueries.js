// 1.Search 10 top movies according IMDB rating
portugueseDrama = function () {
	return db.movies.find({
		$and: [
			{ countries: { $elemMatch: { $eq: "Portugal" } } },
			{ genres: { $elemMatch: { $eq: "Drama" } } },
		],
	});
};

// 2.Search 20 worst movies rated of Romance genre
englishActionDrama = function () {
	return db.movies.find({
		$and: [
			{ languages: { $elemMatch: { $eq: "English" } } },
			{
				$or: [
					{
						genres: {
							$elemMatch: { $eq: "Drama" },
						},
					},
					{
						genres: {
							$elemMatch: { $eq: "Action" },
						},
					},
				],
			},
		],
	});
};

// 3.List the number of Spanish movies by genre
moviesUSA20102013 = function () {
	return db.movies.find({
		$and: [
			{ countries: { $elemMatch: { $eq: "USA" } } },
			{
				released: {
					$gte: ISODate("2010-03-01T00:00:00Z"),
					$lt: ISODate("2013-09-30T00:00:00Z"),
				},
			},
		],
	});
};

// 4. List actores did not appear in the same movies as star Tom Cruise (i.e. never worked with him)
moviesChristopherNolan = function () {
	return db.movies
		.find({
			directors: {
				$elemMatch: { $eq: "Christopher Nolan" },
			},
		})
		.limit(5);
};

// 5. How many Drama movies Natalie Portman participated
moviesLeonardoDiCaprio = function () {
	return db.movies.find({
		$and: [
			{
				directors: {
					$elemMatch: { $ne: "Leonardo DiCaprio" },
				},
			},
			{
				cast: {
					$elemMatch: { $eq: "Leonardo DiCaprio" },
				},
			},
		],
	});
};

// 6. Show top 20 movies ordered by rating that were at least 10000 votes
scientificFiction = function () {
	return db.movies.find({
		$and: [
			{
				genres: {
					$elemMatch: { $eq: "Thriller" },
				},
			},
			{
				'imdb.rating': {
					$gte: 7.0
				},
			},
		],
	}).sort( { 'imdb.rating': 1 } );
};

// 7. How many movies Emma Watson acting with Daniel Radcliffe
scientificFiction = function () {
	return db.movies.find({
		$and: [
			{
				genres: {
					$elemMatch: { $eq: "Thriller" },
				},
			},
			{
				'imdb.rating': {
					$gte: 7.0
				},
			},
		],
	}).sort( { 'imdb.rating': 1 } );
};

// 8. How many 007 movies were produced with a different main actor. For each main actor, list all 007 movies acted in.
scientificFiction = function () {
	return db.movies.find({
		$and: [
			{
				genres: {
					$elemMatch: { $eq: "Thriller" },
				},
			},
			{
				'imdb.rating': {
					$gte: 7.0
				},
			},
		],
	}).sort( { 'imdb.rating': 1 } );
};