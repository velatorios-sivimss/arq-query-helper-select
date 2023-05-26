package com.example.selectquerydemo.service.impl;

import com.example.selectquerydemo.bean.QueryExample;
import com.example.selectquerydemo.dto.ConsultaGeneralRequest;
import com.example.selectquerydemo.dto.FiltrosDto;
import com.example.selectquerydemo.service.QueryService;
import com.example.selectquerydemo.util.SelectQueryUtil;
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

    @Override
    public String selectConUnion() {
        return queryExample.selectConUnion();
    }

    @Override
    public String queryHelperExample() {
        return queryExample.insertQuery();
    }

    @Override
    public String unionAllExample(ConsultaGeneralRequest filtros) {
        SelectQueryUtil queryBeneficiarios = new SelectQueryUtil();
        // todo - recuperar beneficiario que tengan un convenio pagado (ver los estatus)
        //      - la consulta puede ser por los filtros asi que hay que relacionar el:
        //      - rfc
        //      - curp
        //      - folioConvenio
        //      - estatusConvenio

        // Replicar la consulta para empresas
        final String aliasNombreBeneficiario = "nombreBeneficiario";
        final String aliasFechaNacimiento = "fechaNacimiento";
        final String aliasEdad = "edad";
        final String aliasParentesco = "descripcionParentesco";
        queryBeneficiarios.select(
                        recuperarNombrePersona("personaBeneficiario", aliasNombreBeneficiario),
                        "personaBeneficiario.FEC_NAC as " + aliasFechaNacimiento,
                        "TIMESTAMPDIFF(YEAR, personaBeneficiario.FEC_NAC, CURDATE()) as " + aliasEdad,
                        "parentesco.DES_PARENTESCO as " + aliasParentesco
                )
                .from("SVT_CONTRATANTE_BENEFICIARIOS beneficiario")
                .join("SVT_CONTRATANTE_PAQUETE_CONVENIO_PF contratantePaquete",
                        "contratantePaquete.ID_CONTRATANTE_PAQUETE_CONVENIO_PF = beneficiario.ID_CONTRATANTE_PAQUETE_CONVENIO_PF")
                .join("SVT_CONVENIO_PF convenio",
                        "convenio.ID_CONVENIO_PF = contratantePaquete.ID_CONVENIO_PF")
                .join("SVC_PERSONA personaBeneficiario",
                        "personaBeneficiario.ID_PERSONA = beneficiario.ID_PERSONA")
                .join("SVC_PARENTESCO parentesco",
                        "parentesco.ID_PARENTESCO = beneficiario.ID_PARENTESCO");
        crearWhereConFiltros(queryBeneficiarios, filtros, true);

        SelectQueryUtil queryBeneficiariosEmpresa = new SelectQueryUtil();
        queryBeneficiariosEmpresa.select(
                        recuperarNombrePersona("personaBeneficiario", aliasNombreBeneficiario),
                        "personaBeneficiario.FEC_NAC as " + aliasFechaNacimiento,
                        "TIMESTAMPDIFF(YEAR, personaBeneficiario.FEC_NAC, CURDATE()) as " + aliasEdad,
                        "parentesco.DES_PARENTESCO as " + aliasParentesco
                )
                .from("SVT_CONTRATANTE_BENEFICIARIOS beneficiario")
                .join("SVT_CONTRATANTE_PAQUETE_CONVENIO_PF contratantePaquete",
                        "contratantePaquete.ID_CONTRATANTE_PAQUETE_CONVENIO_PF = beneficiario.ID_CONTRATANTE_PAQUETE_CONVENIO_PF")
                .join("SVT_CONVENIO_PF convenio",
                        "convenio.ID_CONVENIO_PF = contratantePaquete.ID_CONVENIO_PF")
                .join("SVT_EMPRESA_CONVENIO_PF empresaContratante",
                        "empresaContratante.ID_CONVENIO_PF = convenio.ID_CONVENIO_PF",
                        "empresaContratante.ID_CONVENIO_PF = contratantePaquete.ID_CONVENIO_PF")
                .join("SVC_PERSONA personaBeneficiario",
                        "personaBeneficiario.ID_PERSONA = beneficiario.ID_PERSONA")
                .join("SVC_PARENTESCO parentesco",
                        "parentesco.ID_PARENTESCO = beneficiario.ID_PARENTESCO");
        crearWhereConFiltros(queryBeneficiariosEmpresa, filtros, false);

        // todo - agregar lo del utf-8 a los otros servicios
        return queryBeneficiarios.unionAll(queryBeneficiariosEmpresa);
    }

    private void crearWhereConFiltros(SelectQueryUtil selectQuery, ConsultaGeneralRequest filtros, boolean isPersona) {
        if (isPersona) {
            if (filtros.getRfc() != null) {
                selectQuery.where("personaContratante.CVE_RFC = :rfc")
                        .setParameter("rfc", filtros.getRfc());
            }
            if (filtros.getCurp() != null) {
                selectQuery.where("personaContratante.CVE_CURP = :curp")
                        .setParameter("curp", filtros.getCurp());
            }
            if (filtros.getNombre() != null) {
                final String concatNombrePersona = "concat(personaContratante.NOM_PERSONA, " +
                        "' ', " +
                        "personaContratante.NOM_PRIMER_APELLIDO, " +
                        "' ', " +
                        "personaContratante.NOM_SEGUNDO_APELLIDO)";
                selectQuery.where(recuperarNombrePersona("personaContratante") + " = :nombre")
                        .setParameter("nombre", filtros.getNombre());
            }
        } else {
            if (filtros.getRfc() != null) {
                selectQuery.where("empresaContratante.DES_RFC = :rfc")
                        .setParameter("rfc", filtros.getRfc());
            }
            if (filtros.getNombre() != null) {
                selectQuery.where("empresaContratante.DES_NOMBRE = :nombreEmpresa")
                        .setParameter("nombreEmpresa", filtros.getNombre());
            }
        }
        if (filtros.getFolioConvenio() != null) {
            selectQuery.where("convenio.DES_FOLIO = :folioConvenio")
                    .setParameter("folioConvenio", filtros.getFolioConvenio());
        }
        if (filtros.getEstatusConvenio() != null) {
            selectQuery.where("convenio.ID_ESTATUS_CONVENIO = :estatusConvenio")
                    .setParameter("estatusConvenio", filtros.getEstatusConvenio());
        }
    }

    private static String recuperarNombrePersona(String aliasTabla, String aliasCampo) {
        return recuperarNombrePersona(aliasTabla) +
                "as " +
                aliasCampo;
    }

    private static String recuperarNombrePersona(String aliasTabla) {
        return "concat(" +
                aliasTabla + "." + "NOM_PERSONA" + ", " +
                "' ', " +
                aliasTabla + "." + "NOM_PRIMER_APELLIDO" + ", " +
                "' ', " +
                aliasTabla + "." + "NOM_SEGUNDO_APELLIDO" + ") ";
    }
}
