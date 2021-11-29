package fafij.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invitations", schema = "public")
public class Invitations {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Users idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jrnl", referencedColumnName = "id")
    private Journal idJournal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Roles idRole;

    @Column(name = "accepted", columnDefinition = "boolean default false")
    private Boolean accepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @JsonProperty(value = "idUser")
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

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitations that = (Invitations) o;
        return Objects.equals(id, that.id) && Objects.equals(idUser, that.idUser) && Objects.equals(idJournal, that.idJournal) && Objects.equals(idRole, that.idRole) && Objects.equals(accepted, that.accepted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idJournal, idRole, accepted);
    }

    @Override
    public String toString() {
        return "Invitations{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idJournal=" + idJournal +
                ", idRole=" + idRole +
                ", accepted=" + accepted +
                '}';
    }

    public Invitations() {
    }
}
