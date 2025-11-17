# Cajero Service (Microservicio)

Microservicio Spring Boot para el rol "Cajero" — operaciones soportadas:
- Crear cliente
- Crear dependiente (marca rol)
- Depositar en cuenta (validando DUI -> primera cuenta)
- Retirar de cuenta (validando saldo y DUI)
- Crear préstamo (reglas de salario, interés y cuota <= 30% salario)

## Ejecutar
1. Crea la base de datos `BancoAgricultura` usando el schema proporcionado.
2. Ajusta `src/main/resources/application.properties` con tus credenciales.
3. Ejecuta con Maven: `mvn spring-boot:run` (puerto 8081).
