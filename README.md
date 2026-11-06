# Parcial práctico POO: El Brunch POO de Envigado 🥞☕

| | |
|---|---|
| **Duración** | 1 hora 30 minutos (90 minutos) |
| **Lenguaje** | Java 17 o superior |
| **Puntaje** | 100 puntos |
| **Modalidad** | Equipos de 3 personas (la nota es la misma para los tres) |

---

## 📖 Historia

**El Brunch POO** abre los domingos en Envigado, Antioquia, a unas cuadras del parque principal. Doña Gloria sirve calentado paisa, huevos pericos con arepa, arepa de chócolo con quesito, chocolate en leche, tinto de Jardín, jugo de mandarina, pandequeso y buñuelos. Con el sistema atiende las mesas, cuida el stock de cada producto, liquida las cuentas con impoconsumo y propina, y saca las ventas del día.

El sistema lo hizo un primo que tomó un curso de un fin de semana y lo dejó **roto**. No compila. Cuando compila, **las cuentas no cuadran**: el stock de todos los productos es el mismo, la arepa se sigue vendiendo aunque se acabó el chócolo y las mesas nunca quedan libres. Y aunque funcione, por dentro está tan desordenado que nadie quiere tocarlo.

Doña Gloria los contrató como equipo para dejarlo **funcionando y presentable** antes del domingo.

---

## ⚠️ Lee esto antes de empezar

Este parcial tiene **tres fases** y se hacen **en orden**:

```
     FASE 1                 FASE 2                 FASE 3              ENTREGA
Errores de sintaxis → Errores de lógica → Revisión de código  →  EVIDENCIAS.md y PR
  (que compile)       (que cuadre)        (que quede limpio)
  15 errores · 20 min 10 errores · 35 min 5 hallazgos · 20 min      15 min
```

No se puede probar la lógica de un programa que no compila, y no se debe refactorizar un programa que todavía da resultados equivocados.

**Administren el tiempo**: si un error los bloquea más de 5 minutos, anótenlo y sigan con otro.

> 👥 **Sugerencia para el equipo:** uno escribe, otro sigue las reglas del negocio y hace las cuentas a mano, y otro compara la salida con la tabla y lleva `EVIDENCIAS.md`. Roten en cada fase.
>
> 💡 **Hagan commits mientras avanzan**, como mínimo: `Fase 1: el proyecto compila`, `Fase 2: corrige errores de lógica`, `Fase 3: revisión de código` y `Evidencias`.

---

## 🗂️ Estructura del proyecto

```text
src/
  Main.java                  ← programa principal con los datos de prueba
  modelo/
    Identificable.java       ← interfaz: tiene código y ficha
    Cobrable.java            ← interfaz: tiene un valor en pesos
    ProductoMenu.java        ← clase abstracta (padre de los 3 productos)
    PlatoFuerte.java         ← hereda de ProductoMenu
    Bebida.java              ← hereda de ProductoMenu
    Panaderia.java           ← hereda de ProductoMenu
    Adicion.java             ← aguacate, quesito (es Cobrable, NO es producto)
    Mesa.java                ← número, capacidad y si está ocupada
    LineaPedido.java         ← un renglón de la cuenta
    Pedido.java              ← la cuenta de una mesa
  servicio/
    Brunch.java              ← administra la carta, las mesas y los pedidos
```

### Diagrama de clases

```text
   «interface»                     «interface»
  Identificable                     Cobrable
 + getCodigo(): String          + calcularPrecio(): double
 + obtenerFicha(): String              ▲
        ▲                              │ implements
        │        ┌──────────────┬──────┴───────┬─────────────┐
        │        │              │              │             │
        ├── «abstract» ProductoMenu         Adicion     LineaPedido
        │   - codigo, nombre                - nombre    - producto
        │   - precioBase: double            - precio    - cantidad
        │   - stock: int                                - adiciones
        │   - disponible: boolean
        │   + puedeVender(int): boolean
        │   + vender(int): boolean
        │   + minutosPreparacion(): int   (abstracto)
        │   + getCategoria(): String      (abstracto)
        │   + esVegetariano(): boolean    (abstracto)
        │          ▲
        │   ┌──────┼──────────┐
        │ PlatoFuerte Bebida  Panaderia
        │
        └── Pedido ───────► Mesa
            - codigo, personas, aceptaPropina, pagado
            - lineas: ArrayList<LineaPedido>
            + calcularSubtotal() + calcularDescuento()
            + calcularImpoconsumo() + calcularPropina()
            + calcularTiempoEstimado() + esVegetariano()

 Brunch (servicio)
  - menu, mesas, pedidos
  + buscarProducto(String)     + abrirPedido(int, int, boolean): Pedido
  + buscarMesa(int)            + agregarAlPedido(Pedido, String, int): LineaPedido
  + calcularVentasDelDia()     + cerrarPedido(Pedido): boolean
```

