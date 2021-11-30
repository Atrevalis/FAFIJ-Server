package fafij.server.service;

import fafij.server.entity.Invitations;
import fafij.server.entity.Journal;
import fafij.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations, Long> {
    List<Invitations> findAllByIdUserAndAccepted(Users user, Boolean accept);
    Invitations findByIdUserAndIdJournalAndAccepted(Users user, Journal journal, Boolean accept);
    @Modifying
    @Query(value = "insert into invitations (id_user, id_jrnl, id_role) values(:id_user, :id_jrnl, :id_role)", nativeQuery = true)
    @Transactional
    void addInvitation(@Param("id_user") Long id_user, @Param("id_jrnl") Long id_jrnl, @Param("id_role") Long id_role);
    Boolean existsByIdUserAndIdJournal(Users user, Journal journal);
}
