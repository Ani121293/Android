package com.example.disney.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragments.DetailsFragment;
import com.example.fragments.ListFragment;
import com.example.test.video_proj_example.R;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> implements Filterable {

    private ArrayList<Video> videos;
    private final Context context;
    private final FragmentManager manager;
    private VideoFilter filter;
    static final String key = "lastSinglePaneFragment";
    static final String DETAILS_FRAGMENT = "details fragment";


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Video currentVideo = videos.get(position);
        holder.setmNumber(String.valueOf(currentVideo.getmNumber()));
        holder.setmVideoName(currentVideo.getmVideoName());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailsFragment = new DetailsFragment(videos.get(position));
                Fragment listFragment = new ListFragment();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
                    fragmentTransaction.addToBackStack("details").replace(R.id.details_container,
                            detailsFragment, DETAILS_FRAGMENT);
                } else {
                    fragmentTransaction.addToBackStack("details").replace(R.id.fragment_container,
                            detailsFragment, DETAILS_FRAGMENT);
                }
                fragmentTransaction.commit();
                saveFragment();
            }
        });
    }

    private void saveFragment() {
        context.getSharedPreferences(VideoActivity.PREFERENCE, this.context.MODE_PRIVATE).edit()
                .putBoolean(key,true).commit();
    }

    @Override
    public int getItemCount() {
        return null != videos ? videos.size() : 0;
    }

    public VideoAdapter(Activity context, int resource, ArrayList<Video> videos, FragmentManager manager) {
        this.videos = videos;
        this.context = context;
        this.manager = manager;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private View row;
        private TextView mNumber;
        private TextView mVideoName;
        private ImageView mShowDetails;

        public ViewHolder(View row) {
            super(row);
            this.row = row;
            mNumber = (TextView) row.findViewById(R.id.video_number);
            mVideoName = (TextView) row.findViewById(R.id.video_name);
            mShowDetails = (ImageView) row.findViewById(R.id.show_details);
        }

        public TextView getmNumber() {
            return mNumber;
        }

        public void setmNumber(String number) {
            this.mNumber.setText(number);
        }

        public TextView getmVideoName() {
            return mVideoName;
        }

        public void setmVideoName(String videoName) {
            this.mVideoName.setText(videoName);
        }

        public ImageView getmShowDetails() {
            return mShowDetails;
        }

        public void setmShowDetails(ImageView mShowDetails) {
            this.mShowDetails = mShowDetails;
        }
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
