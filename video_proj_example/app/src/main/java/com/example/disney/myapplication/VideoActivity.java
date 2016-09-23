package com.example.disney.myapplication;


import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fragments.DetailsFragment;
import com.example.fragments.ListFragment;
import com.example.test.video_proj_example.R;

import static android.graphics.Color.rgb;


public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    static final String LIST_FRAGMENT = "list fragment";
    static final String DETAILS_FRAGMENT = "details fragment";
    static final String key = "lastSinglePaneFragment";
    public static final String PREFERENCE = "com.example.disney.myapplication.VideoActivity.PREFERENCE";
    boolean isDetailsOPened = false;
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setRippleColor(rgb(255, 163, 26));
        fab.setOnClickListener(this);
        boolean mDualPane = findViewById(R.id.dual_pane) != null;
        if (savedInstanceState != null) {
            isDetailsOPened = isDetailsOpened();
        }
        if (!mDualPane && fm.findFragmentById(R.id.fragment_container) == null) {
            if (isDetailsOPened) {
                openSinglePaneDetailFragment();
            } else {
                ListFragment singleListFragment = getDetatchedMasterFragment(false);
                fm.beginTransaction().add(R.id.fragment_container, singleListFragment, LIST_FRAGMENT)
                        .addToBackStack(LIST_FRAGMENT).commit();
            }
        }
        if (mDualPane && fm.findFragmentById(R.id.list_container) == null) {
            ListFragment listFragment = getDetatchedMasterFragment(true);
            fm.beginTransaction().add(R.id.list_container, listFragment, LIST_FRAGMENT).
                    addToBackStack(LIST_FRAGMENT).commit();
        }
        if (mDualPane && fm.findFragmentById(R.id.details_container) == null) {
            DetailsFragment detailFragment = getDetatchedDetailFragment();
            fm.beginTransaction().add(R.id.details_container, detailFragment, DETAILS_FRAGMENT).
                    addToBackStack(DETAILS_FRAGMENT).commit();
        }
    }

    public boolean isDetailsOpened() {
        return getSharedPreferences(PREFERENCE, MODE_PRIVATE).getBoolean(key, false);
    }


    private ListFragment getDetatchedMasterFragment(boolean popBackStack) {
        System.out.println("--------- List FRAGMENT " + fm.getBackStackEntryCount() + " --- " + fm.getFragments());
        ListFragment masterFragment = (ListFragment) fm.findFragmentByTag(LIST_FRAGMENT);
        if (masterFragment == null) {
            masterFragment = new ListFragment();
        } else {
            if (popBackStack) {
                System.out.println("--------- BEFORE POP LIST " + fm.getBackStackEntryCount());
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                System.out.println("---------AFTER POP " + fm.getBackStackEntryCount());
            }
            fm.beginTransaction().remove(masterFragment).commit();
            fm.executePendingTransactions();
        }
        return masterFragment;
    }

    private DetailsFragment getDetatchedDetailFragment() {
        System.out.println("--------- DETAILS FRAGMENT " + fm.getBackStackEntryCount()+ " --- " + fm.getFragments());
        DetailsFragment detailFragment = (DetailsFragment) fm.findFragmentByTag(DETAILS_FRAGMENT);
        if (detailFragment == null) {
            detailFragment = new DetailsFragment(new Video("", 0, "", 0));
        } else {
            fm.beginTransaction().remove(detailFragment).commit();
            fm.executePendingTransactions();
        }
        return detailFragment;
    }

    private void openSinglePaneDetailFragment() {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        DetailsFragment detailFragment = getDetatchedDetailFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment, DETAILS_FRAGMENT);
        fragmentTransaction.commit();
        ((FloatingActionButton)findViewById(R.id.fab)).hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_video, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search your lovely video");


// Listener for the query in search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
// Reload listView corresponding to filtering of query
                VideoAdapter listAdapter = ((ListFragment) getSupportFragmentManager().
                        findFragmentByTag(LIST_FRAGMENT)).getAdapter();
                listAdapter.getFilter().filter(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        System.out.println("--------- BACK PRESSED " + fm.getBackStackEntryCount() + " --- " + fm.getFragments());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                System.out.println("--------- FINISH " + fm.getBackStackEntryCount() + " --- " + fm.getFragments());
                finish();
            } else {
                System.out.println("--------- BACK TO LIST " + fm.getBackStackEntryCount()+ " --- " + fm.getFragments());
                ListFragment singleListFragment = getDetatchedMasterFragment(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        singleListFragment, LIST_FRAGMENT).addToBackStack(LIST_FRAGMENT).commit();
                ((FloatingActionButton)findViewById(R.id.fab)).show();
            }
        } else {
            System.out.println("-------BACK SUPER " + fm.getBackStackEntryCount()+ " --- " + fm.getFragments());
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                ((ListFragment) fm.findFragmentByTag(LIST_FRAGMENT)).addNewVideo();
                break;
            default:
                Toast.makeText(this, "Yor click is crashed",Toast.LENGTH_LONG).show();
        }
    }
}


