package com.example.selectquerydemo.service.impl;

import com.example.selectquerydemo.bean.QueryExample;
import com.example.selectquerydemo.dto.BeanValidator;
import com.example.selectquerydemo.dto.FiltrosDto;
import com.example.selectquerydemo.service.QueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryServiceImpl implements QueryService {
    private final QueryExample queryExample;

    public QueryServiceImpl(QueryExample queryExample) {
        this.queryExample = queryExample;
    }

    @Override
    public String selectNormal() {
        return queryExample.selectNormal();
    }

    @Override
    public String selectConJoin() {
        return queryExample.selectConLeftJoin();
    }

    @Override
    public String selectConParametrosOpcionales(FiltrosDto filtros) throws Exception {

//        List<String> errores = BeanValidator.validate(filtros);
//
//        if (!errores.isEmpty()) {
//            throw new Exception("Error en la validacion del request");
//        }

        return queryExample.consultaConFiltrosOpcionales(filtros);
    }


}
