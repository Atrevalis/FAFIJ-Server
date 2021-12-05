package fafij.server.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_roles", schema = "public")

@IdClass(UserRolesId.class)
public class UserRoles implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Users idUser;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_jrnl", referencedColumnName = "id")
    private Journal idJournal;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Roles idRole;

    public Users getIdUser() {
        return idUser;
    }

    public void setIdUser(Users idUser) {
        this.idUser = idUser;
    }

    public Journal getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(Journal idJournal) {
        this.idJournal = idJournal;
    }

    public Roles getIdRole() {
        return idRole;
    }

    public void setIdRole(Roles idRole) {
        this.idRole = idRole;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
        result = prime * result + ((idRole == null) ? 0 : idRole.hashCode());
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
        UserRoles other = (UserRoles) obj;
        if (idUser == null) {
            if (other.idUser != null)
                return false;
        } else if (!idUser.equals(other.idUser))
            return false;
        if (idRole == null) {
            if (other.idRole != null)
                return false;
        } else if (!idRole.equals(other.idRole))
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
        return "Role [user ID=" + idUser + ", role ID=" + idRole + ", journal ID=" + idJournal + "]";
    }

    public UserRoles() {

    }
}