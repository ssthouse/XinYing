package ssthouse.love.xinying.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import ssthouse.love.xinying.R;

public class MainActivity extends AppCompatActivity {

    //UI
    @Bind(R.id.id_tb)
    Toolbar toolbar;


    //Fragments
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("love you so muc( ⊙ _ ⊙ )");
    }


    private void initFragment() {
        mainFragment = new MainFragment();

        //填充当前fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_container, mainFragment)
                .commit();
    }
}
