package com.microservicio.carro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.carro.entity.Carro;
import com.microservicio.carro.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public List<Carro> listaCarros(){
		return carroRepository.findAll();
	}
	
	public Carro buscarCarro(int id) {
		return carroRepository.findById(id).orElse(null);
	}
	
	public Carro guardarNuevoCarro(Carro Carro) {
		Carro nuevoCarro = carroRepository.save(Carro);
		
		return nuevoCarro;
	}
	
	public List<Carro> byUsuarioId(int usuarioId){
		return carroRepository.findByUsuarioId(usuarioId);
	}

}
