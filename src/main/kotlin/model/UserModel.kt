package org.example.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserModel (
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    var userId: UUID,

    @Column(name = "email", nullable = false, unique = true, length = 100)
    var email : String,

    @Column(name = "created_at", nullable = false)
    var createdAt : LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tasks : MutableList<TaskModel> = mutableListOf()
    ) {
    constructor(email: String) : this(
        userId = UUID.randomUUID(),
        email = email
    )
}