package techpeak89.braingame.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import techpeak89.braingame.R;
import techpeak89.braingame.util.ImageUriBinderInterfaceImpl;


public class ImageGridViewAdapter extends ArrayAdapter {

    private Activity activityContext;
    private int layoutResourceId;
    private ArrayList<? extends ImageItem> imageList;

    private boolean isPreviewRunning = true;

    public ImageGridViewAdapter(Activity context, int layoutResourceId, ArrayList<? extends ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.activityContext = context;
        this.imageList = data;

    }

    public void setPreviewState(boolean isPreviewRunning){
        this.isPreviewRunning = isPreviewRunning;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) activityContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.previewImage = (ImageView) row.findViewById(R.id.previewImage);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if(isPreviewRunning || imageList.get(position).mIsFoundByUser) {
            holder.image.setVisibility(View.VISIBLE);
            holder.previewImage.setVisibility(View.GONE);

            ImageUriBinderInterfaceImpl.bindImageViewWithRemoteUri(activityContext,
                    imageList.get(position).mUrl, R.drawable.imagebg,holder.image);

/*
            Glide.with(activityContext).
                    load(imageList.
                            get(position).mUrl).
                    placeholder(R.drawable.imagebg).
                    centerCrop().
                    into(holder.image);
*/

        }else {

            holder.image.setVisibility(View.GONE);
            holder.previewImage.setVisibility(View.VISIBLE);

        }


        return row;
    }

    static class ViewHolder {
        String id;
        TextView imageTitle;
        TextView desig;
        TextView company;
        ImageView image;
        ImageView previewImage;
    }


}


