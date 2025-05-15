package hse.filestoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "files")
class FileMeta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val originalName: String,
    val sizeBytes: Long,
    var hashCode: String,
    val uploadTimestamp: LocalDateTime = LocalDateTime.now(),
    val storage_path: String,
) {
    constructor() : this(0, "", 0, "", LocalDateTime.now(), "")

    constructor(filename: String, path: String, file_size: Long) : this(
        originalName = filename,
        sizeBytes = file_size,
        hashCode = "",
        uploadTimestamp = LocalDateTime.now(),
        storage_path = path,
    )

    fun setHash(hash: String) {
        this.hashCode = hash
    }

}