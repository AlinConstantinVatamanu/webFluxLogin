package com.nttdata.webfluxlogin.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.webfluxlogin.models.Person;

import reactor.core.publisher.Flux;

@RestController
public class AppController {
private List<Person> listaUsuarios = new ArrayList<Person>();
	
	public AppController() {
		listaUsuarios.add(new Person("Alin", "alin", "1234"));
		listaUsuarios.add(new Person("Juan", "juan", "1111"));
		listaUsuarios.add(new Person("Manuel", "manuel", "2222"));
	}
	
	@GetMapping(path = "/login", produces = "text/event-stream")
	public Flux<Person> login(@RequestParam(value= "username") String username, @RequestParam(value= "password") String password) {
		List<Person> persons = listaUsuarios.stream().filter(usr -> (usr.getUsername().equals(username)) && (usr.getPassword().equals(password))).collect(Collectors.toList());
		
		Flux<Person> flux = Flux.fromIterable(listaUsuarios);
		return flux;
		
		//Esto debería estar para comprobar que el usuario existe. Si no existe debería devolver un Objeto Flux vacio.
//		if (!persons.get(0).getNombre().isEmpty()) {
			//Flux<Person> flux = Flux.fromIterable(listaUsuarios).delayElements(Duration.ofSeconds(2));
		//}
		
	}
	
	@GetMapping(path="/addUser")
	public String adduser(@RequestParam(value= "name") String name, @RequestParam(value= "username") String username, @RequestParam(value= "password") String password) {
		System.out.println(name);
		listaUsuarios.add(new Person(name, username, password));
		return "Has introducido el usuario: " + name;
	}
	
	
	@DeleteMapping(path="/deleteUser")
	public String deleteUser(@RequestParam(value= "name") String name, @RequestParam(value= "username") String username, @RequestParam(value= "password") String password) {
		listaUsuarios.remove(new Person(name, username, password));
		return "Has borrado el usuario: " + name;
	}
}