// 1.List all portuguese Drama movies
portugueseDrama = function () {
	return db.movies.find({
		$and: [
			{ countries: { $elemMatch: { $eq: "Portugal" } } },
			{ genres: { $elemMatch: { $eq: "Drama" } } },
		],
	});
};

// 2.Search all English movies of Action or Drama genres
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

// 3.Search all movies from USA released between march 2010 and september 2013
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

// 4. List 5 movies directed by Christopher Nolan
moviesChristopherNolan = function () {
	return db.movies
		.find({
			directors: {
				$elemMatch: { $eq: "Christopher Nolan" },
			},
		})
		.limit(5);
};

// 5. Search all movies where Leonardo DiCaprio acted but not directed in.
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

// 6. List all Thriller movies with at least 7.0 rating ordered by rating
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
