package com.hooshang.tmdb.core.ui.shimmer

import com.hooshang.tmdb.feature.home.domain.model.HomeMovieDomainModel
import kotlinx.collections.immutable.persistentListOf

val fakeMovie = persistentListOf(
    HomeMovieDomainModel(
        "", "//////////////////", 0, "/////////////", "", 0.1,
    ),
    HomeMovieDomainModel(
        "", "//////////////////", 1, "////////////", "", 0.1,
    ),
    HomeMovieDomainModel(
        "", "//////////////////", 2, "///////////", "", 0.1,
    ),
    HomeMovieDomainModel(
        "", "//////////////////", 2, "//////////", "", 0.1,
    ),
    HomeMovieDomainModel(
        "", "//////////////////", 2, "/////////", "", 0.1,
    )

)