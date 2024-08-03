# Proyecto Lugares (Reto Técnico)

Este proyecto es una aplicación móvil para la visualización de un listado de lugares turisticos. La aplicación está construida usando Kotlin, siguiendo la arquitectura MVVM (Model-View-ViewModel) y utilizando varias tecnologías modernas para asegurar su escalabilidad y mantenibilidad.

## Tecnologías Utilizadas

- **MVVM (Model-View-ViewModel)**: Se utiliza para mantener una separación clara de responsabilidades y facilitar la prueba y el mantenimiento del código.
- **Retrofit 2**: Para manejar las solicitudes de red y la comunicación con las APIs.
- **Hilt**: Para la inyección de dependencias, facilitando la gestión y provisión de dependencias en todo el proyecto.
- **Room**: Para el almacenamiento de datos locales.
- **Kotlinx Serialization**: Para la serialización de datos, tanto para el almacenamiento local como para las comunicaciones de red.
- **Timber**: Para el registro de logs de manera eficiente.

## Estructura del Proyecto

El proyecto sigue una estructura modular que facilita la escalabilidad y el mantenimiento. A continuación se detalla la estructura de carpetas utilizada:

```plaintext
data/
└── database/
    └── room/
└── network/
    └── api/
    └── models/
└── repository/
└── model/
    └── (Modelos)

di/
└── (Modulos)

domain/
└── detail/
└── home/
└── usecase/

ui/
├── detail/
├── home/
├── map/
├── (Archivos Base)

utils/
├── Result.kt
├── ...

widget/
├── ProgressButton.kt
```

## Configuración del Proyecto

### Prerrequisitos

- Android Studio Koala o superior.
- Configurar un archivo secrets.properties con el API KEY de Google Maps.
- JDK 17
