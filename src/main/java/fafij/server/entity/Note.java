package fafij.server.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "note", schema = "public")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ctgr", referencedColumnName = "id")
    private Category idCtgr;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_jrnl", referencedColumnName = "id")
    private Journal idJournal;

    @Column(name="comment")
    private String comment;

    @Column(name="type")
    private Boolean type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getIdCtgr() {
        return idCtgr;
    }

    public void setIdCtgr(Category idCtgr) {
        this.idCtgr = idCtgr;
    }

    public Journal getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(Journal idJournal) {
        this.idJournal = idJournal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((idCtgr == null) ? 0 : idCtgr.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((idJournal == null) ? 0 : idJournal.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (idCtgr == null) {
            if (other.idCtgr != null)
                return false;
        } else if (!idCtgr.equals(other.idCtgr))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (idJournal == null) {
            if (other.idJournal != null)
                return false;
        } else if (!idJournal.equals(other.idJournal))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Note [id=" + id + ", category ID=" + idCtgr + ", type=" + type + ", comment=" + comment + ", journal ID=" + idJournal + "]";
    }

    public Note() {

    }
}