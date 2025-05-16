package hse.fileanalysis.client.dto

data class FileMetaDto (
    val id: Long,
    val originalName: String,
    val sizeBytes: Long,
    val hashCode: String,
)
