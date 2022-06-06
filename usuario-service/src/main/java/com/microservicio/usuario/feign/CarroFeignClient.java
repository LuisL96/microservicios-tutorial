package com.microservicio.usuario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservicio.usuario.config.models.CarroModel;

@FeignClient(name="carro-service")
@RequestMapping("/carro")
public interface CarroFeignClient {

	@PostMapping("/guardarCarro")
	public CarroModel save(@RequestBody CarroModel carroModel);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<CarroModel> getCarros(@PathVariable("usuarioId") int usuarioId);
	
}
