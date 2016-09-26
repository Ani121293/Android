package com.example.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ListFragment extends Fragment {

    public static ArrayList<Video> videoList;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private static boolean isLinear = true;
    private final String key = "currentVideoList";
    private Paint p = new Paint();
    private LayoutInflater inflater;

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
            //starting to parse 'data.xml'
            XmlPullParser parser = getResources().getXml(R.xml.data);
            try {
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlPullParser.START_TAG:
                             if(parser.getAttributeCount() > 2) {
                                 videoList.add(new Video(parser.getAttributeValue(1),
                                         Integer.valueOf(parser.getAttributeValue(0)),
                                         parser.getAttributeValue(2), R.mipmap.ic_video_image));
                            }
                        default:
                            break;
                    }
                    parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.inflater = inflater;
        view = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new VideoAdapter(this.getActivity(), R.layout.list_fragment, videoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        initSwipe();
        return view;
    }

    public void addNewVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a video to videolist");
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.add_video_dialog, null);
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((EditText) layout.findViewById(R.id.videoNameAlert)).getText().toString();
                String description = ((EditText) layout.findViewById(R.id.videoDescAlert)).getText().toString();
                videoList.add(new Video(name, videoList.size() + 1, description, R.mipmap.ic_video_image));
                adapter.notifyItemInserted(videoList.size());
                adapter.notifyDataSetChanged();
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

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Confirmation");
                alert.setMessage("Are you sure you want to DELETE this record ?");
                alert.setIcon(android.R.drawable.ic_dialog_email);
                alert.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewHolder.getAdapterPosition();
                        videoList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    p.setColor(Color.RED);
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_delete);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() +
                            width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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

