package com.example.disney.videoApp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.fragments.PlaylistFragment;
import com.example.test.video_proj_example.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by disney on 9/27/16.
 */
public class GenrePagerAdapter extends FragmentPagerAdapter  implements Filterable{

    Context context;
    VideoFilter filter;

    public GenrePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaylistFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.melodramy).toUpperCase(l);
            case 1:
                return context.getString(R.string.thrill).toUpperCase(l);
            case 2:
                return context.getString(R.string.comedy).toUpperCase(l);
        }
        return null;
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
                filterResults.values = PlaylistFragment.videoList;
                filterResults.count = PlaylistFragment.videoList.size();
            } else {
                ArrayList<Video> filteredVideos = new ArrayList<>();
                for (Video video : PlaylistFragment.videoList) {
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
                PlaylistFragment.videoList = (ArrayList<Video>) results.values;
                notifyDataSetChanged();
            }

        }
    }

}