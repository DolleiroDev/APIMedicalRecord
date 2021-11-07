package com.APIMedicalRecord.APIMedicalRecord.transport

import javax.persistence.EnumType
import javax.persistence.Enumerated

data class clinicalRequestBackground(
    val clinical_backgrounds: List<ClinicalRequest>
)
data class ClinicalRequest(
    @Enumerated(EnumType.STRING)
    val type: ClinicalType,
    val value: String,
    val created_at: String
)

enum class ClinicalType(val values: List<String>) {
    DISEASE(listOf("Diabetes", "Alzheimer", "Hypertension", "Asthma", "Parkinson")),
    SURGICAL(listOf("Mammoplasty", "Liposuction", "Blepharoplasty", "Rhinoplasty", "Abdominoplasty")),
    VACCINE(listOf("BCG", "HPV", "Hepatitis A", "Hepatitis B", "Influenza")),
    MEDICINE(listOf("Aradois", "Paroxetine", "Addera D3", "Xarelto", "Glifage XR")),
}