### 📐 Reglas del negocio (las fórmulas)

| Concepto | Regla |
|---|---|
| Plato fuerte | Precio base. Con proteína extra suma **$6.000**. Tarda **20 min**; con proteína extra, **25 min** |
| Bebida | Precio base. Si tiene **500 ml o más** suma **$2.500**. Caliente **4 min**, fría **6 min** |
| Panadería | `precioBase × unidades` de la porción. Recién horneado **12 min**, de vitrina **3 min** |
| Vender un producto | Solo si está **disponible**, la cantidad es mayor que 0 y **alcanza el stock**. Cada producto tiene **su propio** stock |
| Buscar un producto | Por código, **sin importar** mayúsculas o minúsculas |
| Precio de una línea | `(precio del producto + adiciones) × cantidad` (cada unidad lleva sus adiciones) |
| Subtotal | La suma de todas las líneas |
| Plan familiar | **10 %** del subtotal si son **4 o más personas y** el subtotal llega a **$150.000** |
| Impoconsumo | **8 %** sobre `subtotal - descuento` |
| Propina voluntaria | **10 %** sobre `subtotal - descuento`, **sin** incluir el impoconsumo. Si no la aceptan, $0 |
| Total | `subtotal - descuento + impoconsumo + propina` |
| Tiempo del pedido | El del producto **más demorado** |
| Pedido vegetariano | Solo si **todos** sus productos lo son |
| Abrir pedido | Solo si la mesa existe, está libre y el grupo cabe. Ocupa la mesa |
| Cerrar pedido | Lo marca como pagado y **libera la mesa**. No se puede cerrar dos veces |
| Ventas del día | Suma el total de los pedidos **ya pagados** |

---

## ▶️ Compilar y ejecutar

Desde la **raíz del proyecto** (la carpeta que contiene `src/`):

```bash
javac -encoding UTF-8 -d out src/modelo/*.java src/servicio/*.java src/Main.java
java -cp out Main
```

> Si usan un IDE también pueden ejecutar `Main` directamente, pero **comprueben al menos una vez con los comandos de arriba**.

---

## 🔧 FASE 1: Errores de sintaxis (30 pts) · ⏱ 20 min

El proyecto tiene **exactamente 15 errores de compilación**. Corríjanlos hasta que `javac` termine **sin ningún mensaje**.

1. Compilen y lean el **primer** error: archivo, número de línea y mensaje.
2. Vayan a esa línea, entiendan qué dice el mensaje y corrijan.
3. Vuelvan a compilar. Repitan.

> 🧠 **Los errores aparecen por oleadas.** Java primero revisa que el código esté bien *escrito*, luego los tipos y la herencia, y al final que las variables tengan valor y que todo el código se pueda alcanzar. Por eso, al corregir unos errores pueden aparecer otros nuevos. **Vas avanzando.** Un mismo error puede producir varios mensajes.

| Mensaje del compilador | Qué suele significar |
|---|---|
| `'{' expected` | Algo mal escrito en la declaración de la clase |
| `')' expected` / `';' expected` | Falta cerrar un paréntesis, o el `for` no separa bien sus tres partes |
| `unclosed string literal` | A un texto le falta cerrar la comilla |
| `cannot find symbol` | Un nombre mal escrito o falta un `import` |
| `constructor X in class X cannot be applied to given types` | Llaman a un constructor o a `super` con argumentos de más o de menos |
| `X has private access in Y` | La clase hija no ve los atributos `private` del padre: usen su *getter* |
| `cannot assign a value to final variable` | Un atributo `final` no cambia después de creado. Decidan si de verdad debe ser `final` |
| `return type X is not compatible with Y` | Al sobrescribir un método hay que respetar su tipo de retorno |
| `attempting to assign weaker access privileges` | Un método que implementa una interfaz debe ser `public` |
| `incompatible types: X cannot be converted to boolean` | Dentro de un `if` escribieron `=` (asignar) en vez de `==` (comparar) |
| `incompatible types: String cannot be converted to int` | `"1"` es un texto, `1` es un número |
| `variable X might not have been initialized` | La variable se usa sin haber recibido valor |
| `missing return statement` | Hay un camino en el que el método termina sin retornar nada |
| `unreachable statement` | Hay código después de un `return`: nunca se ejecuta |

