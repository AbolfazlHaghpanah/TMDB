package com.example.tmdb.feature.detail.data.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.Entity.MovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.detail.data.credit.CreditEntity
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.detail.DetailEntity
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import kotlinx.collections.immutable.toPersistentList

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
    val movie: MovieEntity,
    @Relation(
        parentColumn = "detailMovieId",
        entityColumn = "movieId"
    )
    val favorite: FavoriteMovieEntity?
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
) {
    fun toMovieWithGenreDataBaseWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            movie = MovieDatabaseWrapper(
                movieId = similarMovie.id,
                backdropPath = similarMovie.backdropPath,
                voteAverage = similarMovie.voteAverage,
                posterPath = similarMovie.posterPath,
                releaseDate = "",
                title = similarMovie.title
            ),
            genres = genres.toPersistentList()
        )
    }
}