package com.example.tmdb.feature.detail.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DetailMovieWithAllRelations(
    @Embedded val detailEntity: DetailEntity,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "genreId",
        associateBy = Junction(DetailMovieWithGenreCrossRef::class)
    )
    val genres: List<Genre>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "creditId",
        associateBy = Junction(DetailMovieWithCreditCrossRef::class)
    )
    val credits: List<CreditEntity>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "movieId",
        entity = MovieEntity::class,
        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
    )
    val similarMovies: List<SimilarMovieWithGenre>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "movieId"
    )
    val movie: MovieEntity
)

data class SimilarMovieWithGenre(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        entity = Genre::class,
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<Genre>
)

//data class DetailMovieWithCredits(
//    @Embedded val detailEntity: DetailEntity,
//    @Relation(
//        parentColumn = "detailMovieId",
//        entityColumn = "creditId",
//        associateBy = Junction(DetailMovieWithCreditCrossRef::class)
//    )
//    val credits: List<CreditEntity>,
//    @Relation(
//        parentColumn = "detailMovieId",
//        entityColumn = "movieId",
//        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
//    )
//    val similarMovies: List<MovieEntity>
//)
//
//
//data class DetailMovieWithSimilarMovies(
//    @Embedded val detailEntity: DetailEntity,
//    @Relation(
//        parentColumn = "detailMovieId",
//        entityColumn = "movieId",
//        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
//    )
//    val similarMovies: List<MovieEntity>
//)