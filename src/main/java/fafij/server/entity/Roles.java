package fafij.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles", schema = "public")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="id_role"),
            inverseJoinColumns = @JoinColumn(name="id_jrnl")
    )
    private List<Journal> journal;

    @ManyToMany(mappedBy="role")
    private List<Users> user;

    @OneToMany(mappedBy = "idRole")
    private List<Invitations> idInvitations;

    @Column(name = "role_name", length = 15, unique = true, nullable = false)
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Journal> getJournal() {
        return journal;
    }

    public void setJournal(List<Journal> journal) {
        this.journal = journal;
    }

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Invitations> getIdInvitations() {
        return idInvitations;
    }

    public void setIdInvitations(List<Invitations> idInvitations) {
        this.idInvitations = idInvitations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
        Roles other = (Roles) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (roleName == null) {
            if (other.roleName != null)
                return false;
        } else if (!roleName.equals(other.roleName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Note [id=" + id + ", role name=" + roleName + "]";
    }

    public Roles() {

    }
}



