package com.m_docket.ui.activities.movielist_core;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.JsonObject;
import com.m_docket.model.MovieResponse;
import com.m_docket.network.ApiHelper;
import com.m_docket.network.MDocketServiceCommunicator;
import com.m_docket.network.ServiceURLchecker;
import com.m_docket.utils.ConnectionDetector;
import com.m_docket.utils.GlobalProgressDialog;


import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;

public class MovieListPageIntracator implements MovieListPageContractServiceComunicator.MovieListPageInteractor{

    private final MovieListPageContractServiceComunicator.MovieListPageNetworkDataListener movieListPageNetworkDataListener;
    private final Context context;
    private final String customMessageNoDataFound = "No data found.";
    private final boolean noInternetConnectionMessage = false;
    private final ConnectionDetector connectionDetector;
    private ProgressDialog progress_dialog;

    public MovieListPageIntracator(MovieListPageContractServiceComunicator.MovieListPageNetworkDataListener movieListPageNetworkDataListener, Context context) {
        this.movieListPageNetworkDataListener = movieListPageNetworkDataListener;
        this.context = context;
        this.connectionDetector = new ConnectionDetector(context);
        this.progress_dialog = GlobalProgressDialog.getInstanceOfProgressDialog(context);
    }


    @Override
    public void interactorRetrofitCallForGetMovieList(String apikey,String lang, int page) {
        if (connectionDetector.isConnectingToInternet()) {

            Call<MovieResponse> dataRequester = ApiHelper
                    .getInstance(context)
                    .getService(MDocketServiceCommunicator.class)
                    .getNowPlayingMovies(apikey,lang,page);

            ServiceURLchecker.GET_SERVICE_URL_WHEN_EACH_API_GET_HIT(dataRequester.request().url().toString());

            dataRequester.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull retrofit2.Response<MovieResponse> response) {
                    if (progress_dialog.isShowing())
                        progress_dialog.dismiss();

                    try {

                        MovieResponse movieListResponse = response.body();

                        if (movieListResponse != null) {

                            movieListPageNetworkDataListener.onNetworkSuccessOfgetMovieListResponse(movieListResponse);

                        } else {
                            movieListPageNetworkDataListener.onCustomeResponseMessage(customMessageNoDataFound);
                        }


                    } catch (Exception ee) {
                        if (progress_dialog.isShowing())
                            progress_dialog.dismiss();
                        movieListPageNetworkDataListener.onDataExceptionOccure(ee);
                    }


                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    if (progress_dialog.isShowing())
                        progress_dialog.dismiss();
                    movieListPageNetworkDataListener.onNetworkFailure(t.getMessage());
                }
            });


        } else {
            movieListPageNetworkDataListener.onInternetConnection(noInternetConnectionMessage);

        }
    }


}

