package io.vinter.trackmyanime.network.service;

import java.util.Map;

import io.reactivex.Single;
import io.vinter.trackmyanime.entity.search.Search;
import io.vinter.trackmyanime.entity.top.Top;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AnimeService {
    @GET("/top/anime/{page}/{type}")
    Single<Top> getAnimeTop(@Path("page") int page, @Path("type") String type);

    @GET("search/anime?")
    Single<Search> searchAnime(@Query("q") String q, @Query("page") int page);
}
