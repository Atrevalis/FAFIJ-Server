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

    @Column(name="sum")
    private Long sum;

    @Column(name="date")
    private String date;

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

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((idCtgr == null) ? 0 : idCtgr.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((sum == null) ? 0 : sum.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        if (sum == null) {
            if (other.sum != null)
                return false;
        } else if (!sum.equals(other.sum))
            return false;
        if (idJournal == null) {
            if (other.idJournal != null)
                return false;
        } else if (!idJournal.equals(other.idJournal))
            return false;
        if (date == null) {
            if (other.date  != null)
                return false;
        } else if (!date .equals(other.date))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Note [id=" + id + ", category ID=" + idCtgr + ", sum=" + sum + ", date=" + date + ", comment=" + comment + ", journal ID=" + idJournal + "]";
    }

    public Note() {

    }
}