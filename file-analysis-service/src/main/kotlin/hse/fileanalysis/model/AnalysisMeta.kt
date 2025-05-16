package hse.fileanalysis.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "analysis_results")
class AnalysisMeta (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val fileId: Long,
) {
    constructor() : this(0, 0)
}