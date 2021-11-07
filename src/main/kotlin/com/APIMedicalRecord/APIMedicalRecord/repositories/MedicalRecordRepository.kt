package com.APIMedicalRecord.APIMedicalRecord.repositories

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalBackgroundResponse
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MedicalRecordRepository {
    private val clinicsBackground = mutableListOf<ClinicalBackground>()
    fun save(clinicalBackground: ClinicalBackground) {
        clinicsBackground.add(clinicalBackground)
    }

    fun findByPersonId(person_id: UUID): ClinicalBackground? {
        return clinicsBackground.find { it.person_id == person_id }
    }
    fun checkEnum(clinicalRequest: ClinicalRequest):
            Boolean{
        return clinicalRequest.type.values.contains(clinicalRequest.value)
    }
}