package myapp.repository;

import myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {


  User findByEmail(String email);

  List<User> findByFirstName(String firstName);

  List<User> findByLastName(String lastName);

  //saveUser
  User save(User user);

}