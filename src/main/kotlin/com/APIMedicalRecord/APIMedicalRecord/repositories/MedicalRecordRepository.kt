package com.APIMedicalRecord.APIMedicalRecord.repositories

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MedicalRecordRepository()
{
    private val clinicsBackground = mutableListOf<ClinicalBackground>()

    fun save(clinicalBackground: ClinicalBackground) =
        clinicsBackground.add(clinicalBackground).let { clinicalBackground }

    fun findAllWithPersonId(person_id: UUID): List<ClinicalBackground> {
        val clinicalBackgroundResponse = mutableListOf<ClinicalBackground>()

        clinicsBackground.forEach {
            if (it.person_id == person_id) {
                clinicalBackgroundResponse.add(it)
            }
        }

        return clinicalBackgroundResponse
    }
    fun saveAll(clinicalBackground: List<ClinicalBackground>) =
        clinicsBackground.addAll(clinicalBackground).let {
            clinicsBackground
        }

}