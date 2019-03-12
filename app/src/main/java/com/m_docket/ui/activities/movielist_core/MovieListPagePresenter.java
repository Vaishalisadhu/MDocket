package com.m_docket.ui.activities.movielist_core;

import android.content.Context;

import com.google.gson.JsonObject;
import com.m_docket.model.MovieResponse;

public class MovieListPagePresenter implements MovieListPageContractServiceComunicator.MovieListPagePresenter, MovieListPageContractServiceComunicator.MovieListPageNetworkDataListener {

    private final MovieListPageContractServiceComunicator.MovieListPageView movieListPageView;
    private MovieListPageIntracator movieListPageIntracator;


    public MovieListPagePresenter(MovieListPageContractServiceComunicator.MovieListPageView movieListPageView, Context context) {
        this.movieListPageView = movieListPageView;
        this.movieListPageIntracator = new MovieListPageIntracator(this,context);
    }


    @Override
    public void presenterSendRequestForGetMovieList(String apikey,String lang, int page) {

        movieListPageIntracator.interactorRetrofitCallForGetMovieList(apikey,lang,page);

    }


    // todo --------Data response form retrofit------------------------


    @Override
    public void onNetworkSuccessOfgetMovieListResponse(MovieResponse response) {

        movieListPageView.onViewForGetMovieListSuccess(response);

    }


    @Override
    public void onDataExceptionOccure(Exception e) {

        movieListPageView.onViewRequestExceptionOccure(e);

    }


    // todo -------- network error, succcess and exception message------------------------


    @Override
    public void onNetworkFailure(String message) {
        movieListPageView.onViewRequestFailure(message);
    }

    @Override
    public void onCustomeResponseMessage(String customeResponseMessage) {

        movieListPageView.onViewResponseCustomMessage(customeResponseMessage);

    }

    @Override
    public void onInternetConnection(boolean internetConnectionMessage) {

        movieListPageView.onViewInternetConnection(internetConnectionMessage);

    }
}
