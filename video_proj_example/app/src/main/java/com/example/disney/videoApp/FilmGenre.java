package com.example.disney.videoApp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.favorite.FavoriteRecyclerAdapter;
import com.example.fragments.FavoriteFragment;
import com.example.fragments.GenreFragment;
import com.example.test.video_proj_example.R;

public class FilmGenre extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GenrePagerAdapter mPagerAdapter;

    public static ViewPager mViewPager;
    NavigationView navigationView = null;
    public static final String FAVORIT_FRAGMENT = "favorite_fragment";
    public static final String DETAILS_FRAGMENT = "details_fragment";
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("PLAYLIST");
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mPagerAdapter = new GenrePagerAdapter(getSupportFragmentManager(), this);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mPagerAdapter);
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private FavoriteFragment getDetatchedMasterFragment(boolean popBackStack) {
        System.out.println("--------- List FRAGMENT " + fm.getBackStackEntryCount() + " --- " + fm.getFragments());
        FavoriteFragment masterFragment = (FavoriteFragment) fm.findFragmentByTag(FAVORIT_FRAGMENT);
        if (masterFragment == null) {
            masterFragment = new FavoriteFragment();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.playlist) {
            if (fm.findFragmentByTag(FAVORIT_FRAGMENT) != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag(FAVORIT_FRAGMENT)).commit();
            }
            this.mViewPager.setVisibility(View.VISIBLE);

        } else if (id == R.id.favorits) {
            this.mViewPager.setVisibility(View.INVISIBLE);
//            FavoriteFragment singleListFragment = getDetatchedMasterFragment(false);
            fm.beginTransaction().add(R.id.main_fragment_container, new FavoriteFragment(), FAVORIT_FRAGMENT)
                    .addToBackStack(FAVORIT_FRAGMENT).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_film_genre, menu);
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
                if (R.id.main_fragment_container != 0) {
                    FavoriteRecyclerAdapter listAdapter = ((FavoriteFragment) getSupportFragmentManager().
                            findFragmentByTag(DETAILS_FRAGMENT)).getAdapter();
                    listAdapter.getFilter().filter(query);
                } else {
                    mPagerAdapter.getFilter().filter(query);
                }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
