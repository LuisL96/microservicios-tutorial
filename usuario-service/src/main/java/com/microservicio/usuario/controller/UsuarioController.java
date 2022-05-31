package com.microservicio.usuario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.usuario.config.models.CarroModel;
import com.microservicio.usuario.config.models.MotoModel;
import com.microservicio.usuario.entity.Usuario;
import com.microservicio.usuario.service.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.listaUsuarios();
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("{id}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.buscarUsuario(id);

		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}

	@PostMapping("guardarUsuario")
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.guardarNuevoUsuario(usuario);

		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(nuevoUsuario);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
	@GetMapping("carro/{usuarioId}")
	public ResponseEntity<List<CarroModel>> listaCarrosUsuario(@PathVariable("usuarioId") int usuarioId) {
		Usuario user = usuarioService.buscarUsuario(usuarioId);

		if (user == null) {
			return ResponseEntity.noContent().build();
		}

		List<CarroModel> listaCarro = usuarioService.listarCarrosByUsuario(usuarioId);

		return ResponseEntity.ok(listaCarro);
	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
	@GetMapping("moto/{usuarioId}")
	public ResponseEntity<List<MotoModel>> listaMotosUsuario(@PathVariable("usuarioId") int usuarioId) {
		Usuario user = usuarioService.buscarUsuario(usuarioId);

		if (user == null) {
			return ResponseEntity.noContent().build();
		}

		List<MotoModel> listaMoto = usuarioService.listarMotosByUsuario(usuarioId);

		return ResponseEntity.ok(listaMoto);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<CarroModel> saveCarroFeign(@PathVariable("usuarioId") int usuarioId,
			@RequestBody CarroModel carroModel) {
		CarroModel nuevoCarro = usuarioService.guardarCarroFeign(usuarioId, carroModel);

		return ResponseEntity.ok(nuevoCarro);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<MotoModel> saveMotoFeign(@PathVariable("usuarioId") int usuarioId,
			@RequestBody MotoModel motoModel) {
		MotoModel nuevoMoto = usuarioService.guardarMotoFeign(usuarioId, motoModel);

		return ResponseEntity.ok(nuevoMoto);
	}

	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
	@GetMapping("/vehiculos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> getVehiculos(@PathVariable("usuarioId") int usuarioId) {
		Map<String, Object> resultado = usuarioService.listaVehiculos(usuarioId);

		return ResponseEntity.ok(resultado);
	}

	private ResponseEntity<List<CarroModel>> fallBackGetCarros(@PathVariable("usuarioId") int usuarioId,
			RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + usuarioId + " tiene los carros en el taller", HttpStatus.OK);
	}

	private ResponseEntity<List<CarroModel>> fallBackSaveCarro(@PathVariable("usuarioId") int usuarioId,
			@RequestBody CarroModel carroModel, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + usuarioId + " no tiene dinero suficiente para un carro",
				HttpStatus.OK);
	}

	private ResponseEntity<List<MotoModel>> fallBackGetMotos(@PathVariable("usuarioId") int usuarioId,
			RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + usuarioId + " tiene los motos en el taller", HttpStatus.OK);
	}

	private ResponseEntity<List<CarroModel>> fallBackSaveMoto(@PathVariable("usuarioId") int usuarioId,
			@RequestBody MotoModel motoModel, RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + usuarioId + " no tiene dinero suficiente para una moto",
				HttpStatus.OK);
	}

	private ResponseEntity<List<CarroModel>> fallBackGetTodos(@PathVariable("usuarioId") int usuarioId,
			RuntimeException excepcion) {
		return new ResponseEntity("El usuario: " + usuarioId + " tiene sus vehiculos en el taller", HttpStatus.OK);
	}

}
