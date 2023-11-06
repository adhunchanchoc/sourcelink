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
    void deleteLink(@PathVariable Long id){
        linkRepository.deleteById(id);
    }
    @PutMapping("/links/{id}")
    String updateLink(@RequestBody Link link,@PathVariable Long id){ // replacing
        Link updatedLink = linkRepository.findById(id)
                .map(updated -> {
//                        updated.setId(link.getId()); //already set when found
                        updated.setUrl(link.getUrl());
                        updated.setFile(link.getFile());
                        return linkRepository.save(updated);})
                .orElseThrow(() -> new LinkNotFoundException(id));
        return "Updated link: " + updatedLink;
    }
}
