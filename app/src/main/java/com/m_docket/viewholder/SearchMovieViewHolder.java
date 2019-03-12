package com.m_docket.viewholder;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.m_docket.R;
import com.m_docket.model.ResultsItem;
import com.m_docket.model.searchmodel.SearchResultsItem;
import com.m_docket.utils.MovieClickListener;
import com.m_docket.utils.SearchMovieClickListner;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.m_docket.ui.activities.MainActivity.movieImagePathBuilder;

public class SearchMovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie_poster)
    ImageView mMoviePoster;
    @BindView(R.id.cv_movie_card)
    CardView mMovieCard;


    public SearchMovieViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final SearchResultsItem movie, final SearchMovieClickListner movieClickListener) {

        mMovieCard.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));

        Picasso.with(mMoviePoster.getContext()).load(movieImagePathBuilder(movie.getPosterPath())).fit().centerCrop().into(mMoviePoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieClickListener.onMovieClick(movie);
            }
        });
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    private int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }
}

