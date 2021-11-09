package com.APIMedicalRecord.APIMedicalRecord.repositories

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MedicalRecordRepository(private val clinicsBackground: MutableList<ClinicalBackground>)
{
    fun save(clinicalBackground: ClinicalBackground) {
        clinicsBackground.add(clinicalBackground)
    }

    fun checkEnum(clinicalRequest: ClinicalRequest): Boolean{
        return clinicalRequest.type.values.contains(clinicalRequest.value)
    }

    fun findAllWithPersonId(person_id: UUID): List<ClinicalBackground> {
        val clinicalBackgroundResponse = mutableListOf<ClinicalBackground>()

        clinicsBackground.forEach {
            if (it.person_id == person_id) {
                clinicalBackgroundResponse.add(it)
            }
        }

        return clinicalBackgroundResponse
    }
}