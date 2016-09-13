package com.example.disney.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.test.video_proj_example.R;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> implements Filterable {

    private ArrayList<Video> videos;
    private final Context context;
    private final FragmentManager manager;
    private VideoFilter filter;

    public VideoAdapter(Activity context, int resource, ArrayList<Video> videos, FragmentManager manager) {
        super(context, resource, videos);
        this.videos = videos;
        this.context = context;
        this.manager = manager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        }
        TextView videoName = (TextView) convertView.findViewById(R.id.video_name);
        videoName.setText(videos.get(position).getmVideoName());

        TextView videoNumber = (TextView) convertView.findViewById(R.id.video_number);
        videoNumber.setText(String.valueOf(videos.get(position).getmNumber()));
        return convertView;
    }

    @Override
    public int getCount() {
        return videos != null? videos.size() : 0;
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
            if (0 == results.count) {
                notifyDataSetInvalidated();
            } else {
                videos = (ArrayList<Video>) results.values;
                notifyDataSetChanged();
            }

        }
    }

}
