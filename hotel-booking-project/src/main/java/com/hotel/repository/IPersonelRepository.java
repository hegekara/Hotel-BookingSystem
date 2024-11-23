package com.hotel.repository;
import com.hotel.entities.Personel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPersonelRepository extends JpaRepository<Personel, Long>{
    Personel findByEmail(String email);
}
