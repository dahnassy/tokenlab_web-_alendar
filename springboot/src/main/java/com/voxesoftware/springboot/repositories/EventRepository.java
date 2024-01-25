package com.voxesoftware.springboot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voxesoftware.springboot.models.EventModel;

@Repository
public interface EventRepository extends JpaRepository<EventModel, UUID> {

}
