package io.vinter.trackmyanime.network.service;


import io.reactivex.Single;
import io.vinter.trackmyanime.entity.animelist.AnimeListItem;
import io.vinter.trackmyanime.entity.animelist.ListResponse;
import io.vinter.trackmyanime.entity.detail.Anime;
import io.vinter.trackmyanime.entity.search.Search;
import io.vinter.trackmyanime.entity.top.Top;
import io.vinter.trackmyanime.entity.user.Message;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeService {
    @GET("/top/anime/{page}/{type}")
    Single<Top> getAnimeTop(@Path("page") int page, @Path("type") String type);

    @GET("search/anime?")
    Single<Search> searchAnime(@Query("q") String q, @Query("page") int page);

    @GET("/anime/{malId}")
    Single<Anime> getAnimeDetail(@Path("malId") int malId);

    @GET("/anime")
    Single<ListResponse> getAnimeList(@Header("Authorization") String token);

    @POST("/anime/update")
    Single<Message> updateAnime(@Header("Authorization") String token, @Body AnimeListItem anime);

    @POST("/anime")
    Single<Message> addAnimeToList(@Header("Authorization") String token, @Body AnimeListItem anime);
}
