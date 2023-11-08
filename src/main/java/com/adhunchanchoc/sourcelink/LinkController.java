package com.adhunchanchoc.sourcelink;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinkController {
    private final LinkRepository linkRepository;
    private final LinkService linkService;

    public LinkController(LinkRepository linkRepository, LinkService linkService) {
        this.linkRepository = linkRepository;
        this.linkService = linkService;
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
//                        updated.setId(link.getId()); //unnecessary - already set when found
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

    @GetMapping("/links/convert/**")
    String getConvertedUrl(HttpServletRequest request) {
        String fullRequestPath = request.getRequestURI(); //TODO null case
        String url = fullRequestPath.split("/convert/")[1];
        System.out.println("Input URL: "+url);
        // save to database
        linkRepository.save(new Link(url, linkService.makeUrlCompatible(url)));
        return linkService.makeUrlCompatible(url);
//        return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8); //equals sign at the end means, that instead of the contentType it is received as dataType
    }

}
