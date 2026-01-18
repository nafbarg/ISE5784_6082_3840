# ISE5784_6082_3840 - Introduction to Software Engineering Mini-Project

## Project Overview

This is a mini-project for the Introduction to Software Engineering course. The project demonstrates software engineering principles through practical implementation of a raytracing renderer with graphical output.

## Project Collaborators

- **Naftali Bar-giora** (@nafbarg)
- **Itamar Pozaylov** (@itamarPoz)

## Repository Structure

```
.
├── src/                 # Main source code
├── lib/                 # External libraries
├── uniTests/            # Unit tests
├── images/              # Project images and assets
├── .gitignore           # Git ignore rules
└── ISE5784_6082_3840.iml # IntelliJ IDEA project file
```

### Directory Descriptions

- **src/**: Contains the main Java source code for the project
- **lib/**: External dependencies and libraries required by the project
- **uniTests/**: Unit test classes for testing project functionality
- **images/**: Graphics assets, screenshots, and rendered output images

## Requirements

- Java 8 or higher
- IntelliJ IDEA (optional, for IDE development)
- JUnit for running unit tests

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
java -cp bin:lib/* [MainClassName]
```

### Run Unit Tests

```bash
java -cp bin:lib/*:uniTests org.junit.runner.JUnitCore [TestClassName]
```

## Features

- **3D Raytracing Engine**: Implements core raytracing algorithms for rendering 3D scenes
- **Geometric Objects**: Support for rays, vectors, and geometric primitives
- **Image Generation**: Renders output to images for visualization
- **Unit Testing**: Comprehensive test coverage for all major components
- **Object-Oriented Design**: Clean architecture following software engineering best practices

## Project Milestones

### Exercise 1
- Initial project setup
- Basic class structure and gitignore configuration

### Exercise 2
- Library dependencies and initial implementation

### Exercise 3
- Additional features and enhancements

### Exercise 4
- ConstructRay implementation
- Ray class development

### Exercise 5-7
- Image rendering with visual elements
- Fish and starfish graphics
- Final output generation

## Building and Testing

### Compile

```bash
javac -cp lib/* -d bin src/**/*.java
```

### Test

```bash
java -cp bin:lib/*:uniTests org.junit.runner.JUnitCore [TestClassName]
```

## Project Output

Sample output images from the project can be found in the `images/` directory:
- Generated rendered output with visual elements
- Project demonstrations

## Code Quality

This project follows software engineering best practices including:
- Object-oriented design principles
- Unit testing and test-driven development
- Version control with Git
- Code documentation and comments
- Clean architecture patterns

## Release History

- **v7.1** (Latest) - July 9, 2024
- **v7.0** - Previous release
- See [Releases](https://github.com/nafbarg/ISE5784_6082_3840/releases) for full history

## Contributors

This project was developed by:
- [@nafbarg](https://github.com/nafbarg) - Naftali Bar-giora
- [@itamarPoz](https://github.com/itamarPoz) - Itamar Pozaylov

## Course Information

**Course**: Introduction to Software Engineering  
**Language**: Java  
**Project Type**: Mini-Project

## Support

For questions or issues related to this project, please open an issue on GitHub.

## License

This project is created for educational purposes as part of the Introduction to Software Engineering course.

---

**Last Updated**: January 18, 2026
