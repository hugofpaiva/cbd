// 1.Number of movies by all actors ordered descendent
allCastCount = function () {
	return db.movies.aggregate(
		{ $unwind: "$cast" },
		{ $group: { _id: "$cast", count: { $sum: 1 } } },
		{ $sort: { count: -1 } }
	);
};

// 2.Average Rating of Tom Cruise movies
avgRatingTomCruise = function () {
	return db.movies.aggregate(
		{ $unwind: "$cast" },
		{
			$match: {
				cast: {
					$eq: "Tom Cruise",
				},
			},
		},
		{ $group: { _id: "$cast", avgRate: { $avg: "$imdb.rating" } } }
	);
};

// 3.List actors who did not appear in the same movies as the star Tom Cruise (i.e. never worked with him)
castWithoutTom = function () {
	return db.movies.aggregate(
		{ $unwind: "$cast" },
		{
			$match: {
				cast: {
					$ne: "Tom Cruise",
				},
			},
		},
		{ $project: { cast: 1, _id: 0 } }
	);
};

// 4.How many movies where rated upper 7.0 and have only one director
MoviesRatedDirector = function () {
	return db.movies.aggregate(
		{
			$unwind: "$directors",
		},
		{
			$group: {
				_id: "$title",
				avgRate: { $first: "$imdb.rating" },
				unique_directors: { $addToSet: "$directors" },
			},
		},
		{
			$project: {
				title: 1,
				avgRate: 1,
				countDirector: { $size: "$unique_directors" },
			},
		},
		{
			$match: {
				avgRate: {
					$gt: 7,
				},
				countDirector: { $eq: 1 },
			},
		}
	);
};

// 5.Movie with the most cast
movieMostCast = function () {
	return db.movies.aggregate(
		{ $unwind: "$cast" },
		{
			$group: {
				_id: "$title",
				countCast: { $sum: 1 },
			},
		},
		{ $sort: { countCast: -1 } },
		{ $limit: 1 }
	);
};

// 6.Avg rating of movies made in 1986
avgRate1986 = function () {
	return db.movies.aggregate(
		{
			$group: {
				_id: "$year",
				avgRate: { $avg: "$imdb.rating" },
			},
		},
		{
			$match: {
				_id: {
					$eq: 1986,
				},
			},
		}
	);
};

// 7.List directors who have at least 3 action films rated higher than 7
directorsAvgRate = function () {
	return db.movies.aggregate(
		{
			$unwind: "$directors",
		},
		{
			$match: {
				genres: {
					$elemMatch: { $eq: "Action" },
				},
			},
		},
		{
			$unwind: "$genres",
		},
		{
			$group: {
				_id: "$directors",
				avgRate: { $avg: "$imdb.rating" },
				countActionGenre: { $sum: 1 },
			},
		},

		{
			$match: {
				avgRate: {
					$gt: 7,
				},
				countActionGenre: {
					$gt: 3,
				},
			},
		}
	);
};

// 8.Harry Potter movie with more comments
movieHarryPotter = function () {
	return db.movies.aggregate(
		{
			$match: {
				title: { $regex: ".*Harry Potter.*" },
			},
		},
		{$unwind: "$comments"},
		{
			$group: {
				_id: "$title",
				countComments: { $sum: 1 },
			},
		},
		{ $sort: { countComments: -1 } },
		{ $limit: 1 }
	);
};
