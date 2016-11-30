package techpeak89.braingame.provider;

import java.io.IOException;

import techpeak89.braingame.model.IImageCollection;

/**
 * Created by root on 30/11/16.
 */

public interface IImageProvider {
    public IImageCollection fetchImages() throws IOException;
}
