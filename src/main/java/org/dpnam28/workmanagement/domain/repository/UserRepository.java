package org.dpnam28.workmanagement.domain.repository;

import org.dpnam28.workmanagement.domain.entity.User;

public interface UserRepository {
    User save(User user);
    User update(Long id, User user);
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findById(Long id);
}
