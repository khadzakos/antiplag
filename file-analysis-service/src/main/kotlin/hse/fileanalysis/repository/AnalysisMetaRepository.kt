package hse.fileanalysis.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import hse.fileanalysis.model.AnalysisMeta

@Repository
interface AnalysisMetaRepository : JpaRepository<AnalysisMeta, Long> {}