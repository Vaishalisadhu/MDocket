package com.m_docket.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.m_docket.model.searchmodel.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class AutoCompleteService {

    private final Context context;
    private final AutoCompleteCommunicator autoCompleteCommunicator;


    public AutoCompleteService(Context context, AutoCompleteCommunicator autoCompleteCommunicator) {
        this.context = context;
        this.autoCompleteCommunicator = autoCompleteCommunicator;
    }


    public void hitServiceForAutocomplete(String apikey,String lang,String text,int page,boolean isadult) {



        Call<SearchResponse> dataRequester = ApiHelper
                .getInstance(context)
                .getService(MDocketServiceCommunicator.class)
                .getSearchMovieResponse(apikey,lang,text,page,isadult);


        ServiceURLchecker.GET_SERVICE_URL_WHEN_EACH_API_GET_HIT(dataRequester.request().url().toString());

        dataRequester.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull retrofit2.Response<SearchResponse> response) {
                autoCompleteCommunicator.SearchResponse( response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                try {
                    autoCompleteCommunicator.ErrorResponse( t.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }


    public interface AutoCompleteCommunicator {

        void SearchResponse(SearchResponse autocompleteResponse);

        void ErrorResponse(String error_name);


    }

}




