# Ejercicios

1. Crea una consulta SELECT que seleccione todos los registros de una tabla llamada "clientes".
```java
SelectQuery selectQuery = new SelectQuery();
selectQuery.select()
        .from("clientes");
System.out.println(selectQuery.build());
```
2. Crea una consulta SELECT que seleccione solamente los registros de la tabla "clientes" donde el valor de la 
columna "edad" sea mayor que 25.
```java
SelectQuery selectQuery = new SelectQuery(); 
selectQuery.select()
        .from("clientes")
        .where("edad > :edad")
        .setParameter("edad", 25);
System.out.println(selectQuery.build());
```

3. Crea una consulta SELECT que seleccione los registros de la tabla "pedidos" donde el valor de la columna "fecha" 
sea igual a '2022-03-22'.
```java
SelectQuery selectQuery = new SelectQuery();
selectQuery.select()
        .from("pedidos")
        .where("fecha = :fecha")
        .setParameter("fecha", "2022-03-22");
System.out.println(selectQuery.build());
```

4. Crea una consulta `SELECT` que seleccione solamente las columnas `nombre` y `apellido` de la tabla `clientes`.
```java
SelectQuery selectQuery = new SelectQuery();
selectQuery.select("nombre", "apellido")
        .from("clientes");
System.out.println(selectQuery.build());
```

5. Crea una consulta `SELECT` que seleccione todos los registros de la tabla `productos` ordenados por el valor de la 
columna `precio` en orden `ascendente`.
```java
SelectQuery selectQuery = SelectQuery(); 
selectQuery.select()
        .from("productos")
        .orderBy("precio ASC");
System.out.println(selectQuery.build());
```

6. Crea una consulta `SELECT` que seleccione todos los registros de la tabla `pedidos` que tengan un valor de la 
columna `cantidad` mayor que `10` y un valor de la columna `fecha` posterior a `'2022-01-01'`.
```java
SelectQuery selectQuery = new SelectQuery();
selectQuery.select()
        .from("pedidos")
        .where("cantidad > :cantidad")
        .and("fecha > :fecha")
        .setParameter("cantidad", 10)
        .setParameter("fecha", "01-01-2023")
System.out.println(selectQuery.build());
```

6. Supongamos que tenemos dos tablas: orders y order_items. La tabla orders tiene una columna id como clave primaria y 
la tabla order_items tiene una columna order_id que hace referencia a la columna id de la tabla orders. 
Queremos obtener el total de ventas por cada orden. 

```java
Copy code
SelectQuery query = new SelectQuery();
query.select("orders.id", "SUM(order_items.price * order_items.quantity) as total")
        .from("orders")
        .join("order_items", "orders.id = order_items.order_id")
        .groupBy("orders.id");

String sql = query.toString();
```
La consulta generada sería:

```sql
Copy code
SELECT orders.id, SUM(order_items.price * order_items.quantity) as total
FROM orders
JOIN order_items ON orders.id = order_items.order_id
GROUP BY orders.id
```

7. Supongamos que tenemos tres tablas: `users`, `orders` y `order_items`. La tabla `users` tiene una columna `id` como 
clave primaria, la tabla `orders` tiene una columna id como clave primaria y una columna `user_id` que hace referencia 
a la columna `id` de la tabla `users`, y la tabla `order_items` tiene una columna `order_id` que hace referencia a la 
columna `id` de la tabla `orders`. Queremos obtener el nombre del usuario, el número de orden y el total de ventas por 
orden.

```java
SelectQuery query = new SelectQuery();
query.select("users.name", 
        "orders.id", 
        "SUM(order_items.price * order_items.quantity) as total")
        .from("users")
        .join("orders", "users.id = orders.user_id")
        .join("order_items", "orders.id = order_items.order_id")
        .groupBy("orders.id");
String sql = query.build();
```

La consulta generada sería:

```sql
SELECT users.name, orders.id, SUM(order_items.price * order_items.quantity) as total
FROM users
JOIN orders ON users.id = orders.user_id
JOIN order_items ON orders.id = order_items.order_id
GROUP BY users.name, orders.id
```


```java
Copy code
// construir la consulta SQL
SelectQueryUtil queryUtil = new SelectQueryUtil();
queryUtil.select("o.order_id", "o.order_date", "c.customer_name")
.from("orders o")
.join("customers c", "o.customer_id = c.customer_id")
.where("o.order_id = :orderId")
.setParameter("orderId", 10);

```
Estos son solo algunos ejemplos de cómo usar SelectQueryUtil para crear consultas `SELECT` en Java. Espero que les
sean útiles para aprender a utilizar esta herramienta.

