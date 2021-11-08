package com.APIMedicalRecord.APIMedicalRecord.controllers

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class MedicalRecordController {
    private val clinicalBackgroundResponse = mutableListOf<ClinicalBackground>()
    val medicalRecordRepository = MedicalRecordRepository(clinicalBackgroundResponse)
    @PostMapping("{person_id}/clinical_backgrounds")
    fun create(@PathVariable person_id: UUID, @RequestBody clinicalRequest: clinicalRequestBackground): ResponseEntity<Any> {
        clinicalRequest.clinical_backgrounds.forEach {
            val clinicalBackground = ClinicalBackground(id = UUID.randomUUID(), person_id = person_id, type = it.type,
                value = it.value, created_at = it.created_at
            )
            if (!medicalRecordRepository.checkEnum(clinicalRequest = it)) {
                return ResponseEntity.badRequest().body(
                    "The ${it.type} doesn't contains ${it.value}")
            } else {
                medicalRecordRepository.save(clinicalBackground)
            }
        }
        return ResponseEntity.ok().body(ClinicalBackgroundResponse(
            clinical_backgrounds = clinicalBackgroundResponse))
    }
    @GetMapping("{person_id}/clinical_backgrounds")
    fun get(@PathVariable person_id: UUID): ResponseEntity<Any> {
        return try {
            val foundClinicalBackground = medicalRecordRepository.findAllWithPersonId(person_id)
            ResponseEntity.ok().body(ClinicalBackgroundResponse(clinical_backgrounds = foundClinicalBackground))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Clinical Background not found!")
        }
    }
}