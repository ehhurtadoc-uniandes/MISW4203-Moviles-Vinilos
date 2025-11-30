# Proyecto MISW4203-Moviles-Vinilos

## Integrantes del equipo

| Nº | Nombre y Apellido                     | Código    | Correo                        |
|----|---------------------------------------|-----------|-------------------------------|
| 1  | Edwin Hernán Hurtado Cruz             | 202326341 | eh.hurtado@uniandes.edu.co    |
| 2  | Harold Andrés Bartolo Moscoso         | 202513889 | h.bartolo@uniandes.edu.co     |
| 3  | Sergio Fernando Barrera Molano        | 202517034 | sf.barreram1@uniandes.edu.co  |
| 4  | Juan José Restrepo Bonilla            | 202516633 | jj.restrepob1@uniandes.edu.co |

---

## Acuerdos

- Reunirnos mínimo **2 veces por semana**.  
  - La reunión inicial de asignación de labores será los **lunes o martes**.  
  - La reunión de unificación será entre **jueves y sábado**, a las **8 PM (UTC -5)**.  
- Estar atentos al grupo para resolver dudas, inconvenientes o problemas que surjan.  
- Conectarse con **puntualidad** a las reuniones o avisar en caso de algún problema con al menos **1 hora de antelación**.  
- Cada integrante es responsable de la labor asignada y deberá **cargarla en la fecha y hora acordada**.  
- Al inicio de la semana se **asignarán las labores** en el tablero del proyecto. cada persona es responsable de **actualizar las tareas** y **cerrar los issues** asignados.  

---

## Metodología

- Se realizarán reuniones **mínimo 2 veces por semana**:
  - **Lunes o martes, 8:00 PM (hora Colombia)** por **Teams**, para la asignación de tareas individuales.
  - **Jueves a sábado**, para la **retroalimentación y unificación**.  
- La coordinación de reuniones será por **WhatsApp**.  
- En cada reunión se **revisarán las tareas realizadas**.

---

## Requerimientos

- Android Studio
- ADB (winget install --id=Google.PlatformTools  -e)

---

## Cómo Generar el APK

### Opción 1: Generar APK de Debug (Desarrollo)

1. **Desde Android Studio:**
   - Ve al menú superior: `Build > Build Bundle(s) / APK(s) > Build APK(s)`
   - Espera a que finalice el proceso de compilación
   - Cuando termine, aparecerá una notificación en la esquina inferior derecha
   - Haz clic en `locate` para abrir la carpeta donde se guardó el APK
   - El APK estará en: `app\build\outputs\apk\debug\app-debug.apk`

2. **Desde la terminal (dentro del proyecto):**
   ```cmd
   gradlew assembleDebug
   ```
   - El APK se generará en: `app\build\outputs\apk\debug\app-debug.apk`

### Opción 2: Generar APK de Release (Producción)

1. **Desde Android Studio:**
   - Ve al menú: `Build > Generate Signed Bundle / APK...`
   - Selecciona `APK` y haz clic en `Next`
   - Si no tienes un keystore, crea uno nuevo:
     - Haz clic en `Create new...`
     - Completa la información requerida (ruta, contraseña, alias, etc.)
     - Guarda el archivo `.jks` en un lugar seguro
   - Si ya tienes un keystore, selecciónalo y proporciona las contraseñas
   - Haz clic en `Next`
   - Selecciona `release` como Build Variant
   - Marca `V1 (Jar Signature)` y `V2 (Full APK Signature)`
   - Haz clic en `Finish`
   - El APK firmado estará en: `app\release\app-release.apk`

2. **Desde la terminal (requiere configuración previa del keystore):**
   ```cmd
   gradlew assembleRelease
   ```

### Opción 3: Generar Bundle (Para Google Play Store)

1. **Desde Android Studio:**
   - Ve al menú: `Build > Generate Signed Bundle / APK...`
   - Selecciona `Android App Bundle` y sigue los mismos pasos del APK de Release
   - El archivo `.aab` se generará en: `app\release\app-release.aab`

2. **Desde la terminal:**
   ```cmd
   gradlew bundleRelease
   ```

### Instalar el APK en un Dispositivo

**Usando ADB:**
```cmd
adb install app\build\outputs\apk\debug\app-debug.apk
```

**Para reinstalar (si ya existe):**
```cmd
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

### Notas Importantes

- Los APKs de **debug** son solo para pruebas y desarrollo
- Los APKs de **release** deben estar firmados para distribución
- Para publicar en Google Play Store, se recomienda usar **Android App Bundle (.aab)**
- **Nunca compartas** tu archivo keystore (`.jks`) ni sus contraseñas en repositorios públicos
- El APK de release actual se encuentra en: `APKs Release\Vinilos_1.0.apk`

