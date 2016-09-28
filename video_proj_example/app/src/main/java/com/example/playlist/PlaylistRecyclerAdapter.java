package com.example.playlist;

import android.app.Activity;
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
public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistViewHolder> implements Filterable {

    private ArrayList<Video> videos;
    private final Context context;
    private FragmentManager fm;
    private Integer position;
    private VideoFilter filter;


    public PlaylistRecyclerAdapter(Activity context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
        fm = ((FilmGenre) context).getSupportFragmentManager();
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_playlist, viewGroup, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder playlistViewHolder, int i) {
        position = i;
        playlistViewHolder.videoName.setText(videos.get(i).getmVideoName());
        playlistViewHolder.videoPicture.setImageResource(videos.get(i).getmImageId());
        playlistViewHolder.favIcon.setImageResource(videos.get(i).getMarkFavorite());
        playlistViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmGenre.mViewPager.setVisibility(View.GONE);
                ((FilmGenre) context).findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                fm.beginTransaction().replace(R.id.main_fragment_container, new DetailsFragment(videos.get(position)),
                        FilmGenre.DETAILS_FRAGMENT).addToBackStack(FilmGenre.DETAILS_FRAGMENT).commit();
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
