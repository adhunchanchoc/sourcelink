package com.adhunchanchoc.sourcelink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
//@Table
public class Weblink {
    //    @Id
    //    @SequenceGenerator(
    //            name = "link_sequence",
    //            sequenceName = "link_sequence",
    //            allocationSize = 1
    //    )
    //    @GeneratedValue(
    //            strategy = GenerationType.SEQUENCE,
    //            generator = "link_sequence"
    //    )
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    private String url;
    private String file;
    //@Transient // marking the field not needed to be stored (when virtual, calculated)

    public Weblink() { }

    public Weblink(String url, String file) {
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
        Weblink weblink = (Weblink) o;
        return Objects.equals(id, weblink.id) && Objects.equals(url, weblink.url) && Objects.equals(file, weblink.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, file);
    }

    @Override
    public String toString() {
        return "Weblink{" +
                "id=" + id +
                ", created=" + created +
                ", url='" + url + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
