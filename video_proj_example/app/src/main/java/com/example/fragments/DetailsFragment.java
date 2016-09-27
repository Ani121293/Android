package com.example.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disney.videoApp.FilmGenre;
import com.example.disney.videoApp.Video;
import com.example.test.video_proj_example.R;

public class DetailsFragment extends Fragment {
    private Video video;

    public DetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public DetailsFragment(Video video) {
        this.video = video;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            video = (Video) savedInstanceState.getSerializable("currentVideo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((FilmGenre) getActivity()).getSupportActionBar().setTitle("Details");
        ((FilmGenre) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((FilmGenre) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        TextView videoName = (TextView) view.findViewById(R.id.video_title);
        videoName.setText(video.getmVideoName());
        ImageView videoImage = (ImageView) view.findViewById(R.id.video_image);
        videoImage.setImageResource(video.getmImageId());
        TextView descriptionTextView = (TextView) view.findViewById(R.id.detailed_info);
        descriptionTextView.setText(video.getmDescription());
        return view;
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
//            menu.findItem(R.id.action_search).setVisible(false).setEnabled(false);
//            menu.setGroupVisible(R.id.main_menu_group, false);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getActivity().onBackPressed();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentVideo", video);
    }
}
