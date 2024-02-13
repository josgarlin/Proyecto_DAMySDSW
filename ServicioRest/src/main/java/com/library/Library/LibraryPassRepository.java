package com.library.Library;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

interface LibraryPassRepository extends JpaRepository<LibraryPass, String> {
	@Transactional
	Optional <LibraryPass> findByUniversity (String university);
}
