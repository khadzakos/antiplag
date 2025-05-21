package hse.gateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServiceUrls {

    @Value("\${STORAGE_SERVICE_URL:http://localhost:8081}")
    lateinit var fileServiceUrl: String

    @Value("\${ANALYSIS_SERVICE_URL:http://localhost:8082}")
    lateinit var analysisServiceUrl: String
}