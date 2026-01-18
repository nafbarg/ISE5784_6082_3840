# Java Raytracing Engine

A pure Java implementation of a Raytracing renderer, built from scratch without external graphics engines. This project demonstrates core computer graphics concepts alongside rigorous software engineering practices including Test-Driven Development (TDD) and Clean Architecture principles.

*A capstone project from the Introduction to Software Engineering course*

## Sample Output

![Rendered Scene](images/myImage2.png)

## Key Features

* **Custom 3D Engine** – Implements core raytracing algorithms including Ray-Surface intersection detection, reflection, and refraction calculations
* **Lighting Model** – Full implementation of Phong shading model with ambient, diffuse, and specular lighting components
* **Mathematical Foundation** – Custom linear algebra library for Vector and Matrix operations built from the ground up
* **Clean Architecture** – Strictly adheres to SOLID principles and object-oriented best practices throughout the codebase
* **Comprehensive Testing** – Extensive unit test coverage for geometric calculations, mathematical operations, and rendering logic
* **Production Quality** – Well-documented code with clear abstractions and maintainable design patterns

## Project Collaborators

- **Naftali Bar-giora** (@nafbarg)
- **Itamar Pozaylov** (@itamarPoz)

## Repository Structure

```
.
├── src/                        # Main source code
│   ├── geometries/             # 3D geometric primitives
│   ├── math/                   # Vector and matrix operations
│   ├── rendering/              # Raytracing engine core
│   └── lighting/               # Lighting and shading models
├── lib/                        # External dependencies
├── uniTests/                   # Comprehensive unit tests
├── images/                     # Rendered output samples
├── .gitignore                  # Git configuration
└── ISE5784_6082_3840.iml      # IntelliJ IDEA project file
```

### Directory Descriptions

- **src/**: Contains all Java source code organized by functional domain
- **lib/**: External libraries and dependencies required for compilation and testing
- **uniTests/**: Comprehensive unit test suite using JUnit framework
- **images/**: Sample rendered scenes demonstrating engine capabilities

## Technical Requirements

- **Java 8 or higher**
- **IntelliJ IDEA** (recommended, but any IDE supporting Java works)
- **JUnit 4** for running unit tests
- **Maven or Gradle** (optional, for dependency management)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/nafbarg/ISE5784_6082_3840.git
cd ISE5784_6082_3840
```

### Compile the Project

```bash
javac -cp lib/* -d bin src/**/*.java
```

### Run the Application

```bash
java -cp bin:lib/* ImageWriter
```

### Execute the Test Suite

```bash
java -cp bin:lib/*:uniTests org.junit.runner.JUnitCore [TestClassName]
```

## Development Phases

### Phase 1: Mathematical Infrastructure
Implemented foundational linear algebra classes:
- Vector3D and Point3D classes
- Mathematical operations (dot product, cross product, scalar multiplication)
- Ray representation and direction vectors

### Phase 2: Geometric Primitives
Added support for 3D geometric objects:
- Plane, Sphere, Triangle geometries
- Intersection detection algorithms
- Surface normal calculations

### Phase 3: Ray-Surface Intersection
Core raytracing engine development:
- Ray casting algorithm
- Intersection point calculations
- Geometric precision handling

### Phase 4: Lighting and Shading
Implemented complete lighting model:
- Point light sources
- Phong reflection model
- Ambient, diffuse, and specular components
- Shadow rendering

### Phase 5-7: Refinement and Optimization
- Image generation and rendering
- Visual element composition (geometric shapes, multiple objects)
- Performance optimization and bug fixes
- Final output generation with enhanced visuals

## Code Quality Standards

This project adheres to professional software engineering practices:

✓ **Object-Oriented Design** – Clear separation of concerns with well-defined classes and interfaces  
✓ **SOLID Principles** – Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion  
✓ **Test-Driven Development** – Comprehensive unit tests written alongside feature implementation  
✓ **Version Control** – Clean git history with meaningful commit messages  
✓ **Code Documentation** – Detailed comments and method documentation  
✓ **Error Handling** – Robust exception handling for edge cases

## Known Issues & Future Improvements

* **Performance Optimization** – Current implementation uses single-threaded rendering. Future versions will implement multi-threading and parallel ray casting for significantly faster rendering of high-resolution images.

* **Acceleration Structures** – The lighting calculation can be further optimized using spatial data structures such as Bounding Volume Hierarchies (BVH) or KD-Trees to reduce ray-primitive intersection tests.

* **Advanced Features** – Future enhancements may include:
  - Anti-aliasing for smoother edges
  - Texture mapping and bump mapping
  - Path tracing for global illumination
  - Support for additional primitive types (cylinders, cones, meshes)

* **Memory Management** – Large scenes could benefit from memory pool allocation and object recycling patterns.

## Release History

- **v7.1** (Latest) - July 9, 2024 – Final optimizations and visual refinements
- **v7.0** – Complete feature parity with all core raytracing functionality
- See [Releases](https://github.com/nafbarg/ISE5784_6082_3840/releases) for detailed version history and changes

## Project Statistics

- **Language**: Java 100%
- **Total Commits**: 42
- **Contributors**: 2
- **Test Coverage**: Comprehensive unit test suite
- **Code Style**: Clean Code principles with consistent formatting

## Building and Testing

### Quick Compilation

```bash
javac -cp lib/* -d bin src/**/*.java
```

### Running Unit Tests

```bash
# Run all tests
java -cp bin:lib/*:uniTests org.junit.runner.JUnitCore src.test.AllTests

# Run specific test class
java -cp bin:lib/*:uniTests org.junit.runner.JUnitCore src.test.GeometryTest
```

## Architecture Highlights

**Raytracing Pipeline:**
1. Ray Generation – Create rays from camera through image plane pixels
2. Intersection Testing – Find closest intersection with scene geometry
3. Lighting Calculation – Compute Phong illumination at intersection point
4. Color Determination – Blend colors based on material properties
5. Output Rendering – Write pixel values to output image

**Key Design Patterns:**
- Factory Pattern for geometric object creation
- Strategy Pattern for different lighting models
- Observer Pattern for rendering pipeline events

## Development Tools

- **IDE**: IntelliJ IDEA Community Edition
- **Build Tool**: Maven/Gradle (optional)
- **Version Control**: Git with GitHub
- **Testing Framework**: JUnit 4
- **Code Analysis**: Built-in IDE inspections

## Professional Development Practices

✓ Peer code reviews within team  
✓ Automated unit testing for regression prevention  
✓ Meaningful commit messages documenting changes  
✓ Branch-based development with main branch protection  
✓ Documentation-first approach for complex algorithms

## Contact & Support

For technical inquiries, questions about the implementation, or bug reports:

- **Naftali Bar-giora** – [@nafbarg](https://github.com/nafbarg)
- **Itamar Pozaylov** – [@itamarPoz](https://github.com/itamarPoz)

Please open an issue on GitHub for bugs or feature requests.

## Course Information

**Course:** Introduction to Software Engineering  
**Language:** Java  
**Project Type:** Capstone Mini-Project  
**Assessment:** Comprehensive, covering design, implementation, testing, and documentation

## Acknowledgments

This project was developed as part of the Introduction to Software Engineering curriculum and demonstrates the application of professional software development methodologies to computer graphics engineering.

## License

This project is created for educational and portfolio purposes. Feel free to use it as reference material or learning resource.

---

**Last Updated:** January 18, 2026  
**Status:** Active Development & Maintenance
