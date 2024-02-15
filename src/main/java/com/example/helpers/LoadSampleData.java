package com.example.helpers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Presentacion;
import com.example.entities.Producto;
import com.example.services.PresentacionService;
import com.example.services.ProductoService;

@Configuration
public class LoadSampleData {

    @Bean
    public CommandLineRunner saveSampleData(ProductoService productoService,
            PresentacionService presentacionService)
                                            {
        // Este método tiene que devolver un objeto de este tipo
        // El Bean creará el objeto por ti
        // Necesita del servicio de Producto y de Presentación

        return datos -> {
            presentacionService.save(Presentacion.builder()
                                    .name("unidad")
                                    .build());

            presentacionService.save(Presentacion.builder()
            .name("docena")
            .build());

            productoService.save(Producto.builder()
            .name("rezma de papel")
            .description("20 manos de papel")
            .stock(10)
            .price(3.75)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("cartas")
            .description("Baraja española de Fournier")
            .stock(10)
            .price(1.0)
            .presentacion(presentacionService.findById(2)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("guitarra de juguete")
            .description("Colección Coco Disney")
            .stock(5)
            .price(4.5)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("teclado para computadora")
            .description("de patitos de goma")
            .stock(5)
            .price(15.0)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("teclado para laptop")
            .description("inalámbrico")
            .stock(5)
            .price(40.0)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("bocinas bluetooth")
            .description("disfraz")
            .stock(5)
            .price(15.0)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("lapices 2b")
            .description("grafito")
            .stock(4)
            .price(1.5)
            .presentacion(presentacionService.findById(2)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("plumas color azul")
            .description("de gel :)")
            .stock(10)
            .price(2.0)
            .presentacion(presentacionService.findById(2)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("monitor dell 15p")
            .description("reacondicionado")
            .stock(5)
            .price(40.0)
            .presentacion(presentacionService.findById(1)) 
            .build()); 

            productoService.save(Producto.builder()
            .name("cargador samsung")
            .description("tipo C")
            .stock(10)
            .price(10.0)
            .presentacion(presentacionService.findById(1))  
            .build()); 

            productoService.save(Producto.builder()
            .name("mouse básico")
            .description("ilustraciones patitos de goma")
            .stock(5)
            .price(5.0)
            .presentacion(presentacionService.findById(1))  
            .build()); 

            productoService.save(Producto.builder()
            .name("patito de goma")
            .description("hace cuak")
            .stock(200)
            .price(2.5)
            .presentacion(presentacionService.findById(1))  
            .build());

            productoService.save(Producto.builder()
            .name("narices de payaso")
            .description("color rojo")
            .stock(50)
            .price(2.5)
            .presentacion(presentacionService.findById(1))  
            .build());
             

        };

    }

}
