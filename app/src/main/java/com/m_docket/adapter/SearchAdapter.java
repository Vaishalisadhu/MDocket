package com.m_docket.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m_docket.R;
import com.m_docket.model.searchmodel.SearchResponse;
import com.m_docket.model.searchmodel.SearchResultsItem;
import com.m_docket.utils.MovieClickListener;
import com.m_docket.utils.SearchMovieClickListner;
import com.m_docket.viewholder.SearchMovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.m_docket.ui.activities.MainActivity.movieImagePathBuilder;

public class SearchAdapter extends RecyclerView.Adapter<SearchMovieViewHolder> {

    private final SearchMovieClickListner movieClickListener;
    private final List<SearchResultsItem> movieList;

    public SearchAdapter(List<SearchResultsItem> movieList, SearchMovieClickListner movieClickListener) {
        this.movieList = movieList;
        this.movieClickListener = movieClickListener;
    }

    @Override
    public SearchMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchMovieViewHolder holder, int position) {
        SearchResultsItem movie = this.movieList.get(position);
        holder.bind(movie, movieClickListener);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    @Override
    public void onViewRecycled(SearchMovieViewHolder holder) {
        super.onViewRecycled(holder);
    }
} /*RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Activity activity;
    List<SearchResultsItem> suggestionsList;


    public SearchAdapter(Activity activity, List<SearchResultsItem> suggestionsList) {
        this.activity = activity;
        this.suggestionsList = suggestionsList;
        // sessionManager = new SessionManager(activity);

    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = null;
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            root = layoutInflater.inflate(R.layout.row_search, parent, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SearchAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        try {

            Picasso.with(activity).load(movieImagePathBuilder(suggestionsList.get(position).getBackdropPath())).into(holder.iv_movie_poster);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return suggestionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_movie_poster;
        CardView cv_movie_card;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_movie_poster = (ImageView) itemView.findViewById(R.id.movie_poster);
            cv_movie_card = (CardView)itemView.findViewById(R.id.cv_movie_card);
            cv_movie_card.setLayoutParams(new ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));

        }

    }
    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    private int getMeasuredPosterHeight(int width) {
        return (int) (width * 1.5f);
    }
}
*/