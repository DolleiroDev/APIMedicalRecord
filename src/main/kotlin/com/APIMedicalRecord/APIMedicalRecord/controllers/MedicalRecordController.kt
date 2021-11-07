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
        val clinicalBackgroundResponse = mutableListOf<ClinicalBackground>()
        clinicalRequest.clinical_backgrounds.forEach {
            val clinicalBackground = ClinicalBackground(
                id = UUID.randomUUID(), person_id = person_id, type = it.type,
                value = it.value, created_at = it.created_at
            )
            if (!medicalRecordRepository.checkEnum(clinicalRequest = it)) {
                return ResponseEntity.badRequest().body(
                    "The ${it.type} doesn't contains ${it.value}")
            } else {
                medicalRecordRepository.save(clinicalBackground)
                clinicalBackgroundResponse.add(clinicalBackground)
            }
        }
        return ResponseEntity.ok().body(ClinicalBackgroundResponse(
            clinical_backgrounds = clinicalBackgroundResponse))
    }
    @GetMapping("{person_id}/clinical_backgrounds")
    fun get(@PathVariable person_id: UUID): ResponseEntity<Any> {
        return try {
            val clinicalBackgroundResponse = medicalRecordRepository.findAllWithPersonId(person_id)
            ResponseEntity.ok().body(ClinicalBackgroundResponse(clinical_backgrounds = clinicalBackgroundResponse))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Clinical Background not found!")
        }
    }
}