# Ejercicios

1. Crea una consulta `SELECT` que seleccione todos los registros de una tabla llamada `clientes`.
```sql
SELECT * 
FROM clientes
```

2. Crea una consulta `SELECT` que seleccione el `id` y `nombre` de registros de la tabla `clientes` donde el valor de la 
columna `edad` sea mayor que `25`.
```sql
SELECT id, nombre
FROM clientes
WHERE edad > 25
AND estatus = true

```

3. Crea una consulta `SELECT` que seleccione los registros de la tabla `pedidos` donde el valor de la columna `fecha` 
sea igual a '22-03-2023'.
```sql
SELECT *
FROM pedidos
WHERE fecha = '22-03-2023'
```

4. Crea una consulta `SELECT` que seleccione solamente las columnas `nombre` y `apellido` de la tabla `clientes`.
```sql
SELECT nombre, apellido
FROM clienes
```

5. Crea una consulta `SELECT` que seleccione todos los registros de la tabla `productos` ordenados por el valor de la 
columna `precio` en orden `ascendente`.
```sql
SELECT * 
FROM productos
ORDER BY precio ASC
```

6. Crea una consulta `SELECT` que seleccione todos los registros de la tabla `pedidos` que tengan un valor de la 
columna `cantidad` mayor que `10` y un valor de la columna `fecha` posterior a `'2022-01-01'`.

```sql
SELECT * FROM pedidos
WHERE cantidad > 10
AND fecha > '01-01-2023'
```

6. Supongamos que tenemos dos tablas: orders y order_items. La tabla orders tiene una columna id como clave primaria y 
la tabla order_items tiene una columna order_id que hace referencia a la columna id de la tabla orders. 
Queremos obtener el total de ventas por cada orden. 

La consulta generada sería:

```sql
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

La consulta generada sería:

```sql
SELECT users.name, orders.id, SUM(order_items.price * order_items.quantity) as total
FROM users
JOIN orders ON users.id = orders.user_id
JOIN order_items ON orders.id = order_items.order_id
GROUP BY users.name, orders.id
```
