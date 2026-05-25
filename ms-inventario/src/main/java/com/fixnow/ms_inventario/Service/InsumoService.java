package com.fixnow.ms_inventario.Service;

import com.fixnow.ms_inventario.Model.Insumo;

import java.util.List;

public interface InsumoService {
//definicion de operaciones permitidad.

List<Insumo> listarTodos();

Insumo buscarPorId(Long id);

Insumo registrarInsumo(Insumo insumo);

Insumo actualizarInsumo(Long id, Insumo insumo);

void eliminarInsumo(Long id);

}
