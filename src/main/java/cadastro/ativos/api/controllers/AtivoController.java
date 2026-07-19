package cadastro.ativos.api.controllers;

import cadastro.ativos.api.dtos.AtivoRequest;
import cadastro.ativos.api.dtos.AtivoRequestUpdate;
import cadastro.ativos.api.interfaces.AtivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/cadastros/ativo")
public class AtivoController {

    @Autowired
    private AtivoService ativoService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid AtivoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ativoService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ativoService.findAll());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCode(@PathVariable String codigo){
        return ResponseEntity.status(HttpStatus.OK).body(ativoService.findByCode(codigo));
    }


    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable String codigo,
                                                    @Valid @RequestBody AtivoRequestUpdate request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ativoService.update(codigo, request));

    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deactivate(@PathVariable String codigo) {
        ativoService.deactivate(codigo);
        return ResponseEntity.noContent().build();
    }
}
