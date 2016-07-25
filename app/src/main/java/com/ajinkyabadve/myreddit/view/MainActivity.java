package com.ajinkyabadve.myreddit.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ajinkyabadve.myreddit.R;
import com.ajinkyabadve.myreddit.model.Child;
import com.ajinkyabadve.myreddit.presenter.MainContract;
import com.ajinkyabadve.myreddit.presenter.MainPresenter;
import com.ajinkyabadve.myreddit.presenter.ReddItViewPagerAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, ViewPager.OnPageChangeListener {
    private MainContract.UserActionsListener mActionsListener;
    private ViewPager reddItViewPager;
    private CoordinatorLayout rootLayout;
    private ProgressBar progressBar;
    private boolean offlineFirstTime = false;
    private TextView textViewPaging;
    private List<Child> child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reddItViewPager = (ViewPager) findViewById(R.id.reddItViewPager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textViewPaging = (TextView) findViewById(R.id.textViewPaging);
        progressBar.setVisibility(View.INVISIBLE);
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        reddItViewPager.addOnPageChangeListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mActionsListener = new MainPresenter(MainActivity.this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showReddItPost(List<Child> child) {
        textViewPaging.setText(1 + " of " + child.size());

        this.child = child;
        ReddItViewPagerAdapter reddItViewPagerAdapter = new ReddItViewPagerAdapter(getSupportFragmentManager(), MainActivity.this, child);
        reddItViewPager.setAdapter(reddItViewPagerAdapter);
    }

    @Override
    public void NetworkNotAvailable() {
        offlineFirstTime = true;
        Snackbar.make(rootLayout, "No connection!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.this.receivedBroadcast(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(this.mBroadcastReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mBroadcastReceiver);
    }

    private void receivedBroadcast(Intent intent) {
        if (intent.getExtras() != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

            if (ni != null && ni.isConnectedOrConnecting()) {
                if (offlineFirstTime) {
                    offlineFirstTime = false;
                    Snackbar.make(rootLayout, "Connection available !", Snackbar.LENGTH_LONG)
                            .setAction("Go online", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mActionsListener.loadPosts();
                                }
                            }).setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent)).show();
                }

            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (child != null) {
            textViewPaging.setText((position + 1) + " of " + child.size());
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
