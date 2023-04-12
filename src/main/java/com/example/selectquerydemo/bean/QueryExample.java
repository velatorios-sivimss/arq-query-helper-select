package com.example.selectquerydemo.bean;

import com.example.selectquerydemo.dto.FiltrosDto;
import com.example.selectquerydemo.util.SelectQueryUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryExample {

    public String selectNormal() {

//        String query = "SELECT " +
//                "ID_CAPILLA AS idCapilla, " +
//                "NOM_CAPILLA AS nombre " +
//                "FROM SVC_CAPILLA";
        SelectQueryUtil queryUtil = new SelectQueryUtil();

        // ejemplo sin columnas
//        queryUtil.select()
//                .from("SVC_CAPILLA");

//        queryUtil.select("ID_CAPILLA AS idCapilla",
//                "NOM_CAPILLA as nombre")
//                .from("SVC_CAPILLA");

        queryUtil.select("codigoPostal", "id")
                .from("svc_cp cp")
                .where("cp.codigoPostal = :codigoPostal")
                .limit(1)
                .setParameter("codigoPostal", "123123");
        String query = queryUtil.build();
        return query;
    }

    public String consultaConSelectAnidado() {
        SelectQueryUtil innerSelectQuery = new SelectQueryUtil();
        SelectQueryUtil outerSelectQuery = new SelectQueryUtil();
        innerSelectQuery
                .select("rfp.ID_FUNCIONALIDAD")
                .from("SVC_ROL_FUNCIONALIDAD_PERMISO rfp")
                .where("rfp.CVE_ESTATUS = 0",
                        "rfp.ID_ROL = :idRol")
                .or("rfp.cve_estatus = 1")
                .setParameter("idRol", 11)
                .groupBy("rfp.ID_FUNCIONALIDAD");

        outerSelectQuery.select("sm.ID_FUNCIONALIDAD",
                        "sm.DES_TITULO")
                .from("SVT_MENU sm")
                .where("IFNULL(sm.ID_MODULO_PADRE, 0) > 0",
                        "IFNULL(sm.ID_FUNCIONALIDAD, 0) > 0",
                        "sm.id_funcionalidad NOT IN (" + innerSelectQuery.build() + ")");
        String query = outerSelectQuery.build();
        return query;
    }

    public String selectConLeftJoin() {
        SelectQueryUtil selectQuery = new SelectQueryUtil();
        selectQuery.select("men.ID_FUNCIONALIDAD as idFuncionalidad",
                        "men.ID_MODULO as idModulo",
                        "men.ID_MODULO_PADRE as idModuloPadre",
                        "men.DES_TITULO as titulo")
                .from("SVT_MENU men")
                .leftJoin("SVC_ROL_FUNCIONALIDAD per")
                .on("per.ID_FUNCIONALIDAD = mem.ID_FUNCIONALIDAD",
                        "per.ID_ROL = :idRol")
                .or("per.CVE_ESTATUS = :estatus")
                .and("otra_condicion = 1")
                .leftJoin("SVT_ROL rol", "rol.ID_ROL = per.ID_ROL")
                .where("rol.id = :idRol",
                        "rol.id in (1,2,3)")
                .setParameter("idRol", 11)
                .setParameter("estatus", 1)

                .groupBy("men.ID_FUNCIONALIDAD");
        String query = selectQuery.build();
        System.out.println("************************");
        System.out.println(query);
        return query;
    }

    public String selectConMultiplesJoins() {
        SelectQueryUtil queryUtil = new SelectQueryUtil();
        queryUtil.select("SA.ID_ARTICULO AS idArticulo",
                        "SA.ID_CATEGORIA_ARTICULO AS idCategoriaArticulo",
                        "CA.DES_CATEGORIA_ARTICULO AS categoriaArticulo",
                        "SA.ID_TIPO_ARTICULO AS idTipoArticulo",
                        "TA.DES_TIPO_ARTICULO AS tipoArticulo",
                        "SA.ID_TIPO_MATERIAL AS idTipoMaterial",
                        "TM.DES_TIPO_MATERIAL AS tipoMaterial",
                        "SA.ID_TAMANIO AS idTamanio",
                        "T.DES_TAMANIO AS tamanio",
                        "SA.ID_CLASIFICACION_PRODUCTO AS idClasificacionProducto",
                        "CP.DES_CLASIFICACION_PRODUCTO AS clasificacionProducto",
                        "SA.CVE_ESTATUS AS estatus",
                        "SA.DES_MODELO_ARTICULO AS modeloArticulo",
                        "SA.DES_ARTICULO AS desArticulo",
                        "SAM.NUM_LARGO AS largo",
                        "SAM.NUM_ANCHO AS ancho",
                        "SAM.NUM_ALTO AS alto",
                        "SA.ID_PART_PRESUPUESTAL AS idPartPresupuestal",
                        "PP.DES_PART_PRESUPUESTAL AS partPresupuestal",
                        "SA.ID_CUENTA_PART_PRESU AS idCuentaPartPresupuestal",
                        "CC.NUM_CUENTA_PART_PRESU AS numCuentaPartPresupuestal",
                        "SA.ID_PRODUCTOS_SERVICIOS AS idProductosServicios",
                        "CPS.DES_PRODUCTOS_SERVICIOS AS productoServicios ")
                .from("SVT_ARTICULO SA")
                .innerJoin("SVC_CATEGORIA_ARTICULO CA", "SA.ID_CATEGORIA_ARTICULO = CA.ID_CATEGORIA_ARTICULO")
                .innerJoin("SVC_TIPO_ARTICULO TA", "SA.ID_TIPO_ARTICULO = TA.ID_TIPO_ARTICULO")
                .innerJoin("SVC_TIPO_MATERIAL TM", "SA.ID_TIPO_MATERIAL = TM.ID_TIPO_MATERIAL")
                .innerJoin("SVC_TAMANIO T", "SA.ID_TAMANIO = T.ID_TAMANIO")
                .innerJoin("SVC_CLASIFICACION_PRODUCTO CP", "SA.ID_CLASIFICACION_PRODUCTO = CP.ID_CLASIFICACION_PRODUCTO")
                .innerJoin("SVT_ARTICULO_MEDIDA SAM", "SA.ID_ARTICULO = SAM.ID_ARTICULO")
                .innerJoin("SVC_PARTIDA_PRESUPUESTAL PP", "SA.ID_PART_PRESUPUESTAL = PP.ID_PART_PRESUPUESTAL")
                .innerJoin("SVC_CUENTA_PART_PRESU CC", "SA.ID_CUENTA_PART_PRESU = CC.ID_CUENTA_PART_PRESU")
                .innerJoin("SVC_CLAVES_PRODUCTOS_SERVICIOS CPS", "SA.ID_PRODUCTOS_SERVICIOS = CPS.ID_PRODUCTOS_SERVICIOS");


//        String testQuery = "SELECT  " +
//                "SA.ID_ARTICULO AS idArticulo, " +
//                "SA.ID_CATEGORIA_ARTICULO AS idCategoriaArticulo, " +
//                "CA.DES_CATEGORIA_ARTICULO AS categoriaArticulo, " +
//                "SA.ID_TIPO_ARTICULO AS idTipoArticulo, " +
//                "TA.DES_TIPO_ARTICULO AS tipoArticulo, " +
//                "SA.ID_TIPO_MATERIAL AS idTipoMaterial, " +
//                "TM.DES_TIPO_MATERIAL AS tipoMaterial, " +
//                "SA.ID_TAMANIO AS idTamanio, " +
//                "T.DES_TAMANIO AS tamanio, " +
//                "SA.ID_CLASIFICACION_PRODUCTO AS idClasificacionProducto, " +
//                "CP.DES_CLASIFICACION_PRODUCTO AS clasificacionProducto, " +
//                "SA.CVE_ESTATUS AS estatus, " +
//                "SA.DES_MODELO_ARTICULO AS modeloArticulo, " +
//                "SA.DES_ARTICULO AS desArticulo, " +
//                "SAM.NUM_LARGO AS largo, " +
//                "SAM.NUM_ANCHO AS ancho, " +
//                "SAM.NUM_ALTO AS alto, " +
//                "SA.ID_PART_PRESUPUESTAL AS idPartPresupuestal, " +
//                "PP.DES_PART_PRESUPUESTAL AS partPresupuestal, " +
//                "SA.ID_CUENTA_PART_PRESU AS idCuentaPartPresupuestal, " +
//                "CC.NUM_CUENTA_PART_PRESU AS numCuentaPartPresupuestal, " +
//                "SA.ID_PRODUCTOS_SERVICIOS AS idProductosServicios, " +
//                "CPS.DES_PRODUCTOS_SERVICIOS AS productoServicios " +
//                "FROM SVT_ARTICULO SA  " +
//                "INNER JOIN SVC_CATEGORIA_ARTICULO CA ON SA.ID_CATEGORIA_ARTICULO = CA.ID_CATEGORIA_ARTICULO  " +
//                "INNER JOIN SVC_TIPO_ARTICULO TA ON SA.ID_TIPO_ARTICULO = TA.ID_TIPO_ARTICULO " +
//                "INNER JOIN SVC_TIPO_MATERIAL TM ON SA.ID_TIPO_MATERIAL = TM.ID_TIPO_MATERIAL " +
//                "INNER JOIN SVC_TAMANIO T ON SA.ID_TAMANIO = T.ID_TAMANIO  " +
//                "INNER JOIN SVC_CLASIFICACION_PRODUCTO CP ON SA.ID_CLASIFICACION_PRODUCTO = CP.ID_CLASIFICACION_PRODUCTO " +
//                "INNER JOIN SVT_ARTICULO_MEDIDA SAM ON SA.ID_ARTICULO = SAM.ID_ARTICULO " +
//                "INNER JOIN SVC_PARTIDA_PRESUPUESTAL PP ON SA.ID_PART_PRESUPUESTAL = PP.ID_PART_PRESUPUESTAL  " +
//                "INNER JOIN SVC_CUENTA_PART_PRESU CC ON SA.ID_CUENTA_PART_PRESU = CC.ID_CUENTA_PART_PRESU " +
//                "INNER JOIN SVC_CLAVES_PRODUCTOS_SERVICIOS CPS ON SA.ID_PRODUCTOS_SERVICIOS = CPS.ID_PRODUCTOS_SERVICIOS ";
//        System.out.println(testQuery);
        final String query = queryUtil.build();
        System.out.println(query);
        return query;
    }


    /**
     * @param request
     * @return
     */
    public String consultaConFiltrosOpcionales(FiltrosDto request) {
        SelectQueryUtil selectQueryUtil = new SelectQueryUtil();

        selectQueryUtil
                .select("ID_CAPILLA AS idCapilla",
                        "NOM_CAPILLA AS nombre",
                        "CAN_CAPACIDAD AS capacidad",
                        "NUM_LARGO AS largo",
                        "NUM_ALTO AS alto",
                        "CVE_ESTATUS AS estatus",
                        "velatorio.ID_VELATORIO AS idVelatorio",
                        "velatorio.NOM_VELATORIO AS nombreVelatorio")
                .from("SVC_CAPILLA capilla", "otra tabla")
                .join("SVC_VELATORIO velatorio", "capilla.ID_VELATORIO = velatorio.ID_VELATORIO");

        if (request.getId() != null) {
            selectQueryUtil.where("capilla.ID_CAPILLA = :idCapilla")
                    .setParameter("idCapilla", request.getId());
        }
        if (request.getDescripcion() != null) {
            selectQueryUtil.where("capilla.NOM_CAPILLA = :nombreCapilla")
                    .setParameter("nombreCapilla", request.getDescripcion());
        }
        if (request.getIdOtraTabla() != null) {
            selectQueryUtil.where("capilla.ID_VELATORIO = :idVelatorio")
                    .setParameter("idVelatorio", request.getIdOtraTabla());
        }

        selectQueryUtil
                .orderBy("ID_CAPILLA");

        Map<String, Object> parametros = new HashMap<>();
        // todo - agregar la funcionalidad para encriptar en el selectQueryUtil
        String query = selectQueryUtil.build(); // .encrypt()
        return query;
    }

    /**
     * Crea la consulta para recuperar una capilla por su identificador
     *
     * @param idCapilla
     * @return
     */
    public String selectConParametrosJoin(Long idCapilla) {
        SelectQueryUtil selectQueryUtil = new SelectQueryUtil();
        selectQueryUtil.select("ID_CAPILLA AS idCapilla",
                        "NOM_CAPILLA AS nombre",
                        "CAN_CAPACIDAD AS capacidad",
                        "NUM_LARGO AS largo",
                        "NUM_ALTO AS alto",
                        "CVE_ESTATUS AS estatus",
                        "velatorio.ID_VELATORIO AS idVelatorio",
                        "velatorio.NOM_VELATORIO AS nombreVelatorio ")
                .from("SVC_CAPILLA capilla")
                .join("SVC_VELATORIO velatorio", "capilla.ID_VELATORIO = velatorio.ID_VELATORIO")
                .where("ID_CAPILLA = :idCapilla")
                .setParameter("idCapilla", idCapilla)
                .orderBy("ID_CAPILLA asc");

        Map<String, Object> parametros = new HashMap<>();
        String query = selectQueryUtil.build();

        return query;
    }

    /**
     * Ejemplo para ver como se puede usar la funci&oacute;n <b>{@code union(SelectQueryUtil)}</b>
     *
     * @return
     */
    public String selectConUnion() {
        SelectQueryUtil primerQuery = new SelectQueryUtil();
        primerQuery.select("columna1", "columna2", "columna3")
                .from("tabla_1")
                .where("columna_1 = 1")
                .or("columna_2",
                        "columna_3 = 3",
                        "columna_4 = :columna")
                .innerJoin("tabla_2", "tabla_2.id = tabla_1.id")
                .setParameter("columna", 12);
        SelectQueryUtil segundoQuery = new SelectQueryUtil();
        segundoQuery.select("columna1", "columna2")
                .from("tabla_3")
                .where("tabla_4.estatus = 1")
                .innerJoin("tabla_5", "tabla_4.id = tabla_5.id");

        String query = primerQuery.union(segundoQuery);
        return query;
    }

}
