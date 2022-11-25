package nl.fontys.marketplacebackend.persistence;

import nl.fontys.marketplacebackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);
}
