package com.adhunchanchoc.sourcelink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Link {
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    private String url;
    private String file;

    public Link() { }

    public Link(String url, String file) {
        this.url = url;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(id, link.id) && Objects.equals(url, link.url) && Objects.equals(file, link.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, file);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", created=" + created +
                ", url='" + url + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
