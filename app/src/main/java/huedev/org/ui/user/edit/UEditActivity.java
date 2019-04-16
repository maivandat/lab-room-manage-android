package huedev.org.ui.user.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import huedev.org.R;
import huedev.org.ui.MainActivity;
import huedev.org.ui.base.BaseActivity;
import huedev.org.utils.navigator.Navigator;

public class UEditActivity extends BaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    Navigator navigator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_user);
        navigator = new Navigator(this);
        toolbar = findViewById(R.id.toolbar_editUser);

        setupToolbar(toolbar, R.drawable.btn_back_white);

        toolbar.setNavigationOnClickListener(this);


    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        navigator.startActivity(MainActivity.class);
    }
}
