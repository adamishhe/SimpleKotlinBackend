package org.example.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.example.dto.TaskStatus
import org.example.model.UserModel
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "tasks")
class TaskModel(

    @Id
    @Column(name = "task_id", nullable = false, unique = true)
    var taskId: UUID,

    @Column(name = "title", nullable = false, length = 300)
    var title: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: TaskStatus = TaskStatus.NEW,

    @Column(name = "priority", nullable = false)
    var priority: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserModel,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor(title: String, priority: Int, user: UserModel) : this(
        taskId = UUID.randomUUID(),
        title = title,
        priority = priority,
        user = user
    )
}
