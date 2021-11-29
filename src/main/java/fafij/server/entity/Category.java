package fafij.server.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jrnl", referencedColumnName = "id")
    private Journal idJournal;

    @JsonManagedReference
    @OneToMany(mappedBy = "idCtgr", fetch=FetchType.LAZY)
    private List<Note> idNote;

    @JsonIgnore
    @JsonProperty(value = "id")
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

    public Journal getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(Journal idJournal) {
        this.idJournal = idJournal;
    }

        public List<Note> getIdNote() {
        return idNote;
    }

    public void setIdNote(List<Note> idNote) {
        this.idNote = idNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(idJournal, category.idJournal) && Objects.equals(idNote, category.idNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, idJournal, idNote);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idJournal=" + idJournal +
                ", idNote=" + idNote +
                '}';
    }

    public Category() {

    }
}