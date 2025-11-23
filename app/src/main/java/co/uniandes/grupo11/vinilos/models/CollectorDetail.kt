package co.uniandes.grupo11.vinilos.models

data class CollectorDetail(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment> = emptyList(),
    val favoritePerformers: List<Performer> = emptyList(),
    val collectorAlbums: List<CollectorAlbum> = emptyList()
)

