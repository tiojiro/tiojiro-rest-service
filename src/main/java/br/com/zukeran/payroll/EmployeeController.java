package br.com.zukeran.payroll;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
@Api(description="Controlador de Employees")
class EmployeeController {

	private final EmployeeRepository repository;
	
	private final EmployeeResourceAssembler assembler;

	EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	// Aggregate root

	@ApiOperation(value = "Listar todos os Employees")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
	        @ApiResponse(code = 401, message = "Você não tem autorização para ver a lista"),
	        @ApiResponse(code = 403, message = "O acesso a lista está proibido"),
	        @ApiResponse(code = 404, message = "Lista não encontrada")
	})
	@GetMapping("/employees")
	Resources<Resource<Employee>> all() {

		List<Resource<Employee>> employees = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());

		return new Resources<>(employees,
			linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	@ApiOperation(value = "Adicionar novo Employee")
	@PostMapping("/employees")
	ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {

		Resource<Employee> resource = assembler.toResource(repository.save(newEmployee));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Single item

	@ApiOperation(value = "Exibe Employee por ID")
	@GetMapping("/employees/{id}")
	Resource<Employee> one(@PathVariable Long id) {

		Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		return assembler.toResource(employee);
	}

	@ApiOperation(value = "Sobreescreve um Employee por ID")
	@PutMapping("/employees/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) throws URISyntaxException {

		Employee updatedEmployee = repository.findById(id)
			.map(employee -> {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return repository.save(employee);
			})
			.orElseGet(() -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});

		Resource<Employee> resource = assembler.toResource(updatedEmployee);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	@ApiOperation(value = "Apagar Employee por ID")
	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}