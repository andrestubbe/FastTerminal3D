# FastTerminal3D 0.1.0 [ALPHA-2026-06] — High-Performance 3D Terminal Renderer for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastTerminal3D/releases/tag/0.1.0)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe/FastTerminal3D)

**⚡ A cutting-edge 3D rasterization bridge for rendering complex 3D environments natively inside your True Color terminal.**

**FastTerminal3D** seamlessly bridges the 3D rendering pipeline of **[FastSoftware3D](https://github.com/andrestubbe/FastSoftware3D)** with the high-performance native ANSI rendering of **[FastTerminal](https://github.com/andrestubbe/FastTerminal)**. 

By applying advanced spatial SSAA downsampling, FastTerminal3D scales 3D graphics into Unicode half-block elements (`▀`) maintaining vivid 24-bit True Color while achieving maximum FPS via native C++ rasterization integration.

To run the terminal engine at maximum desktop-class framerates, it relies on the unified **FastJava** ecosystem:

* ⚡ **[FastSoftware3D](https://github.com/andrestubbe/FastSoftware3D)** — Provides the foundational math, clipping, shading, and multi-threaded triangle rasterization engine.
* 🚀 **[FastTerminal](https://github.com/andrestubbe/FastTerminal)** — Implements the native zero-copy ANSI viewport output and terminal scaling.

---

## 📚 Documentation & Guides

Learn more about the inner workings of FastTerminal3D and its architectural design:

* [**Philosophy & Architecture**](docs/PHILOSOPHY.md) - Why we built it and the design principles.
* [**Compilation & Build Guide**](docs/COMPILE.md) - Instructions for building the native bindings.
* [**API Reference**](docs/REFERENCE.md) - Technical API usage guide.
* [**Roadmap**](docs/ROADMAP.md) - Upcoming features and milestones.
* [**Changelog**](docs/CHANGELOG.md) - Version history.

---

## 🚀 Running the Demos

You can try the pre-configured Windows batch files right away:

- `run-demo.bat` — Simple rotating cube showcasing SSAA downsampling.
- `run-wolf-terminal-demo.bat` — The Wolfenstein 3D interactive terminal map viewer.

> **Note:** The terminal must support 24-bit True Color and UTF-8 encoding (e.g., Windows Terminal, Alacritty, or modern iTerm2).
