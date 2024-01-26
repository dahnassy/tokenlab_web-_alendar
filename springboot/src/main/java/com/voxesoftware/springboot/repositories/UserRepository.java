package com.voxesoftware.springboot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voxesoftware.springboot.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
