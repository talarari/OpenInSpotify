package com.openinspotify.extracors;

import com.openinspotify.FailedToExtractTrackDetailsException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tal on 1/27/2015.
 */
public class ShazamExtractor implements TrackDetailsExtractor {
    @Override
    public TrackDetails extract(final String trackShareText) throws FailedToExtractTrackDetailsException {
        return new TrackDetails(){{
            TrackName = extractFromString("(?<=to discover )(.*)(?= by)",trackShareText);
            ArtistName = extractFromString("(?<= by)(.*)(?=. http)",trackShareText);
        }};
    }

    private String extractFromString(String regex,String target) throws FailedToExtractTrackDetailsException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        if (!matcher.find()) throw new FailedToExtractTrackDetailsException();

        return matcher.group(1);
    }
}
