package com.mobiapps.courses.tmdb.datasources.remote

import com.mobiapps.courses.tmdb.datasources.remote.dtos.GenreDto
import com.mobiapps.courses.tmdb.entities.CastMember
import com.mobiapps.courses.tmdb.entities.Movie

class MockDataSource {
    val genres: List<GenreDto> = listOf(
        GenreDto(28, "Action"),
        GenreDto(12, "Adventure"),
        GenreDto(16, "Animation"),
        GenreDto(35, "Comedy"),
        GenreDto(80, "Crime"),
        GenreDto(99, "Documentary"),
        GenreDto(18, "Drama"),
        GenreDto(10751, "Family"),
        GenreDto(14, "Fantasy"),
        GenreDto(36, "History"),
        GenreDto(27, "Horror"),
        GenreDto(10402, "Music"),
        GenreDto(9648, "Mystery"),
        GenreDto(10749, "Romance"),
        GenreDto(878, "Science Fiction"),
        GenreDto(10770, "TV Movie"),
        GenreDto(53, "Thriller"),
        GenreDto(10752, "War"),
        GenreDto(37, "Western"),
    )

    val movies: List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Interstellar",
            averageVote = 8.6f,
            votesNumber = 9999,
            overview = "Best sci-fi movie ever",
            cast = listOf(
                CastMember(name = "Matthew McConaughey"),
                CastMember(name = "Jessica Chastain"),
                CastMember(name = "Anne Hathaway"),
                CastMember(name = "Michael Caine"),
            )
        ),
        Movie(
            id = 2,
            title = "The Batman",
            averageVote = 7.9f,
            votesNumber = 9999,
            overview = "Best Batman movie ever",
            cast = listOf(
                CastMember(name = "Robert Pattinson"),
                CastMember(name = "Zoe Kravitz"),
                CastMember(name = "Paul Dano"),
            )
        ),
        Movie(
            id = 3,
            title = "Fight Club",
            averageVote = 8.8f,
            votesNumber = 9999,
            overview = "Best movie ever",
            cast = listOf(
                CastMember(name = "Brad Pitt"),
                CastMember(name = "Edward Norton"),
                CastMember(name = "Helena Bonham Carter"),
            )
        )
    )
}