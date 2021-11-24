package fafij.server.Repository;

import fafij.server.entity.Roles;
import fafij.server.service.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    private final RolesRepository rolesRepository;

    public RolesService (RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public Roles findByRoleName(String name){
        return rolesRepository.findByRoleName(name);
    }
}
