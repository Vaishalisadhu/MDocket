package com.m_docket.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m_docket.R;
import com.m_docket.adapter.MovieAdapter;
import com.m_docket.adapter.SearchAdapter;
import com.m_docket.model.MovieResponse;
import com.m_docket.model.ResultsItem;
import com.m_docket.model.searchmodel.SearchResponse;
import com.m_docket.model.searchmodel.SearchResultsItem;
import com.m_docket.network.AutoCompleteService;
import com.m_docket.ui.activities.movielist_core.MovieListPageContractServiceComunicator;
import com.m_docket.ui.activities.movielist_core.MovieListPagePresenter;
import com.m_docket.utils.EndlessRecyclerViewScrollListener;
import com.m_docket.utils.MovieClickListener;
import com.m_docket.utils.SearchMovieClickListner;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements MovieListPageContractServiceComunicator.MovieListPageView {

    public static final String API_KEY = "e9bb85efff73305f0824e3af9c58c7b2";
    public static final String lang = "en-US";
    private static int totalPages;
    private static int pages = 1;
    private static int currentSortMode = 1;
    private Call<MovieResponse> call;
    private RecyclerView recyclerView;
    private List<ResultsItem> movieResults;
    private MovieAdapter movieAdapter;
    private MovieListPagePresenter movieListPagePresenter;
    private Context context;
    private EditText edtsearch;
    private ImageView search, clear;
    private List<SearchResultsItem> suggestionsList = new ArrayList<>();
    private AutoCompleteTextView searchTextView;
    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialized();
        initUI();



        loadPage(1);


        final AutoCompleteService parseData = new AutoCompleteService(getApplicationContext(), autoCompleteCommunicator);


        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    parseData.hitServiceForAutocomplete(API_KEY, lang, editable.toString(), pages, false);
                } else if (editable.toString().length() <= 1) {

                    recyclerView.setAdapter(movieAdapter);

                } else if (editable.toString().length() == 0) {

                    recyclerView.setAdapter(movieAdapter);

                }
            }
        });


    }


    private final AutoCompleteService.AutoCompleteCommunicator autoCompleteCommunicator = new AutoCompleteService.AutoCompleteCommunicator() {
        @Override
        public void SearchResponse(SearchResponse autocompleteResponse) {
            try {
                if (autocompleteResponse.getResults().size() > 0) {
                    suggestionsList.clear();
                    suggestionsList.addAll(autocompleteResponse.getResults());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (edtsearch.getText().length() > 0) {
                recyclerView.setVisibility(View.VISIBLE);

                searchAdapter = new SearchAdapter(suggestionsList, new SearchMovieClickListner() {
                    @Override
                    public void onMovieClick(SearchResultsItem movie) {
                        Intent intent = new Intent(MainActivity.this, SearchMovieDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movie", movie);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(searchAdapter);
            } else {
                List<SearchResultsItem> movies = autocompleteResponse.getResults();
                for (SearchResultsItem movie : movies) {
                    suggestionsList.add(movie);
                    searchAdapter.notifyItemInserted(suggestionsList.size() - 1);
                }
            }


        }


        @Override
        public void ErrorResponse(String error_name) {
            Toast.makeText(getApplicationContext(), "Autocomplete Error:- " + error_name, Toast.LENGTH_SHORT).show();
        }
    };


    private void loadPage(final int page) {

        pages = page;
        movieListPagePresenter.presenterSendRequestForGetMovieList(API_KEY, lang, page);

    }


    private void initialized() {
        context = this;
        movieListPagePresenter = new MovieListPagePresenter(MainActivity.this, context);


    }

    private void initUI(){recyclerView = findViewById(R.id.rv_movies);

        edtsearch = findViewById(R.id.edtsearch);
        search = findViewById(R.id.search);
        edtsearch.setFocusable(true);
        edtsearch.setSelection(0);
        edtsearch.setFocusableInTouchMode(true);
        edtsearch.requestFocus();

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };


        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(edtsearch, R.drawable.search_cursor_white);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }



        recyclerView.addOnScrollListener(scrollListener);

    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

    @Override
    public void onViewForGetMovieListSuccess(MovieResponse response) {


        if (response != null && response.getResults().size() > 0) {

            if (pages == 1) {
                movieResults = response.getResults();
                totalPages = response.getTotalPages();

                movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                    @Override
                    public void onMovieClick(ResultsItem movie) {
                        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movie", movie);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(movieAdapter);
            } else {
                List<ResultsItem> movies = response.getResults();
                for (ResultsItem movie : movies) {
                    movieResults.add(movie);
                    movieAdapter.notifyItemInserted(movieResults.size() - 1);
                }
            }
        }


    }

    @Override
    public void onViewRequestExceptionOccure(Exception e) {
        Log.d("exception", "" + e);
    }

    @Override
    public void onViewRequestFailure(String message) {
        Log.d("failure", "" + message);
    }

    @Override
    public void onViewResponseCustomMessage(String customeResponseMessage) {
        Log.d("message", "" + customeResponseMessage);
    }

    @Override
    public void onViewInternetConnection(boolean internetMessage) {

    }

}

