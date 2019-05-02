package io.vinter.trackmyanime.network.service


import io.reactivex.Single
import io.vinter.trackmyanime.entity.animelist.AnimeListItem
import io.vinter.trackmyanime.entity.animelist.ListResponse
import io.vinter.trackmyanime.entity.detail.Anime
import io.vinter.trackmyanime.entity.search.Search
import io.vinter.trackmyanime.entity.top.Top
import io.vinter.trackmyanime.entity.user.Message
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {
    @GET("/top/anime/{page}/{type}")
    fun getAnimeTop(@Path("page") page: Int, @Path("type") type: String): Single<Top>

    @GET("search/anime?")
    fun searchAnime(@Query("q") q: String, @Query("page") page: Int): Single<Search>

    @GET("/anime/{malId}")
    fun getAnimeDetail(@Path("malId") malId: Int): Single<Anime>

    @GET("/anime")
    fun getAnimeList(@Header("Authorization") token: String): Single<ListResponse>

    @POST("/anime/update")
    fun updateAnime(@Header("Authorization") token: String, @Body anime: AnimeListItem): Single<Message>

    @POST("/anime")
    fun addAnimeToList(@Header("Authorization") token: String, @Body anime: AnimeListItem): Single<Message>

    @POST("/anime/delete")
    fun deleteAnime(@Header("Authorization") token: String, @Body anime: AnimeListItem): Single<Message>
}
