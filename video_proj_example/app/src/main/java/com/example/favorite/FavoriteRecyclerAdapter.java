package com.example.favorite;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.disney.videoApp.FilmGenre;
import com.example.disney.videoApp.Video;
import com.example.fragments.DetailsFragment;
import com.example.test.video_proj_example.R;

import java.util.ArrayList;

/**
 * Created by disney on 9/27/16.
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteViewHolder>  implements Filterable {

    private ArrayList<Video> videos;
    private final Context context;
    private FragmentManager fm;
    private Integer position;
    private VideoFilter filter;

    public FavoriteRecyclerAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
        fm = ((FilmGenre) context).getSupportFragmentManager();
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_favorite, viewGroup,false);
        System.out.println("--------------------" + videos.get(i).getmNumber());
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder favoriteViewHolder, int i) {
        position = i;
        System.out.println("--------------------" + videos.get(i).getmNumber());
        favoriteViewHolder.setmNumber(videos.get(i).getmNumber());
        favoriteViewHolder.setmVideoName(videos.get(i).getmVideoName());
        favoriteViewHolder.setmShowDetails(videos.get(i).getMshowDetailsImageID());
        favoriteViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmGenre.mViewPager.setVisibility(View.INVISIBLE);
                ((FilmGenre) context).findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                fm.beginTransaction().replace(R.id.main_fragment_container,new DetailsFragment(videos.get(position))).addToBackStack(FilmGenre.DETAILS_FRAGMENT).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return null != videos ? videos.size() : 0;
    }


    @Override
    public Filter getFilter() {
        if (null == filter) {
            filter = new VideoFilter();
        }
        return filter;
    }

    private class VideoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                filterResults.values = videos;
                filterResults.count = videos.size();
            } else {
                ArrayList<Video> filteredVideos = new ArrayList<>();
                for (Video video : videos) {
                    if (video.getmVideoName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        System.out.println("------FILTERED VIDEO  " + filteredVideos.size());
                        filteredVideos.add(video);
                    }
                }
                filterResults.values = filteredVideos;
                filterResults.count = filteredVideos.size();

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (0 != results.count) {
                videos = (ArrayList<Video>) results.values;
                notifyDataSetChanged();
            }

        }
    }

}
