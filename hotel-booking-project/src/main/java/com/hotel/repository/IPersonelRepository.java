package com.hotel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.entities.user.Personel;


@Repository
public interface IPersonelRepository extends JpaRepository<Personel, Long>{
    Personel findByEmail(String email);
}
