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
    val fileName: String,
    var fileSize: Long,
    var hashCode: String,
    var numOfChars: Long,
    var numOfWords: Long,
    var numOfParagraphs: Long,
    var wordCloudPath: String,
    val analysisTimestamp: LocalDateTime = LocalDateTime.now(),

) {
    constructor() : this(0, 0, "", 0, "", -1, 1, -1, "")
    constructor(
        fileId: Long,
        fileName: String,
        fileSize: Long,
        hashCode: String,
    ) : this(
        fileId = fileId,
        fileName = fileName,
        fileSize = fileSize,
        hashCode = hashCode,
        numOfChars = -1,
        numOfWords = -1,
        numOfParagraphs = -1,
        wordCloudPath = ""
    )
}