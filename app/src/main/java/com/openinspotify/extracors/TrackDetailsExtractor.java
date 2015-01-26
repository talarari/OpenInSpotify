package com.openinspotify.extracors;

import com.openinspotify.FailedToExtractTrackDetailsException;

/**
 * Created by Tal on 1/27/2015.
 */
public interface TrackDetailsExtractor {

    public TrackDetails extract(String trackShareText) throws FailedToExtractTrackDetailsException;
}
