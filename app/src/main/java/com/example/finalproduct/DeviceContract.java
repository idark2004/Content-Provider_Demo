package com.example.finalproduct;

import android.net.Uri;

public final class DeviceContract {
    // Standard pattern
    private DeviceContract() {}

    /* Anatomy of an URI: content://authority/path/id
        + "content://": the start of an URI
        + "authority" : the symbolic name of the entire provider (should be the package name)
        + "path"      : the virtual directory within the provider that acts as an identifier of the requested data
        + "id"        : optional, specifies the primary key of a record being requested */
    public static final String AUTHORITY = "com.demo.device.provider";
    public static final String CONTENT_PATH = "device";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+"/"+CONTENT_PATH);

    static final int DEVICE = 1;
    static final int DEVICE_ROW = 2;

    // MIME types for device content provider
    static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.com.example.provider.device";
    static final String MULTIPLE_RECORDS_MIME_TYPE = "vnd.android.cursor.dir/vnd.com.example.provider.device";

}
