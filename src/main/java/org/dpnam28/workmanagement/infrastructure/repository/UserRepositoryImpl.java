package org.dpnam28.workmanagement.infrastructure.repository;

import lombok.RequiredArgsConstructor;

import org.dpnam28.workmanagement.domain.entity.User;
import org.dpnam28.workmanagement.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder encoder;
    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return jpaUserRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userToUpdate = jpaUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setPassword(encoder.encode(user.getPassword()));
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFullName(user.getFullName());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setRole(user.getRole());
        return jpaUserRepository.save(userToUpdate);
    }

    @Override
    public User findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return jpaUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.findByUsername(username).isPresent();
    }

    @Override
    public User findById(Long id) {
        return jpaUserRepository.findById(id).orElse(null);
    }
}
