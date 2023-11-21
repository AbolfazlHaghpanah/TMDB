package com.example.tmdb.feature.detail.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.detail.data.credit.CreditEntity
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.detail.DetailEntity

//TODO create a relation package
data class DetailMovieWithAllRelations(
    @Embedded val detailEntity: DetailEntity,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "genreId",
        associateBy = Junction(DetailMovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "creditId",
        associateBy = Junction(DetailMovieWithCreditCrossRef::class)
    )
    val credits: List<CreditEntity>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id",
        entity = MovieEntity::class,
        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
    )
    val similarMovies: List<SimilarMovieWithGenre>,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id"
    )
    val movie: MovieEntity
)

data class SimilarMovieWithGenre(
    @Embedded val similarMovie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId",
        entity = GenreEntity::class,
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)