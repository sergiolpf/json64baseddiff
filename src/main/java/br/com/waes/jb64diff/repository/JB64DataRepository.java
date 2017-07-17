package br.com.waes.jb64diff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.waes.jb64diff.model.JB64Data;

@Repository("jb64DataRepository")
public interface JB64DataRepository extends JpaRepository<JB64Data, Long> {

}
