package com.example.selectquerydemo.service;

import com.example.selectquerydemo.dto.FiltrosDto;

public interface QueryService {
    String selectNormal();

    String selectConJoin();

    String selectConParametrosOpcionales(FiltrosDto filtros) throws Exception;

    String otroSelect(Long id);

    String selectConUnion();
}
