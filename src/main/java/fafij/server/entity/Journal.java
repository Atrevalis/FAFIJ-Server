package fafij.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "journal", schema = "public")
public class Journal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable=false, unique = true)
    private String name;

    @ManyToMany(mappedBy="journal")
    private List<Users> user;

    @OneToMany(mappedBy = "idJournal")
    private List<Note> idNote;

    @ManyToMany(mappedBy="journal")
    private List<Roles> roles;

    @JsonManagedReference
    @OneToMany(mappedBy = "idJournal", fetch=FetchType.LAZY)
    private List<Category> idCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }

    public List<Note> getIdNote() {
        return idNote;
    }

    public void setIdNote(List<Note> idNote) {
        this.idNote = idNote;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Category> getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(List<Category> idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return Objects.equals(id, journal.id) && Objects.equals(name, journal.name) && Objects.equals(user, journal.user) && Objects.equals(idNote, journal.idNote) && Objects.equals(roles, journal.roles) && Objects.equals(idCategory, journal.idCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user, idNote, roles, idCategory);
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", name='" + name + '\'' + '}';
    }

    public Journal() {

    }
}