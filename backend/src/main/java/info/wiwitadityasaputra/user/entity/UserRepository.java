package info.wiwitadityasaputra.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

	public User findByInitial(String initial);

	public User findByUserId(String userId);
}
