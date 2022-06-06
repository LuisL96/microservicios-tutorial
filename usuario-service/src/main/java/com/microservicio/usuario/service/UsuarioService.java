package com.microservicio.usuario.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicio.usuario.config.models.CarroModel;
import com.microservicio.usuario.config.models.MotoModel;
import com.microservicio.usuario.entity.Usuario;
import com.microservicio.usuario.feign.CarroFeignClient;
import com.microservicio.usuario.feign.MotoFeignClient;
import com.microservicio.usuario.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CarroFeignClient carroFeignClient;
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	public List<Usuario> listaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarUsuario(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario guardarNuevoUsuario(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		
		return nuevoUsuario;
	}
	
	public List<CarroModel> listarCarrosByUsuario(int usuarioId){
		List<CarroModel> listarCarros = restTemplate.getForObject("http://carro-service/carro/usuario/" + usuarioId, List.class);
		
		return listarCarros;
	}
	
	public List<MotoModel> listarMotosByUsuario(int usuarioId){
		List<MotoModel> listarMotos = restTemplate.getForObject("http://moto-service/moto/usuario/" + usuarioId, List.class);
		
		return listarMotos;
	}
	
	public CarroModel guardarCarroFeign(int usuarioId,CarroModel carroModel) {
		carroModel.setUsuarioId(usuarioId);
		
		CarroModel nuevoCarro = carroFeignClient.save(carroModel);
		
		return nuevoCarro;
	}
	
	public MotoModel guardarMotoFeign(int usuarioId,MotoModel motoModel) {
		motoModel.setUsuarioId(usuarioId);
		
		MotoModel nuevoMoto = motoFeignClient.save(motoModel);
		
		return nuevoMoto;
	}
	
	public Map<String, Object> listaVehiculos(int usuarioId){
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		
		if(usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
		}
		
		resultado.put("Usuario", usuario);
		
		List<CarroModel> carros = carroFeignClient.getCarros(usuarioId);
		
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}else {
			resultado.put("Carros", carros);
		}
		
		List<MotoModel> motos = motoFeignClient.motosByUsuario(usuarioId);
		
		if(motos.isEmpty()) {
			resultado.put("Motos", "El usuario no tiene motos");
		}else {
			resultado.put("Motos", motos);
		}
		
		return resultado;
	}
	
	
}
