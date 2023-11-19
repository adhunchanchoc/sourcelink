package com.adhunchanchoc.sourcelink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private final LinkRepository repository;
@Autowired
    public LinkService(LinkRepository linkRepository){
        this.repository = linkRepository;
    }

    /**
     * Method for URL String conversion to a String fulfilling naming requirements of files across multiple operating systems.
     *
     * @param url website URL consisting of characters that are either unreserved <code>A-Za-z0-9_.~-</code>
     *            or reserved <code>!*'();:@&=+$,/?#[]</code> including encoding percent character <code>%</code>.
     * @return A string that can be used as cross-platform compatible name. That means a string of max 140 characters,
     * allowing only ASCII alphanumerics with dots . and dashes -, substituting everything else by underscores _.
     */

    String makeUrlCompatible(String url) {
        final int MAX_LENGTH = 140;
        int prefix = 0, length = url.length();

        prefix += (url.contains("https://")) ? 8 : 7; //scheme
        prefix += (url.contains("/www")) ? 4 : 0;
        length -= prefix;
        length = (length > MAX_LENGTH) ? 140 : length;
        return url.substring(prefix, length + prefix).replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }


}
