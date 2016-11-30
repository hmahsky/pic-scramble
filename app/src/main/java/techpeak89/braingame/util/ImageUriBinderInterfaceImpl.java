package techpeak89.braingame.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by root on 30/11/16.
 */

public class ImageUriBinderInterfaceImpl {

    public static void bindImageViewWithRemoteUri(Context context, String uri, int placeHolderId, ImageView imageView) {

        Glide.with(context).
                load(uri).
                placeholder(placeHolderId).centerCrop().
                into(imageView);
    }
}
