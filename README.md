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
String query="select u.id, u.name from user u";
// encriptar la cadena
//...
```

Sin embargo en el ejemplo anterior, aunque todav&iacute;a es posible, la utiler&iacute;a pretende promover su uso,
simplificando la forma y con un mayor control en lo que se pretende consultar, ya que, en el caso del c&oacute;digo
anterior, es simple y no tenemos tanto problema, pero pensemos en un ejercicio m&aacute;s elaborado, podemos perder
control de caracteres como la `,`, `''`, `"`, etc. Ya que la consulta se va generando de forma din&aacute;mica,
permite tambi&eacute;n pasar par&aacute;metros usando `placeholders`.

## Uso

Crea una instancia de la clase `SelectQueryUtil`:
```java
SelectQueryUtil selectQuery = new SelectQueryUtil();
```

Agrega las columnas que quieres seleccionar:
```java
selectQuery.select("nombre", "apellido", "edad");
```

Agrega las tablas de las que quieres obtener los datos:
```java
selectQuery.from("nombre_de_la_tabla");
```

Agrega las condiciones que quieres que cumplan los registros a seleccionar:
```java
selectQuery.where("edad > 18", "ciudad = 'CDMX'");
```

Opcionalmente se pueden agregar `placeholders` para poder agregar par&aacute;metros de forma din&aacute;mica:
```java
selectQuery.where("edad > :edad", "ciudad = :nombreCiudad")
        .setParameter("edad", 18)
        .setParameter("nombreCiudad", "CDMX");
```

Al agregar las condiciones en la funci&oacute;n `where(condiciones...)`, estas se agruparan con operadores `AND`, si
se desea agregan operadores `OR`, usando la funci&oacute;n `or(condicion)`, de igual forma se pueden seguir agregando
condiciones con el operador `AND` usando la funci&oacute;n `and(condicion)` se pueden agregar de forma independiente, 
sin embargo al usar las funciones `or(condicion)` o `and(condicion)`, `where(condiciones...)` debe recibir una 
condici&oacute;n de forma obligatoria, de lo contrario de arroja un error, esto se logra de la siguiente forma:

```java 
// ...
// esto arrojara un error
selectQuery.where()
        .or("condicion_1")
        .and("condicion_2");
```
```java 
// ...
selectQuery.where("condicion_1")
        .or("condicion_2")
        .and("condicion_3");
```
```java
// ...
// si se agregan las condiciones en el where se agruparan con el operador AND
selectQuery.where("condicion_1", 
        "condicion_2", 
        "condicion_3");
```

Si quieres hacer un join con otra tabla, utiliza los métodos `leftJoin`, `innerJoin` o `join`, seguidos de `on` para agregar
las condiciones del join:
```java
selectQuery.leftJoin("direcciones", "direcciones.ciudad = 'Bogotá'", "usuarios.id = direcciones.id_usuario")
```

La funci&oacute;n `join(tabla, on...)`, tiene una funcionalidad parecida a `where(condciones...)`, el par&aacute;metro
`on...`, recibe una lista de condiciones y un `JOIN` puede ser representado de varias formas, a continuaci&oacute;n se
```java
selectQuery.join("direcciones")
        .on("direcciones.ciudad = 'Bogotá'", "usuarios.id = direcciones.id_usuario");
```

Si quieres ordenar los resultados, utiliza el método `orderBy`:
```java
selectQuery.orderBy("apellido DESC");
```

Si quieres agrupar los resultados, utiliza el método groupBy:
```java
selectQuery.groupBy("ciudad");
```

Finalmente, llama al método build para obtener la consulta SQL resultante:
```java
String query = selectQuery.build();
```

## M&eacute;todos
- `public SelectQueryUtil select(String... columnas)`: agrega las columnas que quieres seleccionar. Si no se pasan 
argumentos, se hace un `select * from user`.
- `public SelectQueryUtil from(String... tabla)`: agrega la tabla o las tablas de las que quieres obtener los datos.
- `public SelectQueryUtil where(String... condiciones)`: agrega las condiciones que quieres que cumplan los registros 
a seleccionar. Para poder combinar `where` con un `or()` y/o un `and()`, es necesario agregar por lo menos, una 
condici&oacute;n al `where()`.
- `public SelectQueryUtil and(String condicion)`: agrega una condición con el operador l&oacute;gico `AND`.
- `public SelectQueryUtil or(String condicion)`: agrega una condici&oacute;n con el operador l&oacute;gico `OR`.
- `public SelectQueryUtil leftJoin(String tabla, String... on)`: agrega un `join` `LEFT JOIN` con otra tabla.
- `public SelectQueryUtil innerJoin(String tabla, String on)`: agrega un `join` `INNER JOIN` con otra tabla.
- `public SelectQueryUtil join(String tabla, String on)`: agrega un `JOIN` con otra tabla.
- `public SelectQueryUtil on(String... condiciones)`: agrega las condiciones para el `JOIN`.
- `public SelectQueryUtil setParameter(String nombre, Object valor)`: agrega un par&aacute;metro a la consulta.
- `public SelectQueryUtil orderBy(String columna)`: agrega una columna para ordenar los resultados.
- `public SelectQueryUtil groupBy(String columna)`: agrega una columna para agrupar los resultados.
- `public String build()`: devuelve la consulta `SQL` resultante.

## Ejemplo
A continuaci&oacute;n se muestran algunos ejemplos del uso de la utiler&iacute;a:

```java
SelectQueryUtil selectQuery = new SelectQueryUtil();
selectQuery.select("nombre", "apellido", "edad");
  .from("usuarios");
  .leftJoin("direcciones", "usuarios.id = direcciones
  .where("edad > 18", "ciudad = 'Bogotá'");
```

O tambi&eacute;n se puede armar el query de la siguiente forma:

```java
SelectQueryUtil selectQuery = new SelectQueryUtil();
selectQuery.select("nombre", "apellido", "edad");
selectQuery.from("usuarios");
selectQuery.leftJoin("direcciones", "usuarios.id = direcciones
selectQuery.where("edad > 18", "ciudad = 'Bogotá'");
```

## Ejercicios

Para poder ver en acci&oacute;n la utiler&iacute;a, se han agregado varios ejercicios para poder poner 
en pr&aacute;ctica lo que se menciona en el documento, en el siguiente [readme](EJERCICIOS.md) est&aacute; la lista de ejercicios.