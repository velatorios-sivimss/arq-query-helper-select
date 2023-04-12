package com.example.selectquerydemo.controller;

import com.example.selectquerydemo.dto.FiltrosDto;
import com.example.selectquerydemo.service.QueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query-test")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/normal")
    public ResponseEntity<String> selectNormal() {
        String response = queryService.selectNormal();
        return getStringResponseEntity(response);
    }

    @GetMapping("/select-join")
    public ResponseEntity<String> selectConJoin() {
        String response = queryService.selectConJoin();
        return getStringResponseEntity(response);
    }

    @PostMapping("/select-filtros")
    public ResponseEntity<String> selectConFiltros(@RequestBody FiltrosDto filtros) throws Exception {
        String response = queryService.selectConParametrosOpcionales(filtros);
        return getStringResponseEntity(response);
    }

    @GetMapping("/otro-select/{id}")
    public ResponseEntity<String> otroSelect(@PathVariable Long id) {
        String response = queryService.otroSelect(id);
        return getStringResponseEntity(response);
    }

    @GetMapping("/select-con-union")
    public ResponseEntity<String> selectConUnion() {
        String response = queryService.selectConUnion();
        return getStringResponseEntity(response);
    }

    private static ResponseEntity<String> getStringResponseEntity(String response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // todo - Agrregar los endpoint con los ejercicios que se especifican en el archivo EJERCICIOS.md

}
