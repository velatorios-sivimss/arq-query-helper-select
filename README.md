# mssivimss-cat-velatorios
CU008_GestionarVelatorios
Permitir gestionar los velatorios registrados en el sistema.

## `SelectQueryUtil` introducci&oacute;n.

El objetivo de este documento es explicar el funcionamiento de la case que pretende extender la funcionalidad
de la utiler&iacute;a `QueryHelper`. La nueva utile&iacute;a `SelectQueryUtil` expone servicios para poder construir
una senetencia `SELECT` de `SQL`, simplifica la sem&aacute;ntica para el desarrollador, ya que ya no es necesario
escribir en un `String` el valor de la consulta, por ejemplo:

Para escribir una sentencia `SELECT` lo pod&iacute;amos hacer de la siguiente forma:
```java
String query = "select u.id, u.name from user u";
// encriptar la cadena
//...
```

Sin embargo en el ejemplo anterior, aunque todav&iacute;a es posible, la utiler&iacute;a pretende promover su uso,
simplificando la forma y con un mayor control en lo que se pretende consultar, ya que, en el caso del c&oacute;digo 
anterior, es simple y no tenemos tanto problema, pero pensemos en un ejercicio m&aacute;s elaborado, podemos perder
control de caracteres como la `,`, `''`, `"`, etc. Ya que la consulta se va generando de forma din&aacute;mica,
permite tambi&eacute;n pasar par&aacute;metros usando `placeholders`.

## Uso.

Para poder hacer uso de la utiler&iacute;a es necesario crear una instancia de la clase `SelectQueryUtil`:

```java
// ...
SelectQueryUtil queryUtil = new SelectQueryUtil();
// ...
```

La instancia expondr&aacute; los siguientes servicios:

- `select()`: Puede recibir `0` a `n` columnas, si no se pasa un par&aacute;metro se hace la consulta de todos los 
              campos en la tabla.
- `from(nombreDeLaTabla)`
- 