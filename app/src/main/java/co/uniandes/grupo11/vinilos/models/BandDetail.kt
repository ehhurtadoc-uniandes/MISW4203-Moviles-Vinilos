package co.uniandes.grupo11.vinilos.models

data class BandDetail(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String? = null,
    val albums: List<Album> = emptyList(),
    val musicians: List<Performer> = emptyList(),
    val performerPrizes: List<Award>? = null
)

