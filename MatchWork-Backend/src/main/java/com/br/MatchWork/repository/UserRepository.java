package com.br.MatchWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.MatchWork.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}