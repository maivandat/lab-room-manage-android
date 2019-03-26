package huedev.org.ui.computer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.data.repository.ComputerRepository;
import huedev.org.data.source.local.ComputerLocalDataSource;
import huedev.org.data.source.remote.ComputerRemoteDataSource;
import huedev.org.ui.adapter.ComputerAdapter;
import huedev.org.ui.base.BaseActivity;
import huedev.org.utils.rx.BaseSchedulerProvider;
import huedev.org.utils.rx.SchedulerProvider;


public class ComputerActivity extends BaseActivity implements ComputerContract.View{

    ComputerContract.Presenter mComputerPresenter;
    RecyclerView mRvComputer;
    ComputerAdapter mComputerAdapter;

    Toolbar tbComputer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);
        tbComputer = findViewById(R.id.toolbar_computer);
        mRvComputer = findViewById(R.id.rv_computer);
        tbComputer.setTitle("");
        setSupportActionBar(tbComputer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tbComputer.setNavigationIcon(R.drawable.back);

        init();
        tbComputer.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        mComputerPresenter.computersByRoom(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_computertop, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_computerReset){

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
        mComputerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIndicator() {
        Toast.makeText(this, "Xin chào", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingIndicator() {
        Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(Throwable throwable) {
        Toast.makeText(this, "Lỗi!\n" +
                "" + throwable.toString(), Toast.LENGTH_SHORT).show();
    }

}
