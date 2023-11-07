package com.adhunchanchoc.sourcelink;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinkController {
    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/links/{id}")
    Link getOne(@PathVariable Long id) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
        return link;
    }

    @GetMapping("/links")
    List<Link> getAll() {
        return linkRepository.findAll();
    }

    @PostMapping("/links")
    String createLink(@RequestBody Link link) {
        return "Created link: " + linkRepository.save(link);
    }

    @DeleteMapping("/links/{id}")
    void deleteLink(@PathVariable Long id) {
        linkRepository.deleteById(id);
    }

    @PutMapping("/links/{id}")
    String updateLink(@RequestBody Link link, @PathVariable Long id) { // replacing
        Link updatedLink = linkRepository.findById(id)
                .map(updated -> {
//                        updated.setId(link.getId()); //already set when found
                    updated.setUrl(link.getUrl());
                    updated.setFile(link.getFile());
                    return linkRepository.save(updated);
                })
                .orElseThrow(() -> new LinkNotFoundException(id));
        return "Updated link: " + updatedLink;
    }

    @GetMapping("/links/files/{file}")
    Link getByFile(@PathVariable String file) {
        return linkRepository.findByFile(file).orElseThrow(
                () -> new RuntimeException(String.format("Filename \s not found", file))); //Exception should be customised
    }

    @GetMapping("/links/convert") //use ?url= to append request parameter
    String getConvertedUrl(@RequestParam String url){
        linkRepository.save(new Link(url,makeUrlCompatible(url)));
        // save into database
        return makeUrlCompatible(url);
//        return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8); //equals sign at the end means, that instead of the contentType it is received as dataType
//        return makeUrlCompatible(url);
    }
    //helper method to generate filename appendix
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
