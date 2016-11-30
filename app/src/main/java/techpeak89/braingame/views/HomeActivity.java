package techpeak89.braingame.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import techpeak89.braingame.R;
import techpeak89.braingame.model.IImageCollection;


/**
 * Created by root on 30/11/16.
 */

public class HomeActivity extends AppCompatActivity implements DashBoardFragment.DashBoardFragmentInterface, GameFragment.GameFragmentInterface{

    IImageCollection mImageCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addDashBoard();
    }

    @Override
    public void playGame(){

        // Yet to download
        if(mImageCollection == null) {
            //TODO : remvoe Hardcoded string
            Toast.makeText(this, "Please wait images are being downloaded", Toast.LENGTH_SHORT).show();
            return;
        }

        Fragment fragmentGame = new GameFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentGame);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public IImageCollection getCollection() {
        return mImageCollection;
    }

    @Override
    public void setCollection(IImageCollection imageCollection) {
        mImageCollection = imageCollection;
    }

    @Override
    public void gameFinished() {
        addDashBoard();
    }

    public void addDashBoard(){
        Fragment fragmentGame = new DashBoardFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentGame);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
