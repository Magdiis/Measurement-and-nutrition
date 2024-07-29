package com.example.ea_project.domain.user;

import com.example.ea_project.domain.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

}
