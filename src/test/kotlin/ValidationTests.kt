import jakarta.validation.Validation
import jakarta.validation.Validator
import org.example.dto.CreateTaskDto
import org.example.dto.CreateUserDto
import org.example.dto.TaskStatus
import org.example.dto.UpdateTaskStatusDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Тесты на валидацию DTO")
class ValidationTests {
    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `CreateUserDto with valid email should pass validation`() {
        val dto = CreateUserDto(email = "test@example.com")
        val violations = validator.validate(dto)

        assert(violations.isEmpty())
    }

    @Test
    fun `CreateUserDto with empty email should fail validation`() {
        val dto = CreateUserDto(email = "")
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "email" })
        assert(violations.any { it.message == "Email не может быть пустым" })
    }

    @Test
    fun `CreateUserDto with invalid email format should fail validation`() {
        val dto = CreateUserDto(email = "not-an-email")
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "email" })
        assert(violations.any { it.message == "Некорректный формат email" })
    }

    @Test
    fun `CreateUserDto with too long email should fail validation`() {
        val longEmail = "a".repeat(95) + "@example.com"  // 100+ символов
        val dto = CreateUserDto(email = longEmail)
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "email" })
        assert(violations.any { it.message == "Email не может быть длиннее 100 символов" })
    }

    @Test
    fun `CreateTaskDto with valid data should pass validation`() {
        val dto = CreateTaskDto(title = "Valid task", priority = 5)
        val violations = validator.validate(dto)

        assert(violations.isEmpty())
    }

    @Test
    fun `CreateTaskDto with short title should fail validation`() {
        val dto = CreateTaskDto(title = "ab", priority = 5)
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "title" })
        assert(violations.any { it.message == "Название задачи должно быть от 3 до 100 символов" })
    }

    @Test
    fun `CreateTaskDto with too long title should fail validation`() {
        val longTitle = "a".repeat(101)
        val dto = CreateTaskDto(title = longTitle, priority = 5)
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "title" })
    }

    @Test
    fun `CreateTaskDto with priority less than 1 should fail validation`() {
        val dto = CreateTaskDto(title = "Valid task", priority = 0)
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "priority" })
        assert(violations.any { it.message == "Приоритет должен быть не менее 1" })
    }

    @Test
    fun `CreateTaskDto with priority more than 10 should fail validation`() {
        val dto = CreateTaskDto(title = "Valid task", priority = 11)
        val violations = validator.validate(dto)

        assert(violations.any { it.propertyPath.toString() == "priority" })
        assert(violations.any { it.message == "Приоритет должен быть не более 10" })
    }

    @Test
    fun `UpdateTaskStatusDto with null status should fail validation`() {
        val dto = UpdateTaskStatusDto(status = TaskStatus.DONE)
        val violations = validator.validate(dto)

        assert(violations.isEmpty())
    }
}