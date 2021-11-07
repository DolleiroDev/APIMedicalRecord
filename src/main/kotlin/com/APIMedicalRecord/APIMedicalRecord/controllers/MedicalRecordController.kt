package com.APIMedicalRecord.APIMedicalRecord.controllers

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class MedicalRecordController(private val medicalRecordRepository: MedicalRecordRepository) {

    @PostMapping("{person_id}/clinical_backgrounds")
    fun create(@PathVariable person_id: UUID, @RequestBody clinicalRequest: clinicalRequestBackground):
            ResponseEntity<Any> {
        val clinicalBackgroundRequest = clinicalRequest.clinical_backgrounds.first()
        val clinicalBackgroundResponse = mutableListOf<ClinicalBackground>()
        val clinicalBackground = ClinicalBackground(
            id = UUID.randomUUID(), person_id = person_id, type = clinicalBackgroundRequest.type,
            value = clinicalBackgroundRequest.value, created_at = clinicalBackgroundRequest.created_at
        )
        if (!medicalRecordRepository.checkEnum(clinicalRequest = clinicalBackgroundRequest)) {
            return ResponseEntity.badRequest().body(
                "The ${clinicalBackgroundRequest.type} doesn't contains ${clinicalBackgroundRequest.value}")
        } else {
            medicalRecordRepository.save(clinicalBackground)
            clinicalBackgroundResponse.add(clinicalBackground)
        }
        return ResponseEntity.ok().body(ClinicalBackgroundResponse(
            clinical_backgrounds = clinicalBackgroundResponse))
    }
    @GetMapping("{person_id}/clinical_backgrounds")
    fun get(@PathVariable person_id: UUID): ResponseEntity<Any> {
        val clinicalBackground = medicalRecordRepository.findByPersonId(person_id = person_id)
        if (clinicalBackground != null) {
            return ResponseEntity.ok().body(clinicalBackground)
        }
        return ResponseEntity.badRequest().body("Clinical Background not found!")
    }
}