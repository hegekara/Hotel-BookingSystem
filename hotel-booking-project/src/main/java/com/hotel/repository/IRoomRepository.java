package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.Room;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long>{

}
