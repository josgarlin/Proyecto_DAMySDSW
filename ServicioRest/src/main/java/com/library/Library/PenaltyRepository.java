package com.library.Library;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;
@Transactional 
interface PenaltyRepository extends JpaRepository<Penalty, String> {
	
	@Transactional
	Optional <Penalty> findByDni(String dni);
	@Transactional
	int deleteByDni(String dni);
}