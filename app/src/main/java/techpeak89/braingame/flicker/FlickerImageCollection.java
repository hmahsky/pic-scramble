package techpeak89.braingame.flicker;

import java.util.ArrayList;

import techpeak89.braingame.model.IImageCollection;
import techpeak89.braingame.model.ImageItem;

/**
 * Created by root on 30/11/16.
 * This class is mapped with Flicker JSon object & created for parsing the response
 */

class FlickerImageCollection implements IImageCollection {

    public ArrayList<FlickerImageItem> photo;

    public FlickerImageCollection(){
        photo = new ArrayList<FlickerImageItem>();
    }

    @Override
    public ArrayList<? extends ImageItem> list(){
        return photo;
    }

}