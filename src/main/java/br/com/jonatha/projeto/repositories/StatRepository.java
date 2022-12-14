package br.com.jonatha.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jonatha.projeto.domain.Stat;

@Repository
public interface StatRepository extends JpaRepository<Stat, Integer> {

}
