# FastTerminal3D 0.1.0 [ALPHA-2026-06] — High-Performance 3D Terminal Renderer for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastTerminal3D/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe/FastTerminal3D)

**⚡ A cutting-edge 3D rasterization bridge for rendering complex 3D environments natively inside your True Color terminal.**

FastTerminal3D seamlessly bridges the 3D rendering pipeline of **[FastSoftware3D](https://github.com/andrestubbe/FastSoftware3D)** with the high-performance native ANSI rendering of **[FastTerminal](https://github.com/andrestubbe/FastTerminal)**. 

To achieve a completely responsive, zero-latency desktop-class terminal experience, FastTerminal3D is designed to pair natively with the unified **FastJava** ecosystem:

* ⚡ **[FastSoftware3D](https://github.com/andrestubbe/FastSoftware3D)** — Provides the foundational math, clipping, shading, and multi-threaded triangle rasterization engine.
* 🚀 **[FastTerminal](https://github.com/andrestubbe/FastTerminal)** — Implements the native zero-copy ANSI viewport output and terminal scaling.

Watch Demo (YouTube) | Watch JMH Benchmark (YouTube)

[![FastTerminal3D Showcase](docs/screenshot.png)](docs/screenshot.png)

---

```java
// Quick Start — Example

import fastterminal3d.Demo3DTerminal;
import fastterminal3d.DemoWolfTerminal;

public class Demo {
    public static void main(String[] args) throws Exception {
        // Render an interactive first-person Wolfenstein-like dungeon inside the terminal
        DemoWolfTerminal.main(args);
    }
}
```

---

## Table of Contents

- [Why FastTerminal3D?](#why-fastterminal3d)
- [Key Features](#key-features)
- [Installation](#installation)
- [Documentation](#documentation)
- [License](#license)

---

## Why FastTerminal3D?

Standard terminal applications operate on a strictly 2D character-grid layout. Pushing real-time 3D environments into the terminal usually involves extreme compromises in rendering speed, texture quality, or depth resolution.

**FastTerminal3D** solves this by leveraging full native C++ offloading for the rasterization and downsampling processes. Instead of manually constructing large Java string buffers of ANSI escape codes, it pushes the heavily parallelized `FastSoftware3D` output pixel buffer directly into a highly tuned C++ JNI bridge that applies multi-core Super-Sample Anti-Aliasing (SSAA). It then calculates optimal spatial color blending and directly outputs the raw ANSI binary stream, enabling rich, textured 3D worlds directly inside standard terminals at 60+ FPS.

---

## Key Features

* **⚡ Native SSAA Downsampling** — High-performance spatial downsampling supporting 1x, 2x, 4x, 8x, and 16x resolutions to cleanly scale native pixel buffers to terminal half-blocks.
* **🚀 Zero-Copy Output** — Avoids heavy string allocations by computing the ANSI stream directly in C++ via JNI.
* **🎮 Seamless 3D Integration** — Provides drop-in compatibility with the AVX2 vectorization engine of `FastSoftware3D`.
* **💻 True Color Emulation** — Retains fully textured, mipmapped 24-bit color depth within the strict limitations of a grid-based terminal.

---

## Installation

### Option 1: Maven (Recommended)

Add the JitPack repository and the dependencies to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastTerminal3D</artifactId>
        <version>0.1.0</version>
    </dependency>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastSoftware3D</artifactId>
        <version>main-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastTerminal</artifactId>
        <version>0.1.3</version>
    </dependency>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastCore</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle

Add JitPack to your repositories and include the dependencies:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastTerminal3D:0.1.0'
    implementation 'com.github.andrestubbe:FastSoftware3D:main-SNAPSHOT'
    implementation 'com.github.andrestubbe:FastTerminal:0.1.3'
    implementation 'com.github.andrestubbe:FastCore:0.1.0'
}
```

---

## Documentation

* **[COMPILE.md](docs/COMPILE.md)**: Full compilation guide (Maven Build Setup).
* **[REFERENCE.md](docs/REFERENCE.md)**: Exhaustive catalog of API methods and engine architecture.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Zero-allocation and low-overhead processing designs.
* **[ROADMAP.md](docs/ROADMAP.md)**: Planned milestone features and performance extensions.
* **[CHANGELOG.md](docs/CHANGELOG.md)**: Version history and engine updates.

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
