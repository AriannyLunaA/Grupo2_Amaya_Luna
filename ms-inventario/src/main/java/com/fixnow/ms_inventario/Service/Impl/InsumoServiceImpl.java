package com.fixnow.ms_inventario.Service.Impl;

import com.fixnow.ms_inventario.Model.Insumo;
import com.fixnow.ms_inventario.Repository.InsumoRepository;
import com.fixnow.ms_inventario.Service.InsumoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;

    public InsumoServiceImpl(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }
//1, Lista de insumos
    @Override
    @Transactional(readOnly = true) //Solo lectura de datos
    public List<Insumo> listarTodos() {
        return insumoRepository.findAll();
    }
//2,Buscar insumos
    @Override
    @Transactional(readOnly = true)
    public Insumo buscarPorId(Long id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));
    }
//3.Refistar insumos
    @Override
    @Transactional
    public Insumo registrarInsumo(Insumo insumo) {
        return insumoRepository.save(insumo);
    }
//4, actualizar insumos
    @Override
    @Transactional
    public Insumo actualizarInsumo(Long id, Insumo insumoNuevo) {
       //toma el objeto
        Insumo insumoExistente = buscarPorId(id);

        insumoExistente.setNombre(insumoNuevo.getNombre());
        insumoExistente.setSku(insumoNuevo.getSku());
        insumoExistente.setStockActual(insumoNuevo.getStockActual());
        insumoExistente.setUnidadMedida(insumoNuevo.getUnidadMedida());
        insumoExistente.setPrecioUnitario(insumoNuevo.getPrecioUnitario());

        return insumoRepository.save(insumoExistente);
    }
//5.Eliminar insumo
    @Override
    @Transactional
    public void eliminarInsumo(Long id) {
        Insumo insumoExistente = buscarPorId(id);
        insumoRepository.delete(insumoExistente);
    }
}