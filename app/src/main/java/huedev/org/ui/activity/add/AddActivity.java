package huedev.org.ui.activity.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import huedev.org.R;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.adapter.ViewPagerAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.ui.fragments.computer.create.CCreateFragment;
import huedev.org.ui.fragments.device.create.DCreateFragment;
import huedev.org.ui.fragments.room.create.RCreateFragment;
import huedev.org.ui.fragments.tag.create.TCreateFragment;
import huedev.org.ui.fragments.type_device.create.TDCreateFragment;
import huedev.org.utils.navigator.Navigator;

public class AddActivity extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;
    TabLayout tabsAdd;
    ViewPager vpAdd;
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar_add);
        tabsAdd = findViewById(R.id.tabs_add);
        vpAdd = findViewById(R.id.vp_add);
        navigator = new Navigator(this);


        setupToolbar(toolbar, R.drawable.iv_logo_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupViewPager();
        tabsAdd.setupWithViewPager(vpAdd);
        toolbar.setNavigationOnClickListener(this);

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RCreateFragment(), "Room");
        adapter.addFragment(new CCreateFragment(), "Computer");
        adapter.addFragment(new DCreateFragment(), "Device");
        adapter.addFragment(new TDCreateFragment(), "Type Device");
        adapter.addFragment(new TCreateFragment(), "Tags");
        vpAdd.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        navigator.startActivity(MainActivity.class);
        finish();
    }
}
