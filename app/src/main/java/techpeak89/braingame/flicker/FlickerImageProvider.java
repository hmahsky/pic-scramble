package techpeak89.braingame.flicker;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import techpeak89.braingame.model.IImageCollection;
import techpeak89.braingame.provider.IImageProvider;

/**
 * Created by root on 29/11/16.
 */

public class FlickerImageProvider implements IImageProvider {

    // This is the fixed end point for flicker
    private final String IMG_COLLECTION_URL = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4bb31a435e3f30aaaa977da415a6374d&per_page=9&page=1&format=json&nojsoncallback=1";

    OkHttpClient mHttpClient;

    /**
     *
     * @param client - This should be changed to interface later to adopt any other 3rd party http client
     *               Also having passed interface in constructor will improve code coverage & able to mock http response
     */
    public FlickerImageProvider(OkHttpClient client){
        mHttpClient = client;
    }

    @Override
    public IImageCollection fetchImages() throws IOException {

        Request request = new Request.Builder()
                .url(IMG_COLLECTION_URL)
                .build();

        Response response = mHttpClient.newCall(request).execute();
        InputStream ip =  response.body().byteStream();
        FlickerCollectionHolder imageCollectionHolder = parse(new InputStreamReader(ip));
        deriveImageUrl(imageCollectionHolder);

        return imageCollectionHolder.photos;

    }

    private FlickerCollectionHolder parse(InputStreamReader is) {
        Gson gson = new Gson();
        return gson.fromJson(is, FlickerCollectionHolder.class);
    }

    private void deriveImageUrl(FlickerCollectionHolder imageCollectionHolder){

        /*
         Sample Image URL formatting for deriving individual images from the collected images (meta data)
         https://farm6.staticflickr.com//5534//30474898754_0d704c61a1_s.jpg*/

        for (int i = 0; i < imageCollectionHolder.photos.photo.size(); i++) {
            FlickerImageItem item = imageCollectionHolder.photos.photo.get(i);
            item.mUrl = String.format("https://farm%s.staticflickr.com/%s/%s_%s_s.jpg", item.farm, item.server,item.id,item.secret);
        }
    }


}
