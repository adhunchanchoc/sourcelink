//package com.adhunchanchoc.sourcelink;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////Controller working with plaintext data (eventually converted to JSON) not with real "resources" in REST sense (Response Entities)
//@RestController
//public class LinkPlainController {
//    private final LinkRepository linkRepository;
//    private final LinkService linkService;
//
//    public LinkPlainController(LinkRepository linkRepository, LinkService linkService) {
//        this.linkRepository = linkRepository;
//        this.linkService = linkService;
//    }
//
//    @GetMapping("/links/{id}")
//    Weblink getOne(@PathVariable Long id) {
//        Weblink link = linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
//        return link;
//    }
//
//    @GetMapping("/links")
//    List<Weblink> getAll() {
//        return linkRepository.findAll();
//    }
//
//    @PostMapping("/links")
//    String createLink(@RequestBody Weblink link) {
//        return "Created link: " + linkRepository.save(link);
//    }
//
//    @DeleteMapping("/links/{id}")
//    void deleteLink(@PathVariable Long id) {
//        linkRepository.deleteById(id);
//    }
//
//    @PutMapping("/links/{id}")
//    String updateLink(@RequestBody Weblink link, @PathVariable Long id) { // replacing
//        Weblink updatedLink = linkRepository.findById(id)
//                .map(updated -> {
////                        updated.setId(link.getId()); //unnecessary - already set when found
//                    updated.setUrl(link.getUrl());
//                    updated.setFile(link.getFile());
//                    return linkRepository.save(updated);
//                })
//                .orElseThrow(() -> new LinkNotFoundException(id));
//        return "Updated link: " + updatedLink;
//    }
//
//    @GetMapping("/links/files/{file}")
//    Weblink getByFile(@PathVariable String file) {
//        return linkRepository.findByFile(file).orElseThrow(
//                () -> new RuntimeException(String.format("Filename \s not found", file))); //Exception should be customised
//    }
//
//    @GetMapping("/links/convert/**")
//    String getConvertedUrl(HttpServletRequest request) {
//        String fullRequestPath = request.getRequestURI(); //TODO null case
//        String url = fullRequestPath.split("/convert/")[1];
//        System.out.println("Input URL: "+url);
//        // save to database
//        linkRepository.save(new Weblink(url, linkService.makeUrlCompatible(url)));
//        return linkService.makeUrlCompatible(url);
////        return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8); //equals sign at the end means, that instead of the contentType it is received as dataType
//    }
//
//}
