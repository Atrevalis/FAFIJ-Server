package fafij.server.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "journal", schema = "public")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable=false, unique = true)
    private String name;

    @ManyToMany(mappedBy="journal")
    private Set<Users> user = new HashSet<>();

    @OneToMany(mappedBy = "idJournal")
    private Set<Note> idNote;

    @ManyToMany(mappedBy="journal")
    private Set<Roles> roles = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Users> getUser() {
        return user;
    }

    public void setUser(Set<Users> user) {
        this.user = user;
    }

    public Set<Note> getIdNote() {
        return idNote;
    }

    public void setIdNote(Set<Note> idNote) {
        this.idNote = idNote;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        //result = prime * result + ((idRole == null) ? 0 : idRole.hashCode());
       //result = prime * result + ((idNote == null) ? 0 : idNote.hashCode());
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
        Journal other = (Journal) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        /*if (idRole == null) {
            if (other.idRole != null)
                return false;
        } else if (!idRole.equals(other.idRole))
            return false;
        if (idNote == null) {
            if (other.idNote != null)
                return false;
        } else if (!idNote.equals(other.idNote))
            return false;*/
        return true;
    }
    @Override
    public String toString() {
        return "Journal [id=" + id + ", name=" + name + "]";
    }

    public Journal() {

    }
}