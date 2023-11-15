package com.adhunchanchoc.sourcelink;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class LinkModelAssembler implements RepresentationModelAssembler<Weblink, EntityModel<Weblink>> {
    @Override
    public EntityModel<Weblink> toModel(Weblink weblink) {
        return EntityModel.of(
                weblink,
                linkTo(methodOn(LinkController.class).getOne(weblink.getId())).withSelfRel(),
                linkTo(methodOn(LinkController.class).getAll()).withRel("links") //TODO can add more significant name (to differ from HAL model _link)
                );
    }
}
