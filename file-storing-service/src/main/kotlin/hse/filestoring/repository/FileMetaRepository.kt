package hse.filestoring.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import hse.filestoring.model.FileMeta

@Repository
interface FileMetaRepository : JpaRepository<FileMeta, Long> {
    // Spring Data JPA предоставляет множество методов из коробки (save, findById, findAll и т.д.)
}
