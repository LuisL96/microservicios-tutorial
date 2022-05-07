package com.microservicio.carro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.carro.entity.Carro;
import com.microservicio.carro.service.CarroService;

@RestController
@RequestMapping("/carro")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<List<Carro>> listaCarro(){
		List<Carro> listaCarros = carroService.listaCarros();
		
		if(listaCarros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaCarros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Carro> buscarCarro(@PathVariable("id") int id){
		Carro carroId = carroService.buscarCarro(id);
		
		if(carroId == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(carroId);
	}
	
	@PostMapping("/guardarCarro")
	public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
		Carro nuevoCarro = carroService.guardarNuevoCarro(carro);
		
		return ResponseEntity.ok(nuevoCarro);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarroByUsuario(@PathVariable("usuarioId") int usuarioId){
		List<Carro> listaCarroUsuario = carroService.byUsuarioId(usuarioId);
		
		if(listaCarroUsuario.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaCarroUsuario);
	}

}
