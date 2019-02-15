package io.vinter.trackmyanime.network.service;

import io.reactivex.Single;
import io.vinter.trackmyanime.entity.Top;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnimeService {
    @GET("/top/anime/{page}/{type}")
    Single<Top> getAnimeTop(@Path("page") int page, @Path("type") String type);
}
