package techpeak89.braingame.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import okhttp3.OkHttpClient;
import techpeak89.braingame.R;
import techpeak89.braingame.flicker.FlickerImageProvider;
import techpeak89.braingame.model.IImageCollection;
import techpeak89.braingame.provider.IImageProvider;

/**
 * Created by root on 30/11/16.
 */

public class DashBoardFragment extends Fragment {

    public static final int GRID_IMAGE_COUNT = 9;

    DashBoardFragmentInterface mCollectionListener;

    Button mPlayButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        mPlayButton =  (Button)view.findViewById(R.id.playButton);

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCollectionListener.playGame();
            }
        });

        startContainerDownloading();

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCollectionListener = (DashBoardFragmentInterface)context;
    }

    public void startContainerDownloading(){
        ImageCollectionDownloader downloader = new ImageCollectionDownloader();
        downloader.execute();
    }

    private class ImageCollectionDownloader extends AsyncTask<String, Integer, IImageCollection> {

        protected IImageCollection doInBackground(String... urls) {
            IImageProvider imageProvider = new FlickerImageProvider(new OkHttpClient());
            IImageCollection imageCollection = null;
            try {
                imageCollection = imageProvider.fetchImages();
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }
            //publishProgress((int) ((i / (float) count) * 100));
            return imageCollection;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(IImageCollection collection) {
            mCollectionListener.setCollection(collection);;
        }

    }

    public interface DashBoardFragmentInterface {
        public IImageCollection getCollection();
        public void setCollection(IImageCollection collection);
        public void playGame();
    }


}
