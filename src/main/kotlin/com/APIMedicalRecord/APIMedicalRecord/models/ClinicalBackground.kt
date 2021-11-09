package com.APIMedicalRecord.APIMedicalRecord.models

import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalType
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class ClinicalBackground(
    val id: UUID ,
    val person_id: UUID,
    @Enumerated(EnumType.STRING)
    val type: ClinicalType,
    val value: String,
    val created_at: String
)
