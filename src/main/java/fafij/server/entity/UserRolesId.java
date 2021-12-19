package fafij.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

public class UserRolesId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Users idUser;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_jrnl", referencedColumnName = "id")
    private Journal idJournal;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesId that = (UserRolesId) o;
        return Objects.equals(idUser, that.idUser) && Objects.equals(idJournal, that.idJournal) && Objects.equals(idRole, that.idRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idJournal, idRole);
    }

    @Override
    public String toString() {
        return "UserRolesId{" +
                "idUser=" + idUser +
                ", idJournal=" + idJournal +
                ", idRole=" + idRole +
                '}';
    }

    public UserRolesId() {

    }

    public UserRolesId(Users idUser, Journal idJournal, Roles idRole) {
        this.idUser = idUser;
        this.idJournal = idJournal;
        this.idRole = idRole;
    }
}