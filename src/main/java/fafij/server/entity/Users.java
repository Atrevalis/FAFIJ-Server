package fafij.server.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public")
public class Users {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "login", nullable=false)
        private String login;

        @Column(name = "password", nullable=false)
        private String password;

        @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
        @JoinTable(
                name="user_roles",
                joinColumns = @JoinColumn(name="id_user"),
                inverseJoinColumns = @JoinColumn(name="id_jrnl")
        )
        private List<Journal> journal;

        @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
        @JoinTable(
                name="user_roles",
                joinColumns = @JoinColumn(name="id_user"),
                inverseJoinColumns = @JoinColumn(name="id_role")
        )
        private List<Roles> role;

        @OneToMany(mappedBy = "idUser")
        private List<Invitations> idInvitations;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getLogin() {
                return login;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public List<Journal> getJournal() {
                return journal;
        }

        public void setJournal(List<Journal> journal) {
                this.journal = journal;
        }

        public List<Roles> getRole() {
                return role;
        }

        public void setRole(List<Roles> role) {
                this.role = role;
        }

        public List<Invitations> getIdInvitations() {
                return idInvitations;
        }

        public void setIdInvitations(List<Invitations> idInvitations) {
                this.idInvitations = idInvitations;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Users users = (Users) o;
                return Objects.equals(id, users.id) && Objects.equals(login, users.login) && Objects.equals(password, users.password) && Objects.equals(journal, users.journal) && Objects.equals(role, users.role);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, login, password, journal, role);
        }

        @Override
        public String toString() {
                return "Users{" +
                        "id=" + id +
                        ", login='" + login + '\'' + '}';
        }

        public Users() {

        }

        public Users( String login, String password, List<Journal> journal, List<Roles> role, List<Invitations> idInvitations) {
                this.login = login;
                this.password = password;
                this.journal = journal;
                this.role = role;
                this.idInvitations = idInvitations;
        }
}