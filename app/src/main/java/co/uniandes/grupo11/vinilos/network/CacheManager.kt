package co.uniandes.grupo11.vinilos.network

import android.content.Context
import androidx.collection.LruCache
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.models.Comment
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.models.CollectorDetail

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private var commentsCache: LruCache<Int, List<Comment>> = LruCache(50)
    private var albumDetailsCache: LruCache<Int, Album> = LruCache(30)
    private var artistDetailsCache: LruCache<Int, Any> = LruCache(30)
    private var collectorDetailsCache: LruCache<Int, CollectorDetail> = LruCache(20)
    private var albumsListCache: List<Album>? = null
    private var artistsListCache: List<Performer>? = null
    private var collectorsListCache: List<CollectorDetail>? = null

    fun addComments(albumId: Int, comments: List<Comment>) {
        if (commentsCache[albumId] == null) {
            commentsCache.put(albumId, comments)
        }
    }

    fun getComments(albumId: Int): List<Comment> {
        return commentsCache[albumId] ?: emptyList()
    }

    fun hasComments(albumId: Int): Boolean {
        return commentsCache[albumId] != null
    }

    fun clearComments(albumId: Int) {
        commentsCache.remove(albumId)
    }

    fun addAlbumDetail(albumId: Int, album: Album) {
        if (albumDetailsCache[albumId] == null) {
            albumDetailsCache.put(albumId, album)
        }
    }

    fun getAlbumDetail(albumId: Int): Album? {
        return albumDetailsCache[albumId]
    }

    fun hasAlbumDetail(albumId: Int): Boolean {
        return albumDetailsCache[albumId] != null
    }

    fun clearAlbumDetail(albumId: Int) {
        albumDetailsCache.remove(albumId)
    }

    fun addArtistDetail(artistId: Int, artist: Any) {
        if (artistDetailsCache[artistId] == null) {
            artistDetailsCache.put(artistId, artist)
        }
    }

    fun getArtistDetail(artistId: Int): Any? {
        return artistDetailsCache[artistId]
    }

    fun hasArtistDetail(artistId: Int): Boolean {
        return artistDetailsCache[artistId] != null
    }

    fun clearArtistDetail(artistId: Int) {
        artistDetailsCache.remove(artistId)
    }

    fun addCollectorDetail(collectorId: Int, collector: CollectorDetail) {
        if (collectorDetailsCache[collectorId] == null) {
            collectorDetailsCache.put(collectorId, collector)
        }
    }

    fun getCollectorDetail(collectorId: Int): CollectorDetail? {
        return collectorDetailsCache[collectorId]
    }

    fun hasCollectorDetail(collectorId: Int): Boolean {
        return collectorDetailsCache[collectorId] != null
    }

    fun clearCollectorDetail(collectorId: Int) {
        collectorDetailsCache.remove(collectorId)
    }

    fun setAlbumsList(albums: List<Album>) {
        albumsListCache = albums
    }

    fun getAlbumsList(): List<Album>? {
        return albumsListCache
    }

    fun hasAlbumsList(): Boolean {
        return albumsListCache != null
    }

    fun clearAlbumsList() {
        albumsListCache = null
    }

    fun setArtistsList(artists: List<Performer>) {
        artistsListCache = artists
    }

    fun getArtistsList(): List<Performer>? {
        return artistsListCache
    }

    fun hasArtistsList(): Boolean {
        return artistsListCache != null
    }

    fun clearArtistsList() {
        artistsListCache = null
    }

    fun setCollectorsList(collectors: List<CollectorDetail>) {
        collectorsListCache = collectors
    }

    fun getCollectorsList(): List<CollectorDetail>? {
        return collectorsListCache
    }

    fun hasCollectorsList(): Boolean {
        return collectorsListCache != null
    }

    fun clearCollectorsList() {
        collectorsListCache = null
    }

    fun clearAll() {
        commentsCache.evictAll()
        albumDetailsCache.evictAll()
        artistDetailsCache.evictAll()
        collectorDetailsCache.evictAll()
        albumsListCache = null
        artistsListCache = null
        collectorsListCache = null
    }

    fun invalidateAlbum(albumId: Int) {
        clearAlbumDetail(albumId)
        clearComments(albumId)
        clearAlbumsList()
    }

    fun invalidateArtist(artistId: Int) {
        clearArtistDetail(artistId)
        clearArtistsList()
    }

    fun invalidateCollector(collectorId: Int) {
        clearCollectorDetail(collectorId)
        clearCollectorsList()
    }
}

