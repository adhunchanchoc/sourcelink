package com.adhunchanchoc.sourcelink;

import com.adhunchanchoc.sourcelink.exception.InvalidInputException;
import com.adhunchanchoc.sourcelink.exception.LinkNotFoundException;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
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

    /**
     * Method to convert .md markup file to HTML using 'commonmark' parser
     *
     * @param markdown text content of the .md file
     * @return HTML readable by browser
     */
    public String markdownToHtml(String markdown) {
        // adding extension to generate anchors for HTML headings (needed for hyperlinked table of contents)
        List<Extension> extensions = Arrays.asList(HeadingAnchorExtension.create(), AutolinkExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();

        return renderer.render(document);
    }

    public Weblink findById(Long id) {
        return linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
    }

    public List<Weblink> findAll() {
        return linkRepository.findAll();
    }

    public void deleteById(Long id) {
        linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
        linkRepository.deleteById(id);
    }

    public void save(Weblink weblink) {
        String inputURL = weblink.getUrl();

        if (inputURL == null || inputURL.isEmpty()) {
            throw new InvalidInputException();
        }
        linkRepository.save(new Weblink(
                inputURL,
                (weblink.getFile() == null || weblink.getFile().isEmpty()) ? makeUrlCompatible(inputURL) : weblink.getFile()
        ));
    }

    public Weblink updateLinkById(Weblink weblink, Long id) {
        Weblink updated = linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
        String inputFile = weblink.getFile();
        String inputURL = weblink.getUrl();

        if (inputURL == null || inputURL.isEmpty()) {
            throw new InvalidInputException();
        }
        updated.setUrl(inputURL);
        updated.setFile((inputFile == null || inputFile.isEmpty()) ? makeUrlCompatible(inputURL) : inputFile);
        return linkRepository.save(updated);

    }

    public Weblink findByFile(String file) {
        return linkRepository.findByFile(file).orElseThrow(
                () -> new RuntimeException(String.format("Filename \"\s\" not found", file)));
    }
}
