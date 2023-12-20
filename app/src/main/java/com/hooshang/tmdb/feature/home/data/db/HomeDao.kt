package com.hooshang.tmdb.feature.home.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.NowPlayingWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.PopularMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.TopMovieAndGenreWithMovie
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Query("SELECT * FROM now_playing_movies")
    fun observeNowPlayingMovies(): Flow<List<NowPlayingWithMovie>>

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovies(): Flow<List<PopularMovieAndGenreWithMovie>>

    @Query("SELECT * FROM top_movies")
    fun observeTopMovies(): Flow<List<TopMovieAndGenreWithMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenres(genre: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovies(movie: List<NowPlayingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovies(movie: List<PopularMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre: List<PopularMovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovies(movie: List<TopMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre: List<TopMovieGenreCrossRef>)

    @Query("DELETE FROM now_playing_movies")
    fun removeNowPlayingMovies()

    @Query("DELETE FROM popularmoviegenrecrossref")
    fun removePopularMoviesGenre()

    @Query("DELETE FROM popular_movies")
    fun removePopularMovies()

    @Query("DELETE FROM top_movies")
    fun removeTopMovies()

    @Query("DELETE FROM topmoviegenrecrossref")
    fun removeTopMoviesGenre()
}