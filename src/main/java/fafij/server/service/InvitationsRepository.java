package fafij.server.service;

import fafij.server.entity.Invitations;
import fafij.server.entity.Journal;
import fafij.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations, Long> {
    List<Invitations> findAllByIdUserAndAccepted(Users user, Boolean accept);
}
