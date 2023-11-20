package com.adhunchanchoc.sourcelink;

// Controller working with plaintext data (eventually converted to JSON) not with real "resources" in REST sense (Response Entities)
// Uses repository class directly, service class is used only for URL conversion
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
//    String updateLink(@RequestBody Weblink link, @PathVariable Long id) {
//        Weblink updatedLink = linkRepository.findById(id)
//                .map(updated -> {
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
//                () -> new RuntimeException(String.format("Filename \s not found", file)));
//    }
//
//    @GetMapping("/links/convert/**")
//    String getConvertedUrl(HttpServletRequest request) {
//        String fullRequestPath = request.getRequestURI();
//        String url = fullRequestPath.split("/convert/")[1];
//        linkRepository.save(new Weblink(url, linkService.makeUrlCompatible(url)));
//        return linkService.makeUrlCompatible(url);
//    }
//
//}
