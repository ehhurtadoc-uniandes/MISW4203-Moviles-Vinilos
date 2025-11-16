package co.uniandes.grupo11.vinilos.models

data class ArtistDetail(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String? = null,
    val creationDate: String? = null,
    val albums: List<Album> = emptyList(),
    val performerPrizes: List<Award>? = null
)

