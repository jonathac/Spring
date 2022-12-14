package br.com.jonatha.projeto.controller;

import br.com.jonatha.projeto.service.CategoryService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jonatha.projeto.domain.Category;
import br.com.jonatha.projeto.dto.CategoriaDTO;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

  @Autowired
	CategoryService categoryService;

  @ApiOperation("Buscar por ID")
  @GetMapping("/{id}")
  public ResponseEntity<Category> find(@PathVariable Integer id) {
    Category category = categoryService.find(id);
    return ResponseEntity.ok().body(category);
  }

  @ApiOperation("Inserir nova Categoria")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping()
  public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
    Category obj = categoryService.fromDTO(objDto);
    obj = categoryService.insert(obj);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @ApiOperation("Atualiza categoria, buscando por ID")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto,
      @PathVariable Integer id) {
    Category obj = categoryService.fromDTO(objDto);
    obj.setId(id);
    obj = categoryService.update(obj);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Realiza deleção da categoria, buscando por id")
  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Category> delete(@PathVariable Integer id) {
    categoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Lista todas categorias")
  @GetMapping()
  public ResponseEntity<List<CategoriaDTO>> findAll() {
    List<Category> list = categoryService.findAll();
    List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj))
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(listDto);
  }

  @ApiOperation("Exibe categorias com paginação")
  @GetMapping("/page")
  public ResponseEntity<Page<CategoriaDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
      @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    Page<Category> list = categoryService.findPage(page, linesPerPage, orderBy, direction);
    Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
    return ResponseEntity.ok().body(listDto);
  }

}
