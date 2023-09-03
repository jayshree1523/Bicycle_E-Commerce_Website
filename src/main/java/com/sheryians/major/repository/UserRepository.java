package com.sheryians.major.repository;

import com.sheryians.major.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer>
{

      Optional<User>findUserByEmail(String email); /// custom method

}
