# Evidencias: equipo <Ortega Pertuz>, <Reinoso Bonilla>

**Integrantes:** <los tres nombres completos>

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|1|Adicion|4|implement cannot be resolved to a type|se coloca s en implemen|
|2|Bebida|23|Syntax error, insert ") Statement" to complete BlockStatements|se coloca parentesis|
|3|Bebida|31|SThe return type is incompatible with ProductoMenu.minutosPreparacion()|se cambia double por int|
|4|LineaPedido|12|Type mismatch: cannot convert from ProductoMenu to boolean|faltaba colocar otro =, para un condicional  |
|5|LineaPedido|31|TCannot reduce the visibility of the inherited method from Cobrable|colocar el public |
|6|Panaderia|31|String literal is not properly closed by a double-quote|colocar comillas faltantes|
|7|Panaderia|20|PrecioBase cannot be resolved to a variable|se debia colocar un constructor porque ese atributo es privado|
|8|Pedido|49|The local variable total may not have been initialized|se inicializó variable|
|9|Pedido|42|The final field Pedido.pagado cannot be assigned|se corrige el final porque si es constante no se puede asignar |
|10|Pedido|84|Syntax error on token ",", ; expected|se coloca ; en vez de ,|
|11|PlatoFuerte|11|The constructor ProductoMenu(String, String, double) is undefined|se completa el atributo faltante|
|12|Brunch|52|This method must return a result of type Mesa|falta el return null|
|13|Brunch|76|Unreachable code|falta el return null|el return debe ir al final|
|14|Main|23|Bebida cannot be resolved to a type|falta importar bebida|
|15|Main|44|The constructor Mesa(String, int) is undefined|quitar comillas|


(las 15 filas)

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
| 1 | ProductoMenu (Atributos) | El stock era compartido (static) para todos | Stock de la carta en 5 | Se eliminó el modificador 'static' de la variable stock. |
| 2 | Bebida.calcularPrecio() | No cobraba recargo por tamaño grande | Jugo mandarina a $9000 | Se agregó 'if (ml >= 500) precio += 2500' |
| 3 | Brunch.buscarProducto() | Sensible a mayúsculas | Buscar pan-001 fallaba | Se reemplazó equals() por equalsIgnoreCase() |
| 4 | ProductoMenu.vender() | Vendía sin revisar el límite del stock | Arepa vendida sin stock | Se añadió la validación 'cantidad <= stock' |
| 5 | LineaPedido.calcularPrecio() | Reemplazaba el costo de las adiciones en vez de sumarlo | Subtotales erróneos | Se cambió '=' por '+=' en el ciclo de adiciones |
| 6 | Pedido.esVegetariano() | Retornaba true al primer producto vegetariano | Pedido amigos era true | Se validó que ningún producto sea no vegetariano (!esVegetariano) |
| 7 | Brunch.abrirPedido() | Exigía que la mesa tuviera capacidad exacta | Mesa 3 rechazaba 2 pax | Se cambió '==' por '>=' al comparar capacidad vs personas |
| 8 | Pedido.calcularDescuento()| Daba descuento familiar a cuentas menores a 150k | Descuento con 114k | Se añadió '&& calcularSubtotal() >= 150000' |
| 9 | Pedido.calcularTiempo...()| Retornaba 0 como tiempo estimado | Tiempos en 0 min | Se aplicó Math.max() para hallar la mayor preparación |
| 10| Brunch.cerrarPedido() | No marcaba la mesa como desocupada | Mesa 3 no quedó libre | Se agregó pedido.getMesa().desocupar() al cerrar |
(las 10 filas)

## 3. Revisión de código
| # | Archivo y método | Por qué era una mala práctica | Qué cambiamos |
|---|------------------|-------------------------------|---------------|
(las 5 filas, de R1 a R5)

## 4. Salida final del programa
(pega aquí la salida completa de `java -cp out Main`, o una captura)

## 5. Conceptos de POO en este proyecto
En 2 o 3 líneas cada uno, expliquen DÓNDE se usa en este código:
- **Encapsulamiento:**
- **Herencia:**
- **Abstracción:**
- **Interfaz:** (hay dos, ¿qué clases implementa cada una?)
- **Polimorfismo:** (miren la sección 7 de Main y lo que hicieron en R1)
```

---