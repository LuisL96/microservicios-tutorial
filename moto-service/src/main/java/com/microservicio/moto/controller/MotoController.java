package com.microservicio.moto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.moto.entity.Moto;
import com.microservicio.moto.service.MotoService;

@RestController
@RequestMapping("/moto")
public class MotoController {

	@Autowired
	private MotoService motoService;
	
	@GetMapping
	public ResponseEntity<List<Moto>> listaCarro(){
		List<Moto> listaCarros = motoService.listaMotos();
		
		if(listaCarros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaCarros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Moto> buscarCarro(@PathVariable("id") int id){
		Moto carroId = motoService.buscarMoto(id);
		
		if(carroId == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(carroId);
	}
	
	@PostMapping("/guardarMoto")
	public ResponseEntity<Moto> guardarCarro(@RequestBody Moto carro){
		Moto nuevoCarro = motoService.guardarNuevoMoto(carro);
		
		return ResponseEntity.ok(nuevoCarro);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Moto>> listarCarroByUsuario(@PathVariable("usuarioId") int usuarioId){
		List<Moto> listaCarroUsuario = motoService.byUsuarioId(usuarioId);
		
		if(listaCarroUsuario.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaCarroUsuario);
	}
}
