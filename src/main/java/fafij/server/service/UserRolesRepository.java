package fafij.server.service;

import fafij.server.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId> {
    List<UserRoles> findAllByIdRole(Roles role);

    List<UserRoles> findAllByIdUser(Users user);

    UserRoles findByIdUser(Users user);

    UserRoles findByIdUserAndIdJournal(Users user, Journal journal);

    List<UserRoles> findAllByIdJournal(Journal jrnl);

    @Modifying
    @Query(value = "insert into user_roles values(:id_user, :id_jrnl, :id_role)", nativeQuery = true)
    @Transactional
    void setUserRoles(@Param("id_user") Long id_user, @Param("id_jrnl") Long id_jrnl, @Param("id_role") Long id_role);
}