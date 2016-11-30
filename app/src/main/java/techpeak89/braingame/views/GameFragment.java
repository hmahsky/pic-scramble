package techpeak89.braingame.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.Random;

import techpeak89.braingame.R;
import techpeak89.braingame.model.IImageCollection;
import techpeak89.braingame.model.ImageGridViewAdapter;
import techpeak89.braingame.model.ImageItem;
import techpeak89.braingame.util.ImageUriBinderInterfaceImpl;

import static techpeak89.braingame.views.DashBoardFragment.GRID_IMAGE_COUNT;


/**
 * Created by root on 30/11/16.
 */

public class GameFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GameFragmentInterface mGameFragmentInterface;
    private GridView gridView;
    private ImageGridViewAdapter gridAdapter;
    private Handler myHandler = new Handler();

    private  int[] mRemainingImages;
    private  int mCurrentImage;

    private  ImageView mRandomImage;
    private TextView mPreviewNote;
    private  Runnable mRunnable;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game,container,false);
        gridView = (GridView) view .findViewById(R.id.imageGridView);
        mRandomImage = (ImageView)view .findViewById(R.id.randomImage);
        gridAdapter = new ImageGridViewAdapter(getActivity(), R.layout.image_gridview_item, mGameFragmentInterface.getCollection().list());
        mPreviewNote = (TextView) view .findViewById(R.id.previewNote);
        gridView.setAdapter(gridAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Collections.shuffle(mGameFragmentInterface.getCollection().list());
        initBeforePlayingGame();
        playGame();
    }

    private void initBeforePlayingGame(){

        mRemainingImages = new int [GRID_IMAGE_COUNT];
        for(int i = 0; i<GRID_IMAGE_COUNT; i++) {
            mRemainingImages[i] = 0;
            ((ImageItem)gridAdapter.getItem(i)).mIsFoundByUser = false;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGameFragmentInterface = (GameFragmentInterface)context;
    }

    private void playGame(){

        gridView.setVisibility(View.VISIBLE);
        gridAdapter.setPreviewState(true);
        gridAdapter.notifyDataSetChanged();

        mCurrentImage = -1;

        mRunnable = new Runnable() {
            @Override
            public void run() {
                chooseRandomImage();
                gridAdapter.setPreviewState(false);
                gridAdapter.notifyDataSetChanged();
                mPreviewNote.setVisibility(View.GONE);

                gridView.setOnItemClickListener(GameFragment.this);

                ImageItem item = (ImageItem)gridAdapter.getItem(mCurrentImage);

                if(getActivity() != null && !getActivity().isFinishing()) {

                    ImageUriBinderInterfaceImpl.bindImageViewWithRemoteUri(getActivity(),
                            item.mUrl, R.drawable.imagebg,mRandomImage);

/*
                    Glide.with(getActivity()).
                            load(item.mUrl).
                            placeholder(R.drawable.imagebg).centerCrop().
                            into(mRandomImage);
*/
                }


            }
        };

        myHandler.postDelayed(mRunnable, 15000);

    }


    private void refreshRemainingImageHolder(){
        mRemainingImages = new int[mRemainingImages.length - 1];
        for(int k = 0,m=0; k<GRID_IMAGE_COUNT; k++) {
            if(!((ImageItem)gridAdapter.getItem(k)).mIsFoundByUser)
                mRemainingImages[m++] = k;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ImageItem item = (ImageItem)gridAdapter.getItem(i);
        View image = (ImageView) view.findViewById(R.id.image);
        View imageBg = (ImageView) view.findViewById(R.id.previewImage);


        if(i == mCurrentImage) {

            item.mIsFoundByUser = true;

            refreshRemainingImageHolder();

            image.setVisibility(View.VISIBLE);
            imageBg.setVisibility(View.GONE);

            if(mRemainingImages.length == 0) {

                Toast.makeText(getActivity(),"Done", Toast.LENGTH_SHORT).show();
                mGameFragmentInterface.gameFinished();

            }else{

                chooseRandomImage();

                item = (ImageItem)gridAdapter.getItem(mCurrentImage);

                Glide.with(getActivity()).
                        load(item.mUrl).
                        placeholder(R.drawable.imagebg).centerCrop().
                        into(mRandomImage);

            }

        }

    }

    private void chooseRandomImage(){
        Random mRandomGen = new Random();
        mCurrentImage = mRemainingImages[mRandomGen.nextInt(mRemainingImages.length)];
        mRandomImage.setVisibility(View.VISIBLE);
    }

    public interface GameFragmentInterface {
        public IImageCollection getCollection();
        public void gameFinished();
    }



}
