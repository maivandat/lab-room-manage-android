package huedev.org.ui.activity.computer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.ui.activity.main.MainActivity;
import huedev.org.ui.adapter.ComputerAdapter;
import huedev.org.ui.base.activity.BaseActivity;
import huedev.org.utils.navigator.Navigator;
import huedev.org.utils.rx.SchedulerProvider;


public class ComputerActivity extends BaseActivity implements ComputerContract.View, View.OnClickListener {

    ComputerContract.Presenter mComputerPresenter;
    RecyclerView mRvComputer;
    ComputerAdapter mComputerAdapter;
    Toolbar tbComputer;
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        navigator = new Navigator(this);
        tbComputer = findViewById(R.id.toolbar_computer);
        mRvComputer = findViewById(R.id.rv_computer);

        tbComputer.setTitle("");
        setSupportActionBar(tbComputer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tbComputer.setNavigationIcon(R.drawable.btn_back);

        init();
        tbComputer.setNavigationOnClickListener(this);
    }

    public void init(){
        ComputerRepository computerRepository =
                ComputerRepository.getInstance(ComputerLocalDataSource.getInstance()
                        , ComputerRemoteDataSource.getInstance(this));
        mComputerPresenter = new ComputerPresenter(
                this
                , computerRepository
                , SchedulerProvider.getInstance());
        mComputerPresenter.setView(this);
        mComputerPresenter.computersByRoom();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_computertop, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_computerReset){
            mComputerPresenter.computersByRoom();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateComputerList(List<Computer> computerList) {
        mComputerAdapter = new ComputerAdapter(this, computerList);

        mRvComputer.setAdapter(mComputerAdapter);
        GridLayoutManager manager = new GridLayoutManager(
                this,
                2,
                GridLayoutManager.VERTICAL,
                false);
        mRvComputer.setLayoutManager(manager);

    }

    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void hideLoadingIndicator() {
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Lỗi!\n" +
                "" + throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        navigator.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }
}
