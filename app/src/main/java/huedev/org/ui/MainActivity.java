package huedev.org.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import huedev.org.R;
import huedev.org.ui.adapter.ViewPagerAdapter;
import huedev.org.ui.base.BaseActivity;
import huedev.org.ui.fragments.room.RoomFragment;

public class MainActivity extends BaseActivity {
    Toolbar mToolbar;
    ViewPager mViewPagerMain;
    ViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar_main);
        mViewPagerMain = findViewById(R.id.viewPager_main);
        mToolbar.setTitle("");
        mViewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());
        setSupportActionBar(mToolbar);

        mViewPagerAdapter.addFragment(new RoomFragment(), "Home");
        mViewPagerMain.setAdapter(mViewPagerAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maintop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menubt_home:
                    mToolbar.setTitle("Home");
                    mViewPagerAdapter.addFragment(new RoomFragment(), "Home");
                    mViewPagerMain.setAdapter(mViewPagerAdapter);
                    return true;
                case R.id.menubt_calendar:
                    mToolbar.setTitle("Calendar");
                    return true;
                case R.id.menubt_feeds:
                    mToolbar.setTitle("Feeds");
                    return true;
                case R.id.menubt_messenger:
                    mToolbar.setTitle("Messenger");
                    return true;
            }
            return false;
        }
    };
}
