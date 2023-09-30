package com.kasra.javaee.framework.web.mvc.utility;

import eu.medsea.mimeutil.MimeUtil2;


/**
 * Created by kasra.haghpanah on 08/04/2017.
 */
public class FileUtility {

    public static String getMimeType(String path) {

        MimeUtil2 mimeUtil = new MimeUtil2();
        mimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        String mimeType = MimeUtil2.getMostSpecificMimeType(mimeUtil.getMimeTypes(path)).toString();
        return mimeType;
    }

}
