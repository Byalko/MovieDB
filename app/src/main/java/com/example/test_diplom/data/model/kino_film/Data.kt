package com.example.test_diplom.data.model.kino_film

data class Data(
    val countries: List<Country>,
    val description: String,
    val distributorRelease: String,
    val distributors: String,
    val facts: List<String>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val premiereBluRay: String,
    val premiereDigital: String,
    val premiereDvd: String,
    val premiereRu: String,
    val premiereWorld: String,
    val premiereWorldCountry: String,
    val ratingAgeLimits: Int,
    val ratingMpaa: String,
    val seasons: List<Season>,
    val slogan: String,
    val type: String,
    val webUrl: String,
    val year: String
)