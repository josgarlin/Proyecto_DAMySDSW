package com.library.Library;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

interface UserLibRepository extends JpaRepository<UserLib, String> {
	@Transactional
	Optional <UserLib> findByDni (String dni);
}