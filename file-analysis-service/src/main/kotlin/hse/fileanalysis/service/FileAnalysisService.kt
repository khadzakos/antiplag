package hse.fileanalysis.service

import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.Service
import kotlin.io.path.Path

import hse.fileanalysis.model.AnalysisMeta
import hse.fileanalysis.repository.AnalysisMetaRepository
import hse.fileanalysis.client.FileStorageClient
import hse.fileanalysis.client.WordCloudClient
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import java.nio.file.Files

private val logger = LoggerFactory.getLogger(FileAnalysisService::class.java)

@Service
class FileAnalysisService (
    @Value("\${storage.path}") private val uploadDir: String,
    private val repository: AnalysisMetaRepository,
    private val fileClient: FileStorageClient,
    private val cloudClient: WordCloudClient,


) {
    fun analyze(fileId: ULong): String {
        val fileInfo = fileClient.loadInfo(fileId)
        logger.info("File info loaded: $fileInfo")
        val fileText = fileClient.loadText(fileId)
        logger.info("File text loaded: $fileText")

        val analysisMeta = AnalysisMeta(fileId = fileInfo.id,
                                        fileName = fileInfo.originalName,
                                        fileSize = fileInfo.sizeBytes,
                                        hashCode = fileInfo.hashCode)
        logger.info("File info: $fileInfo")
        val filePath = Path(uploadDir).resolve(fileInfo.originalName + ".png")
        analysisMeta.numOfChars = countChars(fileText)
        analysisMeta.numOfWords = countWords(fileText)
        analysisMeta.numOfParagraphs= countParagraphs(fileText)

        val pic = cloudClient.generateCloud(fileText)
        analysisMeta.wordCloudPath = filePath.toString()

        Files.createDirectories(filePath.parent)
        Files.write(filePath, pic)
        logger.info("Word cloud saved at: $filePath")
        repository.save(analysisMeta)
        logger.info("Analysis metadata saved: $analysisMeta")

        val analysisResult = """
            {
                "fileId": ${analysisMeta.fileId},
                "fileName": "${analysisMeta.fileName}",
                "fileSize": ${analysisMeta.fileSize},
                "hashCode": "${analysisMeta.hashCode}",
                "numOfChars": ${analysisMeta.numOfChars},
                "numOfWords": ${analysisMeta.numOfWords},
                "numOfParagraphs": ${analysisMeta.numOfParagraphs},
                "wordCloudPath": "${analysisMeta.wordCloudPath}"
            }
        """.trimIndent()
        return analysisResult
    }

    fun download(fileName: String): Resource {
        val path = Path(uploadDir).resolve("$fileName.png")

        return UrlResource(path.toUri())
            .takeIf { it.exists() }
            ?: throw RuntimeException("File not found")
    }

    private fun countChars(text: String): Long {
        return text.length.toLong()
    }

    private fun countWords(text: String): Long {
        return text.split("\\s+".toRegex()).size.toLong()
    }

    private fun countParagraphs(text: String): Long {
        return text.split("\n\n").size.toLong()
    }

}