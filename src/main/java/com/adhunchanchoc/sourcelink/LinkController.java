package com.adhunchanchoc.sourcelink;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping(path = "api/v1/links")
@RestController
public class LinkController {
    private final LinkService linkService;
    private final LinkModelAssembler linkModelAssembler;

    public LinkController(LinkService linkService, LinkModelAssembler linkModelAssembler) {
        this.linkService = linkService;
        this.linkModelAssembler = linkModelAssembler;
    }

    @GetMapping("{id}")
    EntityModel<Weblink> getOne(@PathVariable Long id) {
        Weblink weblink = linkService.findById(id);
        return linkModelAssembler.toModel(weblink);
    }

    @GetMapping
    CollectionModel<EntityModel<Weblink>> getAll() {
        List<Weblink> weblinks = linkService.findAll();
        CollectionModel<EntityModel<Weblink>> linkModels = CollectionModel.of(
                weblinks.stream().map((link) -> linkModelAssembler.toModel(link)).collect(Collectors.toList()), //_embedded
                linkTo(methodOn(LinkController.class).getAll()).withSelfRel(),                                  //_links
                linkTo(LinkController.class).slash("help").withRel("GET_HELP")); // link to the /help endpoint
        return linkModels;
    }
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteLink(@PathVariable Long id) {
        linkService.deleteById(id);
        return ResponseEntity.noContent().build(); //204 = No content
    }
    @PostMapping
    ResponseEntity<?> createLink(@RequestBody Weblink weblink) {
        linkService.save(weblink);
        EntityModel<Weblink> linkModel = linkModelAssembler.toModel(weblink);
        return ResponseEntity
                .created(linkModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //include CREATED status and Location header with URI
                .body(linkModel);
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateLink(@RequestBody Weblink weblink, @PathVariable Long id) {
//        replacing the initial Weblink with JSON data
        Weblink updatedWeblink = linkService.updateLinkById(weblink, id);
        EntityModel<Weblink> linkModel = linkModelAssembler.toModel(updatedWeblink);
        return ResponseEntity
                .created(linkModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(linkModel);
    }

    @GetMapping("help")
    public String getHelp() {
        StringBuilder outputMD = new StringBuilder();
//        File readme = new File("README.MD");
        try (InputStream is = getClass().getResourceAsStream("/README.md"); //Thread.currentThread().getContextClassLoader()
            BufferedReader br = new BufferedReader(new InputStreamReader(is));)
        {    br.lines().forEach(line -> outputMD.append(line + "\n"));

//        try {
//            FileReader fr = new FileReader(readme);
//            BufferedReader br = new BufferedReader(fr);
//            br.lines().forEach(line -> outputMD.append(line + "\n"));
        } catch (IOException ioEx) {
            throw new RuntimeException("README.md was not found");
        }
        return linkService.markdownToHtml(outputMD.toString());
    }

//    Retrieval of a Weblink object by its field named 'file'
    @GetMapping("files/{file}")
    Weblink getByFile(@PathVariable String file) {
        return linkService.findByFile(file);
    }
    @GetMapping("convert/**")
    String getConvertedUrl(HttpServletRequest request) {
        String fullRequestPath = request.getRequestURI(); //TODO null case
        String url = fullRequestPath.split("/convert/")[1];
        System.out.println("Input URL: " + url);
        // save to database
        linkService.save(new Weblink(url, linkService.makeUrlCompatible(url)));
        return linkService.makeUrlCompatible(url);
    }

}