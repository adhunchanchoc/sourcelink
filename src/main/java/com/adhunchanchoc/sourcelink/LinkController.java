package com.adhunchanchoc.sourcelink;

import com.adhunchanchoc.sourcelink.exception.LinkNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping(path = "api/v1/links")
@RestController
public class LinkController {
    private final LinkRepository linkRepository;
    private final LinkService linkService;
    private final LinkModelAssembler linkModelAssembler;

    public LinkController(LinkRepository linkRepository, LinkService linkService, LinkModelAssembler linkModelAssembler) {
        this.linkRepository = linkRepository;
        this.linkService = linkService;
        this.linkModelAssembler = linkModelAssembler;
    }

    @GetMapping("{id}")
    EntityModel<Weblink> getOne(@PathVariable Long id) {
        Weblink weblink = linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
        return linkModelAssembler.toModel(weblink);
    }
//TODO delete this test method (
    @GetMapping("TEST/{id}")
    EntityModel<Weblink> getOneLink(@PathVariable Long id) {
        EntityModel<Weblink> linkModel = getAll().getContent().stream().filter(weblinkEntityModel -> weblinkEntityModel.getContent().getId() == id).findFirst()
                .orElseThrow(() -> new LinkNotFoundException(id));
        Weblink weblink1 = linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(id));
        return linkModel;
    }

    @GetMapping
    CollectionModel<EntityModel<Weblink>> getAll() {
        List<Weblink> weblinks = linkRepository.findAll();
        CollectionModel<EntityModel<Weblink>> linkModels = CollectionModel.of(
                weblinks.stream().map((link) -> linkModelAssembler.toModel(link)).collect(Collectors.toList()), //_embedded
                linkTo(methodOn(LinkController.class).getAll()).withSelfRel(),                                  //_links
                linkTo(LinkController.class).slash("help").withRel("GET_HELP")); //TODO static html help
        return linkModels;
    }

    // POST will respond with HAL (link-enriched) object with link; could even return the created instance of webLink, which would be automatically converted to valid HTTP response with JSONized object
    @PostMapping
    ResponseEntity<?> createLink(@RequestBody Weblink weblink) {
        //TODO validation
        linkRepository.save(weblink);
        EntityModel<Weblink> linkModel = linkModelAssembler.toModel(weblink);
        return ResponseEntity
                .created(linkModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //include CREATED status and a Location header with URI
                .body(linkModel);
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> deleteLink(@PathVariable Long id) {
        linkRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //204 Status code for No content
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateLink(@RequestBody Weblink weblink, @PathVariable Long id) {
//        replacing the initial Weblink with JSON data
        Weblink updatedWeblink = linkRepository.findById(id)
                .map(updated -> {
//                        updated.setId(weblink.getId()); //unnecessary - already set when found
                    updated.setUrl(weblink.getUrl());
                    updated.setFile(weblink.getFile());
                    return linkRepository.save(updated);
                })
                .orElseThrow(() -> new LinkNotFoundException(id));
        EntityModel<Weblink> linkModel = linkModelAssembler.toModel(updatedWeblink);
        return ResponseEntity
                .created(linkModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(linkModel);
    }

    @GetMapping("files/{file}")
    Weblink getByFile(@PathVariable String file) {
        return linkRepository.findByFile(file).orElseThrow(
                () -> new RuntimeException(String.format("Filename \s not found", file))); //Exception should be customised
    }

    @GetMapping("convert/**")
    String getConvertedUrl(HttpServletRequest request) {
        String fullRequestPath = request.getRequestURI(); //TODO null case
        String url = fullRequestPath.split("/convert/")[1];
        System.out.println("Input URL: " + url);
        // save to database
        linkRepository.save(new Weblink(url, linkService.makeUrlCompatible(url)));
        return linkService.makeUrlCompatible(url);
//        return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8); //equals sign at the end means, that instead of the contentType it is received as dataType
    }

}
