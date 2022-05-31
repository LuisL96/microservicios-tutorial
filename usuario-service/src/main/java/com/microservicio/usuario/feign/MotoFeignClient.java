package com.microservicio.usuario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservicio.usuario.config.models.MotoModel;

@FeignClient(name="moto-service")
@RequestMapping("/moto")
public interface MotoFeignClient {

	@PostMapping("/guardarMoto")
	public MotoModel save(@RequestBody MotoModel motoModel);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<MotoModel> motosByUsuario(@PathVariable("usuarioId") int usuarioId);
}
