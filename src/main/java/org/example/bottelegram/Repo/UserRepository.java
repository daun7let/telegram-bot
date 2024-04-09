package org.example.bottelegram.Repo;

import org.example.bottelegram.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
