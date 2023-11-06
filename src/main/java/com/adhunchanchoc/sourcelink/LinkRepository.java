package com.adhunchanchoc.sourcelink;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByFile(String file);

    //@Query("custom SQL command for possible method below, where e.g. second method param. marked by ?2")
}
