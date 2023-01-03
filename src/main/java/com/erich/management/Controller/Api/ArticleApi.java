package com.erich.management.Controller.Api;

import com.erich.management.Dto.ArticleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erich.management.Utils.Constants.Path.APP_ROOT;

@Tag(name = APP_ROOT + "/Articles")
public interface ArticleApi {

    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Devuelve la lista de artículos.",description = "Este método le permite buscar y devolver la lista de artículos que existen en la bd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "La lista de articulos")
    })
    List<ArticleDto> listArticles();

    @PostMapping(value = APP_ROOT + "/articles/create",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guarda el articulo",description = "Create el Articulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El objeto articulo crea/modifica"),
            @ApiResponse(responseCode = "400",description = "El objeto del artículo no es válido")
    })
    ResponseEntity<?> save(@RequestBody ArticleDto articleDto);

    @GetMapping(value = APP_ROOT + "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscamos articulo por ID",description = "Este método le permite buscar un artículo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El artículo fue encontrado en la base de datos."),
            @ApiResponse(responseCode = "400",description = "El artículo no existe en la base de datos.")
    })
    ResponseEntity<?> search(@PathVariable Long id);

    @GetMapping(value = APP_ROOT + "/articles/filter/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscamos articulo por codigo",description = "Este método le permite buscar un artículo por su codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El artículo fue encontrado en la base de datos."),
            @ApiResponse(responseCode = "400",description = "El artículo no existe en la base de datos con su code.")
    })
    ResponseEntity<?> findCodeArticle(@PathVariable String id);

    @DeleteMapping(value = APP_ROOT + "/article/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminamos articulo",description = "Este método le permite eliminar el articulo de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "El artículo fue eliminado")
    })
    void delete(@PathVariable Long id);
}
