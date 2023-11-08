package com.adhunchanchoc.sourcelink;

import org.springframework.stereotype.Service;

@Service
public class LinkService {
    //method to generate filename appendix
    static String makeUrlCompatible(String url) {
        final int MAX_LENGTH = 140;
        int prefix = 0, length = url.length();

        prefix += (url.contains("https://")) ? 8 : 7; //scheme
        prefix += (url.contains("/www")) ? 4 : 0;
        length -= prefix;
        length = (length > MAX_LENGTH) ?  140 : length;
        return url.substring(prefix, length+prefix).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
}
