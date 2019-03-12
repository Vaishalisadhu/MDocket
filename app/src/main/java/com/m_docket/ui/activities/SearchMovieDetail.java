package com.m_docket.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m_docket.R;
import com.m_docket.model.ResultsItem;
import com.m_docket.model.searchmodel.SearchResultsItem;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.m_docket.ui.activities.MainActivity.movieImagePathBuilder;

public class SearchMovieDetail extends AppCompatActivity {
    private ImageView movieBackdrop,back;
    private TextView movieTitle,movieDetailsLang,movieDetailsVote;
    private TextView movieGenres;
    private TextView movieOverview;
    private TextView movieOverviewLabel;
    private TextView movieReleaseDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        initUI();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        SearchResultsItem mMovie = (SearchResultsItem) bundle.getSerializable("movie");




        populateActivity(mMovie);

    }

    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieOverviewLabel = findViewById(R.id.summaryLabel);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieDetailsLang = findViewById(R.id.movieDetailsLang);
        movieDetailsVote = findViewById(R.id.movieDetailsVote);
        back = findViewById(R.id.back);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void populateActivity(SearchResultsItem mMovie) {
        movieTitle.setText(mMovie.getTitle());
        movieOverviewLabel.setVisibility(View.VISIBLE);
        movieOverview.setText(mMovie.getOverview());
        movieDetailsLang.setText(mMovie.getOriginalLanguage());
        movieDetailsVote.setText(String.valueOf( mMovie.getVoteAverage()));

        String  input = mMovie.getReleaseDate();
        String output = input.substring(0, 10);
        output.replace("-", "/");
        Date cDate = new Date();
        output = new SimpleDateFormat("MMM dd, yyyy").format(cDate);
        movieReleaseDate.setText(output);

        if (!isFinishing()) {

            Picasso.with(this).load(movieImagePathBuilder(mMovie.getBackdropPath())).into(movieBackdrop);
        }

    }


}


