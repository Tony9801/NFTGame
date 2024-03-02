package com.tony.nftLogin.repository;

import com.tony.nftLogin.model.Scene;
import com.tony.nftLogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SceneRepository extends JpaRepository<Scene, Long> {
    Optional<User> findByUserUserId(Long userId);
}