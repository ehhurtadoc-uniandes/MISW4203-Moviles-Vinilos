package co.uniandes.grupo11.vinilos.models

data class CollectorAlbum(
    val id: Int,
    val price: Int,
    val status: String,
    val album: Album? = null
)

