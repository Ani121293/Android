package com.example.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import static android.view.View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE;
import static com.example.disney.videoApp.FilmGenre.*;

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
        FilmGenre.toggle.setDrawerIndicatorEnabled(false);
        FilmGenre.toggle.setHomeAsUpIndicator(R.drawable.ic_back_btn);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        TextView videoName = (TextView) view.findViewById(R.id.video_title);
        videoName.setText(video.getmVideoName());
        ImageView videoImage = (ImageView) view.findViewById(R.id.video_image);
        videoImage.setImageResource(video.getmImageId());
        TextView descriptionTextView = (TextView) view.findViewById(R.id.detailed_info);
        descriptionTextView.setText(video.getmDescription());
        return view;
    }

        @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            menu.findItem(R.id.action_search).setVisible(false).setEnabled(false);
            menu.setGroupVisible(R.id.main_menu_group, false);
        }
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        System.out.println("-----------------------onOptionsItemSelected");
//        if (FilmGenre.toggle.isDrawerIndicatorEnabled() &&
//                FilmGenre.toggle.onOptionsItemSelected(item)) {
//            return true;
//        } else if (item.getItemId() == android.R.id.home &&
//                getActivity().getSupportFragmentManager().popBackStackImmediate()) {
//            System.out.println("--------------MENU Item Selected");
//            getActivity().onBackPressed();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentVideo", video);
    }
}
