package org.web.carritodecompras.Services;

import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.Photo;

public class PhotoService extends DataBaseRepository<Photo> {

    private static PhotoService photoService;

    public PhotoService() {
        super(Photo.class);
    }

    public static PhotoService getInstance(){
        if(photoService == null){
            photoService = new PhotoService();
        }
        return photoService;
    }

}
