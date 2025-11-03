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

# Guía de Instalación Local - Vinilos Mobile

Esta guía detalla todos los pasos necesarios para configurar el entorno de desarrollo y ejecutar el proyecto **Vynils Mobile** en tu máquina local.

## Tabla de Contenidos

- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación y Configuración](#instalación-y-configuración)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
- [Pruebas](#pruebas)
- [Solución de Problemas Comunes](#solución-de-problemas-comunes)
- [Recursos Adicionales](#recursos-adicionales)

---

## Requisitos del Sistema

### Hardware Recomendado

- **RAM**: Mínimo 8 GB (recomendado 16 GB o más)
- **Espacio en Disco**: Al menos 8 GB de espacio libre (más espacio para emuladores Android)
- **Procesador**: Intel i5/i7 o AMD Ryzen 5/7 de 8ª generación o superior
- **Resolución de Pantalla**: 1280 x 800 mínimo

### Software Requerido

#### 1. Sistema Operativo

- **Windows**: Windows 10 (64-bit) o superior
- **macOS**: macOS 10.14 (Mojave) o superior
- **Linux**: Ubuntu 20.04 LTS o superior, Debian 10 o superior

#### 2. Java Development Kit (JDK)

- **JDK 11** o superior
- Se puede descargar desde:
  - [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
  - [OpenJDK](https://adoptium.net/)

**Verificar instalación de Java:**
```bash
java -version
```

#### 3. Android Studio

- **Versión requerida**: Android Studio Iguana (2023.2.1) o superior
- **Descarga**: [https://developer.android.com/studio](https://developer.android.com/studio)

#### 4. Android SDK Components

El proyecto requiere los siguientes componentes del Android SDK:

- **Android SDK Platform**: API Level 36 (Android 16.0+)
- **Android SDK Build-Tools**: Versión 34.0.0 o superior
- **Android SDK Platform-Tools**: Última versión disponible
- **Android Emulator**: (Opcional, para ejecutar sin dispositivo físico)

#### 5. Git

- **Git**: Para clonar el repositorio
- **Descarga**: [https://git-scm.com/downloads](https://git-scm.com/downloads)

---

## Instalación y Configuración

### Paso 1: Instalar Java Development Kit (JDK)

#### Windows

1. Descarga el instalador de JDK 11 o superior desde [Adoptium](https://adoptium.net/)
2. Ejecuta el instalador y sigue las instrucciones
3. Configura las variables de entorno:
   - Abre "Panel de Control" → "Sistema" → "Configuración avanzada del sistema"
   - Clic en "Variables de entorno"
   - En "Variables del sistema", crea una nueva variable llamada `JAVA_HOME` con la ruta de instalación del JDK (ej: `C:\Program Files\Eclipse Adoptium\jdk-11.0.xx.xx-hotspot\`)
   - Edita la variable `Path` y agrega `%JAVA_HOME%\bin`
4. Abre una nueva terminal y verifica:
   ```bash
   java -version
   javac -version
   ```

#### macOS

1. Descarga el instalador de JDK desde [Adoptium](https://adoptium.net/)
2. Ejecuta el archivo `.pkg` descargado
3. Verifica la instalación:
   ```bash
   java -version
   ```

#### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-11-jdk
java -version
```

### Paso 2: Instalar Android Studio

#### Windows

1. Descarga Android Studio desde [https://developer.android.com/studio](https://developer.android.com/studio)
2. Ejecuta el instalador `.exe` descargado
3. Sigue el asistente de instalación:
   - Selecciona **"Standard"** installation type
   - Acepta las configuraciones predeterminadas
   - Espera a que se descarguen los componentes del SDK
4. Una vez completada la instalación, Android Studio se abrirá automáticamente

#### macOS

1. Descarga el archivo `.dmg` de Android Studio
2. Abre el archivo descargado
3. Arrastra Android Studio a la carpeta "Applications"
4. Abre Android Studio desde la carpeta "Applications"
5. Sigue el asistente de configuración inicial

#### Linux (Ubuntu/Debian)

```bash
# Descargar Android Studio
wget https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2023.2.1.24/android-studio-2023.2.1.24-linux.tar.gz

# Extraer el archivo
sudo tar -xvzf android-studio-*-linux.tar.gz -C /opt/

# Ejecutar Android Studio
cd /opt/android-studio/bin
./studio.sh
```

### Paso 3: Configurar Android Studio

#### 3.1 Configuración Inicial

1. Al abrir Android Studio por primera vez, se iniciará el **Setup Wizard**
2. Selecciona **"Standard"** installation
3. Elige el tema de tu preferencia (Light o Darcula)
4. En "SDK Components Setup", asegúrate de que estén seleccionados:
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device
5. Acepta las licencias y espera a que se descarguen los componentes

#### 3.2 Instalar SDK Components Requeridos

1. Abre Android Studio
2. Ve a **File** → **Settings** (en Windows/Linux) o **Android Studio** → **Preferences** (en macOS)
3. Navega a **Appearance & Behavior** → **System Settings** → **Android SDK**
4. En la pestaña **SDK Platforms**:
   - Marca la casilla **"Show Package Details"** (esquina inferior derecha)
   - Expande **"Android 16.0+ (API 36)"** o la versión más reciente disponible
   - Asegúrate de que estén seleccionados:
     - Android SDK Platform 36
     - Sources for Android 36
5. En la pestaña **SDK Tools**:
   - Marca **"Show Package Details"**
   - Verifica que estén instalados:
     - Android SDK Build-Tools 34.0.0 o superior
     - Android SDK Platform-Tools
     - Android Emulator
     - Android SDK Command-line Tools (latest)
     - Google Play Services
6. Clic en **"Apply"** y espera a que se descarguen e instalen los componentes

#### 3.3 Configurar Variables de Entorno (Opcional pero recomendado)

##### Windows

1. Abre "Variables de entorno" como se describió anteriormente
2. Crea una nueva variable llamada `ANDROID_HOME`:
   - Valor: `C:\Users\TU_USUARIO\AppData\Local\Android\Sdk`
3. Edita la variable `Path` y agrega:
   - `%ANDROID_HOME%\platform-tools`
   - `%ANDROID_HOME%\tools`
   - `%ANDROID_HOME%\emulator`

##### macOS/Linux

Agrega al archivo `~/.bashrc`, `~/.zshrc` o `~/.bash_profile`:

```bash
export ANDROID_HOME=$HOME/Android/Sdk  # o la ruta donde instalaste el SDK
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/emulator
```

Luego ejecuta:
```bash
source ~/.bashrc  # o ~/.zshrc según tu shell
```

### Paso 4: Instalar ADB (Android Debug Bridge)

ADB generalmente se instala automáticamente con Android Studio, pero si necesitas instalarlo por separado:

#### Windows (usando winget)

```powershell
winget install --id=Google.PlatformTools -e
```

#### Verificar instalación de ADB

```bash
adb --version
```

### Paso 5: Clonar el Repositorio

1. Abre una terminal o línea de comandos
2. Navega al directorio donde deseas clonar el proyecto:
   ```bash
   cd C:\Users\TU_USUARIO\Projects  # Windows
   cd ~/Projects  # macOS/Linux
   ```
3. Clona el repositorio:
   ```bash
   git clone git@github.com:ehhurtadoc-uniandes/MISW4203-Moviles-Vinilos.git
   cd Vynils-Mobile
   ```

### Paso 6: Abrir el Proyecto en Android Studio

1. Abre Android Studio
2. Selecciona **"Open"** en la ventana de bienvenida
3. Navega hasta la carpeta del proyecto clonado (`Vynils-Mobile`)
4. Selecciona la carpeta y haz clic en **"OK"**
5. Android Studio comenzará a sincronizar el proyecto con Gradle automáticamente
   - Este proceso puede tardar varios minutos la primera vez
   - Verás un mensaje "Gradle Sync" en la parte inferior
6. Espera a que finalice la sincronización de Gradle

**Nota**: Si aparece un mensaje sobre la versión de Gradle o del plugin de Android, acepta actualizar a la versión recomendada.

### Paso 7: Configurar un Dispositivo para Ejecutar la App

Tienes dos opciones:

#### Opción A: Usar un Dispositivo Android Físico

1. **Habilitar opciones de desarrollador en tu dispositivo**:
   - Ve a **Configuración** → **Acerca del teléfono**
   - Toca **"Número de compilación"** 7 veces
   - Aparecerá un mensaje indicando que eres desarrollador
2. **Habilitar depuración USB**:
   - Ve a **Configuración** → **Opciones de desarrollador**
   - Activa **"Depuración USB"**
3. **Conectar el dispositivo**:
   - Conecta tu dispositivo Android a tu computadora mediante un cable USB
   - En tu dispositivo, acepta el mensaje "¿Permitir depuración USB?"
   - Marca "Permitir siempre desde esta computadora" y toca **"Permitir"**
4. **Verificar conexión**:
   ```bash
   adb devices
   ```
   - Deberías ver tu dispositivo listado

#### Opción B: Usar el Emulador de Android

1. En Android Studio, ve a **Tools** → **Device Manager**
2. Haz clic en **"Create Device"**
3. Selecciona un dispositivo de hardware (recomendado: **Pixel 5** o **Pixel 6**)
4. Haz clic en **"Next"**
5. Selecciona una imagen del sistema:
   - Recomendado: **API Level 34** (Android 14.0) o superior
   - Si la imagen no está descargada, haz clic en el botón **"Download"** junto a ella
   - Espera a que se descargue la imagen del sistema
6. Haz clic en **"Next"**
7. Verifica las configuraciones y haz clic en **"Finish"**
8. El emulador aparecerá en el Device Manager
9. Haz clic en el botón **▶** (Play) para iniciar el emulador

**Nota**: El emulador requiere bastante RAM. Asegúrate de tener al menos 8 GB de RAM en tu computadora.

---

## Cómo Ejecutar el Proyecto

### Ejecutar la Aplicación

1. Asegúrate de que el proyecto esté clonado y abierto en Android Studio
2. Espera a que Gradle termine de sincronizar (si no lo ha hecho ya)
3. Conecta un dispositivo físico o inicia un emulador como se describió anteriormente
4. En la parte superior de Android Studio, verás un menú desplegable con el dispositivo seleccionado
5. Asegúrate de que el dispositivo correcto esté seleccionado
6. Haz clic en el botón **▶ Run** (verde) o presiona `Shift + F10` (Windows/Linux) o `Control + R` (macOS)
7. La aplicación se compilará y se instalará en el dispositivo/emulador
8. La app se abrirá automáticamente una vez instalada

### Compilar Variantes del Proyecto

El proyecto tiene múltiples variantes de compilación:

- **debug**: Versión de desarrollo con logs habilitados
- **release**: Versión optimizada para producción

Para cambiar la variante de compilación:

1. Ve a **Build** → **Select Build Variant**
2. Selecciona la variante deseada de la lista
