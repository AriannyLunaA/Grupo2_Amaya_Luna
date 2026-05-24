package com.fixnow.ms_equipos.Service;

import com.fixnow.ms_equipos.Model.Equipo;
import com.fixnow.ms_equipos.Repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;


    public List<Equipo> listarEquipos() {
        return equipoRepository.findAll();
    }


    public Equipo buscarPorId(Long id) {
        return equipoRepository.findById(id).orElse(null);

    }


    public Equipo buscarPorNumeroSerie(String numeroSerie) {
        return equipoRepository.findByNumeroSerie(numeroSerie);
    }


    public List<Equipo> buscarPorMarca(String marca) {
        return equipoRepository.findByMarca(marca);
    }


    public List<Equipo> buscarPorEstado(String estado) {
        return equipoRepository.findByEstado(estado);
    }


    public List<Equipo> buscarPorMarcaYModelo(String marca, String modelo) {
        return equipoRepository.findByMarcaAndModelo(marca, modelo);
    }


    public Equipo agregarEquipo(Equipo nuevo) {
        return equipoRepository.save(nuevo);
    }


    public boolean borrarEquipo(Long id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public Equipo actualizarEquipo(Long id, Equipo nuevo) {
        if (equipoRepository.existsById(id)) {
            Equipo equipo = equipoRepository.findById(id).orElse(null);
            if (equipo != null) {
                equipo.setNombre(nuevo.getNombre());
                equipo.setMarca(nuevo.getMarca());
                equipo.setModelo(nuevo.getModelo());
                equipo.setNumeroSerie(nuevo.getNumeroSerie());
                equipo.setEstado(nuevo.getEstado());

                equipoRepository.save(equipo);
            }
            return equipo;
        } else {
            return null;
        }
    }
}
