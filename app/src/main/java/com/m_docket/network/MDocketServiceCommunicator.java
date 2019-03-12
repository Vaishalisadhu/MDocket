package com.m_docket.network;

import com.m_docket.model.MovieResponse;
import com.m_docket.model.ResultsItem;
import com.m_docket.model.searchmodel.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MDocketServiceCommunicator {

    @GET("search/movie")
    Call<SearchResponse> getSearchMovieResponse(@Query("api_key") String userkey, @Query("language") String language, @Query("query") String text, @Query("page") int page, @Query("include_adult") boolean isadult);

    @GET("movie/now_playing")
    Call<MovieResponse>getNowPlayingMovies(@Query("api_key") String userkey, @Query("language") String language, @Query("page") int page);

}