**Reglas de la fase 1:**

- ❌ No borren clases, métodos, atributos ni interfaces para "hacer que compile".
- ❌ No cambien `abstract`, `extends` ni `implements`, no usen `default` en las interfaces y no cambien `private` por `public` en los atributos.
- ✅ Corrijan **respetando la intención** del código: los comentarios `/** ... */` y las reglas del negocio dicen qué debe hacer cada método.

✔️ **Terminaron la fase 1 cuando:** `javac` no muestra errores y `java -cp out Main` ejecuta las 7 secciones.

---

## 🐞 FASE 2: Errores de lógica (40 pts) · ⏱ 35 min

Ahora el programa compila... pero **miente**. Hay **exactamente 10 errores de lógica** en `ProductoMenu`, `Bebida`, `LineaPedido`, `Pedido` y `Brunch`. **`Main.java` no tiene errores de lógica.**

Ejecuten el programa y comparen **cada sección** con esta tabla. Cada fila que no coincida es una pista.

| Sección | Qué se prueba | Resultado esperado |
|---|---|---|
| 1 | Precios de la carta (en orden) | `$26000`, `$32000`, `$18000`, `$16000`, `$5000`, `$7000`, `$11500`, `$10000`, `$7000`, `$9000` |
| 1 | Minutos de la carta (en orden) | `20`, `25`, `20`, `20`, `4`, `4`, `6`, `6`, `12`, `3` |
| 1 | Stock de la carta (en orden) | `8`, `6`, `10`, `5`, `30`, `20`, `15`, `15`, `12`, `5` |
| 2 | `Buscar pan-001` / `Buscar XXX-999` | `Pandequeso` / `no encontrado` |
| 2 | ¿Se puede vender 1 arepa? Antes / después de acabarse el chócolo | `true` / `false` |
| 3 | ¿La mesa 3 recibe a 6 personas? / ¿A 7? | `true` / `false` |
| 4 | ¿Se vendió la arepa no disponible? / ¿6 buñuelos? / ¿2 buñuelos? | `false` / `false` / `true` |
| 4 | ¿Pedido en la mesa 9? / ¿Otro pedido en la mesa 3? | `false` / `false` |
| 4 | Comanda de la familia (estaciones en orden) | `Cocina`, `Cocina`, `Barra`, `Barra`, `Horno` |
| 4 | `Quedan de chocolate` / `Quedan de buñuelo` | `16` / `3` |
| 4 | ¿Vegetariano? amigos / pareja | `false` / `true` |
| 5 | Familia | `25 min`; subtotal `$184500`, descuento `$18450`, impoconsumo `$13284`, propina `$16605`, total `$195939` |
| 5 | Amigos | `20 min`; subtotal `$108000`, descuento `$0`, impoconsumo `$8640`, propina `$0`, total `$116640` |
| 5 | Pareja | `20 min`; subtotal `$74000`, descuento `$0`, impoconsumo `$5920`, propina `$7400`, total `$87320` |
| 6 | Cerrar la familia / cerrarla otra vez / cerrar la pareja | `true` / `false` / `true` |
| 6 | ¿Mesa 3 libre? / ¿Abrió el siguiente grupo? | `true` / `true` |
| 6 | `Pedidos abiertos en el día` / `Ventas del día` | `4` / `$283259` |
| 7 | Polimorfismo | `PED-001`; `Panaderia $7000`, `Adicion $3500`, `LineaPedido $79000`, `Pedido $87320` |

> 📝 **Algunos errores esconden a otros.** Cuando arreglen uno, vuelvan a ejecutar y revisen **toda** la tabla.
>
> 🧮 Hagan las cuentas a mano con las reglas del negocio para entender de dónde sale cada número.
>
> 💡 Si en la consola las tildes se ven raras (`Panader�a`), no es un error del programa: es la configuración de la consola.

✔️ **Terminaron la fase 2 cuando:** toda la salida coincide con la tabla. **Guarden esa salida**: la van a necesitar en la fase 3.

---

## 🔍 FASE 3: Revisión de código (15 pts) · ⏱ 20 min

