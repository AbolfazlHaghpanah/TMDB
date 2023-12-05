package com.example.tmdb.feature.detail.data.source.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.detail.data.source.local.entity.CreditEntity
import com.example.tmdb.feature.detail.data.source.local.entity.DetailEntity
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.source.local.relation.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.domain.model.MovieDetail
import com.example.tmdb.feature.detail.domain.model.SimilarMovie
import com.example.tmdb.feature.favorite.data.local.entity.FavoriteMovieEntity

data class DetailMovieWithAllRelations(
    @Embedded val detailEntity: DetailEntity,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "genreId",
        associateBy = Junction(DetailMovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "creditId",
        associateBy = Junction(DetailMovieWithCreditCrossRef::class)
    )
    val credits: List<CreditEntity>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id",
        entity = MovieEntity::class,
        associateBy = Junction(DetailMovieWithSimilarMoviesCrossRef::class)
    )
    val similarMovies: List<SimilarMovieWithGenre>?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "id"
    )
    val movie: MovieEntity?,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "movieId"
    )
    val favorite: FavoriteMovieEntity?
) {
    fun toMovieDetail(): MovieDetail = MovieDetail(
        id = detailEntity.detailMovieId,
        title = movie?.title ?: "",
        overview = detailEntity.overview,
        voteAverage = movie?.voteAverage ?: 0.0,
        posterPath = movie?.posterPath ?: "",
        releaseDate = detailEntity.releaseDate.split("-")[0],
        genres = genres?.map { Pair(it.genreId, it.genreName) } ?: listOf(),
        credits = credits?.map { it.toCastOrCrew() } ?: listOf(),
        runtime = detailEntity.runtime,
        externalIds = detailEntity.externalIds,
        similar = similarMovies?.map { it.toSimilarMovie() } ?: listOf(),
        isFavorite = favorite != null
    )
}

data class SimilarMovieWithGenre(
    @Embedded val similarMovie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId",
        entity = GenreEntity::class,
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
) {
    //    fun toMovieWithGenreDataBaseWrapper(): MovieWithGenreDatabaseWrapper {
//        return MovieWithGenreDatabaseWrapper(
//            movie = MovieDatabaseWrapper(
//                movieId = similarMovie.id,
//                backdropPath = similarMovie.backdropPath,
//                voteAverage = similarMovie.voteAverage,
//                posterPath = similarMovie.posterPath,
//                releaseDate = "",
//                title = similarMovie.title
//            ),
//            genres = genres.toPersistentList()
//        )
//    }
    fun toSimilarMovie(): SimilarMovie = SimilarMovie(
        id = similarMovie.id,
        posterPath = similarMovie.posterPath,
        voteAverage = similarMovie.voteAverage.toFloat(),
        title = similarMovie.title,
        genreIds = genres.joinToString(separator = "|") { it.genreName }
    )
}