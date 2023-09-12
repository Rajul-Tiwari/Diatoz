package com.diatoz.repository;

import com.diatoz.Model.MyRole;
import com.diatoz.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    public Role findByRole(MyRole roles);
}
