package id.co.vasystem.frontend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import id.co.vasystem.frontend.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	public User findByUsername(String userName);
}
