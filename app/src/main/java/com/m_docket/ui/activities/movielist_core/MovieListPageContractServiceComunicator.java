package com.m_docket.ui.activities.movielist_core;

import com.google.gson.JsonObject;
import com.m_docket.model.MovieResponse;

public interface MovieListPageContractServiceComunicator {

    interface MovieListPageView {

        void onViewForGetMovieListSuccess(MovieResponse response);



        void onViewRequestExceptionOccure(Exception e);

        void onViewRequestFailure(String message);

        void onViewResponseCustomMessage(String customeResponseMessage);

        void onViewInternetConnection(boolean internetMessage);


    }


    interface  MovieListPagePresenter {


        void presenterSendRequestForGetMovieList(String apikey,String lang, int page);


    }

    interface  MovieListPageInteractor {


        void interactorRetrofitCallForGetMovieList(String ApiKey,String lang, int page);



    }

    interface MovieListPageNetworkDataListener {

        void onNetworkSuccessOfgetMovieListResponse(MovieResponse response);

        void onDataExceptionOccure(Exception e);

        void onNetworkFailure(String message);

        void onCustomeResponseMessage(String customeResponseMessage);

        void onInternetConnection(boolean internetConnectionMessage);
    }


}


