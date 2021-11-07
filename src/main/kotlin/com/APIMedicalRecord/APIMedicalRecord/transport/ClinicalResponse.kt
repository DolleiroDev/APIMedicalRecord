package com.APIMedicalRecord.APIMedicalRecord.transport

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import java.time.LocalDateTime
import java.util.*

data class ClinicalBackgroundResponse(
    val clinical_backgrounds: List<ClinicalBackground>
)

