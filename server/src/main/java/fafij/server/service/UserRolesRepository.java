package fafij.server.service;

import fafij.server.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId> {
    List<UserRoles> findAllByIdRole(Roles role);
    UserRoles findByIdUser(Users user);
    List<UserRoles> findAllByIdJournal(Journal jrnl);
}