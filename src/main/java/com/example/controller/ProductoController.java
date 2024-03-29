package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Producto;
import com.example.services.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;



    // El método responde a una request del tipo
    // http://localhost:8080/productos?page=0&size=3
    // Si no se especifica page y size, entonces que devuelva
    // los productos ordenados por el nombre, por ejemplo

    @GetMapping
    public ResponseEntity<List<Producto>> findAll(
        @RequestParam(name = "page", required = false)Integer page,
        @RequestParam(name = "size", required = false)Integer size){
        
        ResponseEntity<List<Producto>> responseEntity = null;
        Sort sortByName = Sort.by("name");
        Sort sortById = Sort.by("id");
        List<Producto> productos = new ArrayList<>();

        // Primero comprobamos si han enviado page y size
        if (page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortByName);
            Page<Producto> pageProducto = productoService.findAll(pageable);
            productos = pageProducto.getContent();
            responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
            
        } else {
            
            // Solo ordenamiento

            productos = productoService.findAll(sortById);
            responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
            

        }

        return responseEntity;

    }



    // Método que persiste un producto
    // y valida que el producto esté bien formado

    @PostMapping
    public ResponseEntity<Map<String,Object>> saveProduct(@Valid @RequestBody Producto producto,
                            BindingResult validationResults ){

        Map<String,Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity = null;

        // Lo primero que hay que hacer es comprobar si el producto tiene errores
        if (validationResults.hasErrors()) {

            List<String> errores = new ArrayList<>();
            List<ObjectError> objectErrors = validationResults.getAllErrors();

            objectErrors.forEach(objectError -> errores.add(objectError.getDefaultMessage()));
            
            responseAsMap.put("errores",errores);
            responseAsMap.put("producto mal formado", producto);

            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        } 
        // No hay errores en el producto, pues a persistir el producto

        try {

            Producto productoPersistido = productoService.save(producto);
            String successMessage = "El producto se ha persistido exitosamente";
            responseAsMap.put("Success Message", successMessage);
            responseAsMap.put("Producto Persistido", productoPersistido);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.CREATED);

            
        } catch (DataAccessException e) {
            
            String error = "Error al intentar persistir el producto y la causa más probable es: "
            + e.getMostSpecificCause();

            responseAsMap.put("error", error);
            responseAsMap.put("producto que se ha intentado persistir", producto);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        

        
        return responseEntity;

    }
    

    // Método que actualiza un producto cuyo id recibe como parámetro
    // y valida que el producto esté bien formado

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateProduct(@Valid @RequestBody Producto producto,
                            BindingResult validationResults,
                            @PathVariable(name = "id", required = true) Integer idProducto ){

        Map<String,Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity = null;

        // Lo primero que hay que hacer es comprobar si el producto tiene errores
        if (validationResults.hasErrors()) {

            List<String> errores = new ArrayList<>();
            List<ObjectError> objectErrors = validationResults.getAllErrors();

            objectErrors.forEach(objectError -> errores.add(objectError.getDefaultMessage()));
            
            responseAsMap.put("errores",errores);
            responseAsMap.put("producto mal formado", producto);

            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        } 
        // No hay errores en el producto, pues a actualizar el producto

        try {
            producto.setId(idProducto);
            Producto productoActualizado = productoService.save(producto);
            String successMessage = "El producto se ha actualizado exitosamente";
            responseAsMap.put("Success Message", successMessage);
            responseAsMap.put("Producto Actualizado", productoActualizado);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);

            
        } catch (DataAccessException e) {
            
            String error = "Error al intentar actualizar el producto y la causa más probable es: "
            + e.getMostSpecificCause();

            responseAsMap.put("error", error);
            responseAsMap.put("producto que se ha intentado actualizar", producto);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        

        
        return responseEntity;

    }
    

    // Método que recupera un producto por el id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findProductById(@PathVariable(name = "id",
                required = true) Integer idProducto) throws IOException{

        Map<String,Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity = null;

        try {
            Producto producto = productoService.findById(idProducto);

            if (producto != null) {

                String successMessage = "Producto con id " + idProducto + " encontrado";
                responseAsMap.put("successMessage", successMessage);
                responseAsMap.put("producto", producto);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
                
            } else {

                String errorMessage = "Producto con id " + idProducto + " no encontrado";
                responseAsMap.put("errorMessage", errorMessage);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);
                
            }
         //   
        } catch (DataAccessException e) {

            String error = "Se ha producido un error grave al buscar el producto con id " + idProducto +
            " y la causa más probable es: " + e.getMostSpecificCause();

            responseAsMap.put("error", error);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
            
        }

        return responseEntity;
    }

    // Método que elimina un producto por el id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteProductById(@PathVariable(name = "id",
                                                                required = true) Integer idProducto){
        Map<String,Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity = null;

        try {
            productoService.delete(productoService.findById(idProducto));
            // deberíamos comprobar si lo ha encontrado primero antes de cantar victoria pero, omitimos.
            String successMessage = "Producto con id " + idProducto + " eliminado con éxito";
            responseAsMap.put("successMessage", successMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);

        } catch (DataAccessException e) {
            String error = "Se ha producido un error grave al eliminar el producto con id " + idProducto +
            " y la causa más probable es: " + e.getMostSpecificCause();

            responseAsMap.put("error", error);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,HttpStatus.INTERNAL_SERVER_ERROR);
            
        }


        return responseEntity;
    }


}
