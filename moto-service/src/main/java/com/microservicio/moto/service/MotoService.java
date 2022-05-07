package com.microservicio.moto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservicio.moto.entity.Moto;
import com.microservicio.moto.repository.MotoRepository;

@Service
public class MotoService {

	@Autowired
	private MotoRepository motoRepository;
	
	public List<Moto> listaMotos(){
		return motoRepository.findAll();
	}
	
	public Moto buscarMoto(int id) {
		return motoRepository.findById(id).orElse(null);
	}
	
	public Moto guardarNuevoMoto(Moto moto) {
		Moto nuevoMoto = motoRepository.save(moto);
		
		return nuevoMoto;
	}
	
	public List<Moto> byUsuarioId(int usuarioId){
		return motoRepository.findByUsuarioId(usuarioId);
	}
}
