
# Patrones
## De creacion
## Estructurales
## De Comportamiento

### Strategy
Cambio de algoritmo en tiempo de ejecucion. El 'contexto' delega la responsabilidad de ejecutar una operacion a una 'strategy'. Cada estrategia en concreto implementa su propia forma de ejecutar la tarea.

![Diagrama de clases del patron strategy](./strategyCD.png)

### Observer
Los observadores ejecutan su metodo actualizar cada vez que el sujeto ejecute su metodo notificarObservadores() en el momento que este cambie de estado.

Los observadores tiene visibilidad de atributo sobre el sujeto de forma que el propio observador se pueda registrar y desregistrar como observador utilizando los metodos del sujeto.

![Diagrama de clases del patron observer](./observerCD.png)

## Decorator

Agrega comportamiento extra a un objeto de una clase.
El decorador en concreto tiene visibilidad de atributo sobre el objeto 'decorado'.

Parecido a la composicion de funciones en matematicas. 
Ejemplo: $$(f o g)(x) = f(g(x))$$

![Diagrama de clases del patron decorador](./decoratorCD.png)



## Adapter

Permite trabajar con dos interfaces incompatibles. Por ejemplo en una integracion con un sistema legado, con un sistema moderno. Se quiere que los metodos de una clase del sistema moderno se conviertan en peticiones a un objeto del sistema legado. 

Valdria la pena crear una clase adaptador que implemente el supertipo de la clase moderna (Target) y que tenga visibilidad de atributo sobre la clase legado (Adaptee). En la implementacion del supertipo de la clase moderna se ejecutaran los metodos de la clase legada 'adaptada'.

![Diagrama de clases del patron Adapter](./adapterCD.png)

## Factory

## Singleton

Un mecanismo para tener variables globales en lenguajes orientados a objetos; especificamente en Java.

A traves d un metodo estatico getInstance() se crea o se retorna una instancia de la clase

![Diagrama de clases del Singleton](./singletonCD.png)


## Composite
Modela una estructura jerarquica como un arbol. Trata transparentemente una hoja de un nodo. Por ejemplo, en un sistema de archivos, trata transparentemente, mas no igual, a los archivos y los directorios.

En la interfaz Component, tendra todos los metodos que usen las hojas y los nodos. En la implementacion de las hojas probablemente haya metodos que no tenga sentido ejecutarlos en las hojas, se sugiere levantar una Exception o un Error. Ejemplo, el metodo add(Component c) no tiene sentido ejecutarlo en una hoja; solo hace sentido en un directorio. 

Aun con esas diferencias ambos casos se tratan transparentemente gracias al patron Composite. 

![Diagrama de clases del Composite](./compositeCD.png)