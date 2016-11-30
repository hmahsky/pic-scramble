package techpeak89.braingame.flicker;

import techpeak89.braingame.model.ImageItem;

/**
 * Created by root on 30/11/16.
 * This class is mapped with Flicker JSon object & created for parsing the response
 */

class FlickerImageItem extends ImageItem {

    public String id;
    public String secret;
    public String server;
    public String farm;

    public FlickerImageItem(){
        super();
    }
}