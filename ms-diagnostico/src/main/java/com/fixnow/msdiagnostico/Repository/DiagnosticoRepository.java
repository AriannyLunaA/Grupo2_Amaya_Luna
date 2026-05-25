package com.fixnow.msdiagnostico.Repository;


import com.fixnow.msdiagnostico.Model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
}