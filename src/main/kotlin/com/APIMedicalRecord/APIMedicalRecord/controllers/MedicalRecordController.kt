package com.APIMedicalRecord.APIMedicalRecord.controllers

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class MedicalRecordController {

    private val medicalRecordRepository = MedicalRecordRepository()

    @PostMapping("{person_id}/clinical_backgrounds")
    fun create(@PathVariable person_id: UUID, @RequestBody clinicalRequest: clinicalRequestBackground): ResponseEntity<Any> {
        if (!checkEnum(clinicalRequest =  clinicalRequest.clinical_backgrounds)) {
            return ResponseEntity.badRequest().body("The request doesn't contains specific value")
        }

        val clinicalBackgrounds = clinicalRequest.clinical_backgrounds.map {
            ClinicalBackground(id = UUID.randomUUID(), person_id = person_id, type = it.type,
                value = it.value, created_at = it.created_at
            )
        }

        val clinicalBackgroundsResponse = medicalRecordRepository.saveAll(clinicalBackgrounds)

        return ResponseEntity.ok().body(ClinicalBackgroundResponse(clinical_backgrounds = clinicalBackgroundsResponse))
    }

    @GetMapping("{person_id}/clinical_backgrounds")
    fun get(@PathVariable person_id: UUID): ResponseEntity<Any> =
        try {
            val foundClinicalBackground = medicalRecordRepository.findAllWithPersonId(person_id)
            ResponseEntity.ok().body(ClinicalBackgroundResponse(clinical_backgrounds = foundClinicalBackground))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Clinical Background not found!")
        }

    private fun checkEnum(clinicalRequest: List<ClinicalRequest>): Boolean {
        val clinicalRequestBackground = clinicalRequest.map {
            it.type.values.contains(it.value)
        }
        return !clinicalRequestBackground.contains(false)
    }
    }

