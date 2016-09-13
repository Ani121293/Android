package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.disney.myapplication.Video;
import com.example.disney.myapplication.VideoActivity;
import com.example.disney.myapplication.VideoAdapter;
import com.example.test.video_proj_example.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    public static ArrayList<Video> videoList;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);


    public VideoAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(VideoAdapter adapter) {
        this.adapter = adapter;
    }

    private VideoAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((VideoActivity) getActivity()).getSupportActionBar().setTitle("List");
        ((VideoActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((VideoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        videoList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            videoList.add(new Video("Video", i, "Here will be the decription of video", R.mipmap.ic_video_image));
        }

        for (int j = 10; j < 17; ++j) {
            videoList.add(new Video("Prestige", j, "Here will be the decription of video", R.mipmap.ic_video_image));
        }

        view = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new VideoAdapter(this.getActivity(), R.layout.list_fragment, videoList, getFragmentManager());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeView:
                if (recyclerView.getLayoutManager().equals(linearLayoutManager)) {
                    recyclerView.setLayoutManager(gridLayoutManager);
                } else {
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

