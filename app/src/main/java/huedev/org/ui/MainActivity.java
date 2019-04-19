package huedev.org.ui;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.sql.DataSource;

import huedev.org.R;
import huedev.org.data.model.Room;
import huedev.org.data.model.User;
import huedev.org.data.repository.RoomRepository;
import huedev.org.data.repository.UserRepository;
import huedev.org.data.source.RoomDataSource;
import huedev.org.data.source.local.RoomLocalDataSource;
import huedev.org.data.source.local.UserLocalDataSource;
import huedev.org.data.source.remote.RoomRemoteDataSource;
import huedev.org.data.source.remote.UserRemoteDataSource;
import huedev.org.ui.auth.login.LoginActivity;
import huedev.org.ui.auth.login.LoginPresenter;
import huedev.org.ui.auth.logout.LogoutContact;
import huedev.org.ui.auth.logout.LogoutPresenter;
import huedev.org.ui.base.BaseActivity;
import huedev.org.ui.fragments.MessengerFragment.MessengerFragment;
import huedev.org.ui.fragments.calendar.CalendarFragment;
import huedev.org.ui.fragments.feed.FeedFragment;
import huedev.org.ui.fragments.room.RoomContract;
import huedev.org.ui.fragments.room.RoomFragment;
import huedev.org.ui.fragments.room.RoomPresenter;
import huedev.org.ui.fragments.room.create.CRoomContact;
import huedev.org.ui.fragments.room.create.CRoomPresenter;
import huedev.org.ui.user.edit.UEditActivity;
import huedev.org.ui.user.edit.UEditContact;
import huedev.org.ui.user.edit.UEditPresenter;
import huedev.org.utils.AppConstants;
import huedev.org.utils.AppPrefs;
import huedev.org.utils.helpers.StringHelper;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class MainActivity extends BaseActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        LogoutContact.View, CRoomContact.View, UEditContact.View {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    BottomNavigationView mBtNavigation;
    Toolbar mToolbar;
    Navigator navigator;
    LinearLayout linearLayout;
    TextView tvNameUSer, tvPosition;
    LogoutPresenter mLogoutPresenter;
    EditText etNameRoom, edtDescRoom, etOldPassword, etNewPassword, etConfirmNewPassword;
    RadioButton rbActive, rbRepair, rbBroken;
    Dialog dialog;
    Button btnCancelAddRoom, btnAddRoom, btnConfirmChangePassword, btnCancelChangePassword;

    CRoomPresenter mCRoomPresenter;
    UEditPresenter mUEditPresenter;

    String name = "", role = "";
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.dl_main);
        mToolbar = findViewById(R.id.toolbar_main);
        linearLayout = findViewById(R.id.linear_container);
        mBtNavigation = findViewById(R.id.navigation_main);
        View headerLayout = mNavigationView.getHeaderView(0);
        tvNameUSer = headerLayout.findViewById(R.id.tv_nameUser);
        tvPosition = headerLayout.findViewById(R.id.tv_position);
        navigator = new Navigator(this);
        init();

        mToolbar.setTitle("");
        setupToolbar(mToolbar, R.drawable.btn_menu);

        setNameUser();

        setVisibleItemNavigation();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linear_container ,new RoomFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        mToolbar.setNavigationOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mBtNavigation.setOnNavigationItemSelectedListener(this);

    }

    public void setNameUser(){
        if (!AppPrefs.getInstance(this).getNameUser().isEmpty() && AppPrefs.getInstance(this).getRole() > -1){
            name = AppPrefs.getInstance(this).getNameUser();
            role = StringHelper.formatStringRole(AppPrefs.getInstance(this).getRole(), this);
        }else {
            name = StringHelper.getStringResourceByName("admin", this);
            role = StringHelper.formatStringRole(2, this);
        }

        tvNameUSer.setText(name);
        tvPosition.setText(role);
    }

    private void setVisibleItemNavigation() {
        if (!AppPrefs.getInstance(this).getApiToken().equals(AppConstants.API_TOKEN_DEFAULT)){
            mNavigationView.getMenu().getItem(0).setVisible(true);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(true);
            mNavigationView.getMenu().getItem(3).setVisible(false);

        }else {
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(false);
            mNavigationView.getMenu().getItem(3).setVisible(true);
        }
    }

    private void init() {
        mLogoutPresenter = new LogoutPresenter(this);
        RoomRepository roomRepository = new RoomRepository(
                RoomLocalDataSource.getInstance(),
                RoomRemoteDataSource.getInstance(this));
        mCRoomPresenter = new CRoomPresenter(this, SchedulerProvider.getInstance(), roomRepository);
        UserRepository userRepository = UserRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance(this));
        mUEditPresenter = new UEditPresenter(this, userRepository, SchedulerProvider.getInstance());
        mLogoutPresenter.setView(this);
        mCRoomPresenter.setView(this);
        mUEditPresenter.setView((UEditContact.View) this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_hometop, menu);
        if (AppPrefs.getInstance(this).getRole() == 2){
            menu.getItem(0).setVisible(false);
        }else {
            menu.getItem(0).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_room:
                setupDialogAddRoom();
                break;
            case R.id.menu_notify:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDialogAddRoom() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_addroom);

        etNameRoom = dialog.findViewById(R.id.et_nameCreateUser);
        edtDescRoom = dialog.findViewById(R.id.et_descCreateUser);
        rbActive = dialog.findViewById(R.id.rb_active);
        rbRepair = dialog.findViewById(R.id.rb_repair);
        rbBroken = dialog.findViewById(R.id.rb_broken);
        btnAddRoom = dialog.findViewById(R.id.btn_add_room);
        btnCancelAddRoom = dialog.findViewById(R.id.btn_cancel_addroom);

        btnAddRoom.setOnClickListener(this);
        btnCancelAddRoom.setOnClickListener(this);
        dialog.show();

    }

    private void settupDialogChangePassword(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_password);

        etOldPassword = dialog.findViewById(R.id.et_old_password);
        etNewPassword = dialog.findViewById(R.id.et_new_password);
        etConfirmNewPassword = dialog.findViewById(R.id.et_confirm_new_password);
        btnConfirmChangePassword = dialog.findViewById(R.id.btn_confirm_change_password);
        btnCancelChangePassword = dialog.findViewById(R.id.btn_cancel_change_password);

        btnConfirmChangePassword.setOnClickListener(this);
        btnCancelChangePassword.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_bt_home:
                replaceFragment(new RoomFragment());
                return true;
            case R.id.nav_bt_calendar:
                replaceFragment(new CalendarFragment());
                return true;
            case R.id.nav_bt_feeds:
                replaceFragment(new FeedFragment());
                return true;
            case R.id.nav_bt_messenger:
                replaceFragment(new MessengerFragment());
                return true;
            case R.id.nav_start_login:
                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();
                navigator.startActivity(LoginActivity.class);
                finish();
                return true;
            case R.id.nav_start_logout:
                mLogoutPresenter.logout();
                return true;
            case R.id.nav_start_editInformation:
                menuItem.setChecked(false);
                navigator.startActivity(UEditActivity.class);
                mDrawerLayout.closeDrawers();
                return true;

            case R.id.mav_start_changePassword:
                menuItem.setChecked(false);
                settupDialogChangePassword();
                mDrawerLayout.closeDrawers();
                return true;

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_room:
                String name = etNameRoom.getText().toString().trim();
                String desc = edtDescRoom.getText().toString().trim();
                if (rbActive.isChecked()){
                    status = 0;
                }else if (rbRepair.isChecked()){
                    status = 1;
                }else {
                    status = 2;
                }
                String sStatus = StringHelper.formatStringStatus(status, this);
                mCRoomPresenter.createRoom(name, desc, sStatus);
                break;
            case R.id.btn_cancel_addroom:
                dialog.dismiss();
                break;
            case R.id.btn_confirm_change_password:
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmNewPassword= etConfirmNewPassword.getText().toString().trim();
                mUEditPresenter.updateUser(oldPassword, newPassword, confirmNewPassword);
                break;
            case R.id.btn_cancel_change_password:
                dialog.dismiss();
                break;
            default:
                mDrawerLayout.openDrawer(GravityCompat.START);
                setNameUser();
                break;
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linear_container ,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_left_in,
                R.anim.slide_left_out);
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoginError(Throwable throwable) {

    }

    @Override
    public void logout() {
        mDrawerLayout.closeDrawers();
        navigator.startActivity(MainActivity.class);
    }


    @Override
    public void logicCorrect(Room room) {
        Toast.makeText(this, "Thêm thành công phòng " + room.getName(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }

    @Override
    public void logicFaild() {
        Toast.makeText(this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logicSuccess() {
        Toast.makeText(this, "Thay đổi Password thành công", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void user(User user) {

    }
}
