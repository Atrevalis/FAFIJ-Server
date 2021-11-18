package fafij.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column
        private String login;

        @Column(name = "pswd")
        private String password;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
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

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((login == null) ? 0 : login.hashCode());
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                result = prime * result + ((password == null) ? 0 : password.hashCode());
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
                Users other = (Users) obj;
                if (login == null) {
                        if (other.login != null)
                                return false;
                } else if (!login.equals(other.login))
                        return false;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                if (password == null) {
                        if (other.password != null)
                                return false;
                } else if (!password.equals(other.password))
                        return false;
                return true;
        }
        @Override
        public String toString() {
                return "Users [id=" + id + ", login=" + login + ", password=" + password + "]";
        }

        public Users() {

        }
}