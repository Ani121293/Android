package com.example.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.disney.myapplication.Video;
import com.example.disney.myapplication.VideoActivity;
import com.example.disney.myapplication.VideoAdapter;
import com.example.test.video_proj_example.R;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class ListFragment extends Fragment {

    public static ArrayList<Video> videoList;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private static boolean isLinear = true;
    private final String key = "currentVideoList";

    public VideoAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(VideoAdapter adapter) {
        this.adapter = adapter;
    }

    private VideoAdapter adapter = null;
    private Integer currentRowPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            videoList = (ArrayList<Video>) savedInstanceState.getSerializable(key);
//                Integer currentPosition = savedInstanceState.getInt("currentRowPosition");
//                if (0 != currentPosition) {
//                    currentRowPosition = currentPosition;
//            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ((VideoActivity) getActivity()).getSupportActionBar().setTitle("List");
        ((VideoActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((VideoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (videoList == null) {
            videoList = new ArrayList<>();
            for (int i = 0; i < 10; ++i) {
                videoList.add(new Video("Video", i, "Here will be the decription of video", R.mipmap.ic_video_image));
            }
            for (int j = 10; j < 17; ++j) {
                videoList.add(new Video("Prestige", j, "Here will be the decription of video", R.mipmap.ic_video_image));
            }
        }
        view = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new VideoAdapter(this.getActivity(), R.layout.list_fragment, videoList, getFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setRippleColor(rgb(255, 163, 26));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add a video to videolist");
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.add_video_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = ((EditText) layout.findViewById(R.id.videoNameAlert)).getText().toString();
                        String description = ((EditText) layout.findViewById(R.id.videoDescAlert)).getText().toString();
                        videoList.add(new Video(name, videoList.size(), description, R.mipmap.ic_video_image));
                        adapter.notifyItemInserted(videoList.size());
                        adapter.notifyDataSetChanged();
//                        adapter.reloadVideo(videoList);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        initSwipe();
        return view;
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                videoList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
//                adapter.reloadVideo(videoList);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLinear) {
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            m.scrollToPosition(currentRowPosition);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeView:
                if (recyclerView.getLayoutManager().getClass().equals(linearLayoutManager.getClass())) {
                    isLinear = false;
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                } else {
                    isLinear = true;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(key, videoList);
//        outState.putInt("currentRowPosition",adapter.currentPosition);
    }
}

