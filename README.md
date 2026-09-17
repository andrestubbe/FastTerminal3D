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

Standard terminal applications operate on a strictly 2D character-grid layout. Pushing real-time 3D environments into the terminal usually involves extreme compromises in rendering speed, texture quality, or depth resolution:

* **Massive String Allocation Bottlenecks:** Naive terminal 3D engines format ANSI RGB strings on the JVM heap, triggering tens of thousands of object allocations per frame and stalling the GC.
* **Low-Fidelity ASCII Hacks:** Most terminal renderers rely on 1-bit ASCII density ramps (`@%#*+=-:. `) with no depth-buffering or color preservation.
* **Heavy Terminal Latency:** Flusing millions of uncompressed escape sequences over standard stdout chokes terminal emulators and causes screen tearing.
* **CPU-Bound Single-Threaded Rasterization:** Pure Java software rasterizers cannot easily exploit AVX2 SIMD or hardware multi-core tiling, dropping frame rates to sub-15 FPS.

FastTerminal3D bridges the AVX2/multi-threaded rasterization pipeline of `FastSoftware3D` with the zero-copy C++ blitter of `FastTerminal`. It applies native multi-core Super-Sample Anti-Aliasing (SSAA) and direct half-block (`▀` / `▄`) color quantization to output silky smooth 60+ FPS 3D scenes directly into any True Color terminal.

| Feature | Pure Java ASCII 3D | Lanterna / JLine Text | Term3D / Curses Wrappers | FastTerminal3D |
| :--- | :--- | :--- | :--- | :--- |
| **Color Fidelity** | 1-bit or 16-color ANSI | 16/256 Colors | 256 Colors | 24-bit True Color (SSAA) |
| **Rasterization Engine** | Pure Java (Single-thread) | Character cell blit | Software CPU | SIMD / AVX2 + Multi-Threaded Tiling |
| **Resolution Density** | 1 character per pixel | 1 cell per pixel | Half-block emulation | Half-block (2 px/cell) + SSAA |
| **Z-Buffering / Depth** | None or coarse float[] | None | CPU 16-bit | 32-bit Native Z-Buffer |
| **GC Pressure** | Extreme (String per char) | High (Text cells) | Moderate | 0 Allocations / Frame (Zero-Copy) |
| **Target Frame Rate** | 10–20 FPS | 15–30 FPS | 20–35 FPS | 60+ FPS Constant |

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
