package hse.filestoring.service

import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths
import org.slf4j.LoggerFactory
import java.util.*

import hse.filestoring.model.FileMeta
import hse.filestoring.repository.FileMetaRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import java.nio.file.Files
import kotlin.io.path.Path

private val logger = LoggerFactory.getLogger(FileStorageService::class.java)

@Service
class FileStorageService(
    @Value("\${storage.path}") private val uploadDir: String,
    private val repository: FileMetaRepository,
    private val hashService: HashService,
) {

    init {
        Files.createDirectories(Paths.get(uploadDir))
    }

    fun store(file: MultipartFile): ULong {
        val filename = file.originalFilename
        val fileNameWithoutExtension = filename?.substringBeforeLast(".") ?: throw IllegalArgumentException("Filename is null")
        var path = Paths.get(uploadDir).resolve(fileNameWithoutExtension + ".txt")
        if (Files.exists(path)) {
            return ULong.MAX_VALUE
        }
        file.transferTo(path)
        logger.info("File stored at: $path")

        val meta = FileMeta(filename = fileNameWithoutExtension, path = path.toString(), file_size = file.size)
        if (repository.existsById(meta.id)) {
            logger.warn("File with ID ${meta.id} already exists")
            return ULong.MAX_VALUE
        }
        meta.setHash(hashService.hash(file.bytes))
        repository.save(meta)

        logger.info("File metadata saved: $meta")
        val id = meta.id.toULong()
        return id
    }

    fun load(file_id: ULong): Resource {
        val fileMeta = repository.findById(file_id.toLong())
            .orElseThrow { RuntimeException("File not found") }

        val path = Path(fileMeta.storage_path)
        logger.info("Loading file from path: $path")
        return UrlResource(path.toUri())
    }

    fun fileInfo(file_id: ULong): String {
        val fileMeta = repository.findById(file_id.toLong())
            .orElseThrow { RuntimeException("File not found") }

        val responseMeta = """
            {
                "id": ${fileMeta.id},
                "originalName": "${fileMeta.originalName}",
                "sizeBytes": ${fileMeta.sizeBytes},
                "hashCode": "${fileMeta.hashCode}"
            }
        """.trimIndent()
        return responseMeta
    }
}