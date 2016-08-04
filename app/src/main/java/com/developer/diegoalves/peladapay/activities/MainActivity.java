package com.developer.diegoalves.peladapay.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.adapters.PlayerAdapter;
import com.developer.diegoalves.peladapay.adapters.clickAdapter.RecyclerViewOnClickListener;
import com.developer.diegoalves.peladapay.database.PlayerRepository;
import com.developer.diegoalves.peladapay.database.ValuesRepository;
import com.developer.diegoalves.peladapay.entities.Player;
import com.developer.diegoalves.peladapay.entities.Values;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActionMode.Callback,
        RecyclerViewOnClickListener {

    RecyclerView recyclerView;
    PlayerRepository repository;
    ValuesRepository valuesRepository;
    List<Player> mPlayers;
    PlayerAdapter adapter;
    ActionMode actionMode;
    FloatingActionButton fab;
    Toolbar toolbar;
    // GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, RegisterActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                ActivityCompat.startActivity(MainActivity.this, it, optionsCompat.toBundle());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, recyclerView, this));
        repository = new PlayerRepository(this);
        mPlayers = repository.listAll();
        adapter = new PlayerAdapter(this, mPlayers /*onClickPlayer()*/);
        recyclerView.setAdapter(adapter);
        //gestureDetector = new GestureDetector(this, new RecyclerViewTouchListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayers = repository.listAll();
        adapter = new PlayerAdapter(this, mPlayers);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void myToggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();
        String title;
        if (count > 1) {
            title = getString(R.string.selected_many, count);
        } else {
            title = getString(R.string.selected_one, count);
        }
        actionMode.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final Values values = new Values();
        valuesRepository = new ValuesRepository(this);

        if (id == R.id.reload) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getText(R.string.titleReset));
            dialog.setMessage(R.string.askReset);
            dialog.setNegativeButton(R.string.notConfirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPlayers = repository.listAll();
                    for(Player p : mPlayers) {
                        if(p.getIsPaid() == 1) {
                            p.setIsPaid(0);
                            repository.insertAndUpdate(p);
                        }
                    }
                    values.setCurrent(0.0);
                    values.setValueP(0.0);
                    values.setValueM(0.0);
                    valuesRepository.insertAndUpdate(values);
                    mPlayers = repository.listAll();
                    adapter = new PlayerAdapter(MainActivity.this, mPlayers);
                    recyclerView.setAdapter(adapter);
                }
            });
            dialog.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawers();
        } else if (id == R.id.nav_values) {
            startActivity(new Intent(this, TotalActivity.class));
        } else if(id == R.id.values_def) {
            startActivity(new Intent(this, ValuesActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.grey_status));
        }

        this.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        //   toolbar.setVisibility(View.GONE);
        //getSupportActionBar().hide();
        fab.setVisibility(View.GONE);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.ic_delete) {
            List<Integer> selectedItems = adapter.getSelectedItems();
            repository = new PlayerRepository(this);
            int currentPosition;
            for (int i = selectedItems.size() - 1; i >= 0; i--) {
                currentPosition = selectedItems.get(i);
                repository.delete(mPlayers.get(currentPosition));
                mPlayers.remove(currentPosition);
                adapter = new PlayerAdapter(this, mPlayers);
                recyclerView.setAdapter(adapter);
            }
            actionMode.finish();
            mPlayers = repository.listAll();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        // toolbar.setVisibility(View.VISIBLE);

        this.actionMode = null;
        adapter.clearSelections();
        fab.setVisibility(View.VISIBLE);
        //getSupportActionBar().show();
    }

    @Override
    public void onClickListener(View view, int position) {

        if (view != null) {
            int id = view.getId();
            switch (id) {
                case R.id.container_list_item:
                    if (actionMode != null) {
                        myToggleSelection(position);
                        return;
                    } else {
                        Player player = mPlayers.get(position);
                        Intent it = new Intent(getBaseContext(), PlayerActivity.class);
                        it.putExtra("player", player);
                        startActivity(it);
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        if (actionMode != null) {
            return;
        }
        actionMode = startSupportActionMode(MainActivity.this);
        myToggleSelection(position);

    }

    private class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context context;
        private GestureDetector gestureDetector;
        private RecyclerViewOnClickListener recyclerViewOnClickListener;

        public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final RecyclerViewOnClickListener recyclerViewOnClickListener) {
            this.context = context;
            this.recyclerViewOnClickListener = recyclerViewOnClickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (view != null && recyclerViewOnClickListener != null) {
                        recyclerViewOnClickListener.onLongPressClickListener(view, recyclerView.getChildAdapterPosition(view));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    onClickListener(view, recyclerView.getChildAdapterPosition(view));
                    return super.onSingleTapConfirmed(e);
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
