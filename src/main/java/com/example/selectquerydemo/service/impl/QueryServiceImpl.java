package com.example.selectquerydemo.service.impl;

import com.example.selectquerydemo.bean.QueryExample;
import com.example.selectquerydemo.dto.FiltrosDto;
import com.example.selectquerydemo.service.QueryService;
import org.springframework.stereotype.Service;

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

        return queryExample.consultaConFiltrosOpcionales(filtros);
    }

    @Override
    public String otroSelect(Long id) {
        return queryExample.selectConParametrosJoin(id);
    }


}