El programa ya da los resultados correctos, pero un buen revisor no aprueba un código solo porque funciona. Hay **5 malas prácticas** en estas clases. Encuéntrenlas y corríjanlas **sin cambiar el comportamiento**.

| # | Qué buscar | Dónde | Pregunta que los orienta |
|---|---|---|---|
| R1 | Polimorfismo desaprovechado | `LineaPedido` | Si mañana agregan una clase `Postre`, ¿cuántos archivos que ya existen hay que tocar? ¿A quién le corresponde saber su estación? |
| R2 | Números mágicos | `Pedido` | ¿Qué significa cada número? Miren cómo lo resuelven `Bebida` y `PlatoFuerte` |
| R3 | Código duplicado | `Pedido` | ¿Qué cálculo se repite y qué pasa si la regla cambia? |
| R4 | Lista interna expuesta | `Brunch` | Comparen con `Pedido.getLineas()`. ¿Qué podría hacerle alguien a la carta desde afuera? |
| R5 | Código difícil de leer | `Brunch` | ¿Qué hace el método privado? ¿Su nombre lo dice? ¿Sobran líneas? |

**Reglas de la fase 3:**

- ✅ Pueden agregar constantes, métodos privados y, en R1, **un método abstracto nuevo** en `ProductoMenu`.
- ✅ Pueden cambiar la implementación interna y los nombres de lo que sea **privado**.
- ❌ No cambien las firmas públicas que ya existen. `obtenerEstacion()` sigue existiendo en `LineaPedido`.
- ❌ No toquen `Main.java`.

✔️ **Terminaron la fase 3 cuando:** los 5 hallazgos están corregidos y la salida es **idéntica** a la de la fase 2. Si cambió un solo número, el refactor dañó algo.

---

## 📄 Entregable: `EVIDENCIAS.md` (10 pts) · ⏱ 15 min

Creen un archivo `EVIDENCIAS.md` en la raíz del proyecto:

```markdown
# Evidencias: equipo <apellido 1>, <apellido 2>, <apellido 3>

**Integrantes:** <los tres nombres completos>

## 1. Errores de sintaxis corregidos
| # | Archivo | Línea | Mensaje del compilador | Corrección |
|---|---------|-------|------------------------|------------|
(las 15 filas)

## 2. Errores de lógica corregidos
| # | Archivo y método | Qué hacía mal | Pista (fila de la tabla) | Cómo lo corregimos |
|---|------------------|---------------|--------------------------|--------------------|
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

## 📊 Rúbrica

| Fase | Criterio | Pts |
|---|---|---|
| 1 | El proyecto compila; los 15 errores están corregidos **respetando la intención** del código (2 pts c/u) | 30 |
| 2 | Los 10 errores lógicos están corregidos y la salida coincide con la tabla (4 pts c/u) | 40 |
| 3 | Los 5 hallazgos están corregidos sin cambiar la salida (3 pts c/u) | 15 |
| — | `EVIDENCIAS.md` completo | 10 |
| — | Commits por fase | 5 |
| | **Total** | **100** |

**Penalizaciones:**

- Eliminar clases, métodos, interfaces o relaciones de herencia: **−10 por cada caso**.
- Reemplazar un cálculo por el número esperado (ej. `return 195939;`): **ese error vale 0** y **−5** adicionales.

---

## 📏 Reglas generales

- ✅ Pueden agregar pruebas propias, pero bórrenlas antes de entregar: `Main` debe quedar como venía, con solo los errores de sintaxis corregidos.
- ✅ Pueden consultar sus apuntes y la documentación oficial de Java.
- ❌ No cambien las **firmas públicas** de los métodos, salvo cuando eso sea justamente el error de sintaxis.
- ❌ No usen bibliotecas externas, asistentes de IA, herramientas de reparación automática ni código de otro equipo.
- ❌ No modifiquen los datos de prueba que ya vienen en `Main`.

---

## 🚀 Entrega

1. **Un integrante** hace *fork* del repositorio y agrega a los otros dos como colaboradores.
2. Trabajan en una rama nueva, con **commits por fase** (mínimo 4).
3. Verifiquen que el repositorio tenga `src/` corregido y `EVIDENCIAS.md`.
4. **Ese mismo integrante** abre **un solo Pull Request** hacia la rama `develop` del repositorio del docente, antes de que termine el tiempo. Título: `Equipo <apellido 1>, <apellido 2>, <apellido 3>`.

**¡Éxitos, y que no se les enfríe el chocolate! ☕**
