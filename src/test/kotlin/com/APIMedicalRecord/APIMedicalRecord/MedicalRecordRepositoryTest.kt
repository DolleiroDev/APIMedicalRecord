package com.APIMedicalRecord.APIMedicalRecord

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class MedicalRecordRepositoryTest {
    private val actualList = mutableListOf<ClinicalBackground>()
    val medicalRecordRepository = MedicalRecordRepository(actualList)
    @Test
    fun `when save should add on list`() {
        val clinicalBackground = ClinicalBackground(
            UUID.randomUUID(), UUID.randomUUID(), ClinicalType.DISEASE, "Alzheimer",
            created_at = "2021-03-03T09:55:00"
        )
        val testList = mutableListOf<ClinicalBackground>()

        testList.add(clinicalBackground)
        medicalRecordRepository.save(clinicalBackground)
        Assertions.assertEquals(testList, actualList)
    }
    @Test
    fun `should check if value belongs to type`() {
        val clinicalRequest = ClinicalRequest(ClinicalType.DISEASE, "Alzheimer", "2021-03-03T09:55:00")
        val checkedEnum = medicalRecordRepository.checkEnum(clinicalRequest)
        Assertions.assertTrue(checkedEnum)
    }
    @Test
    fun `should check if value doesn't belongs to type`() {
        val clinicalRequest = ClinicalRequest(ClinicalType.DISEASE, "Batata", "2021-03-03T09:55:00")
        val checkedEnum = medicalRecordRepository.checkEnum(clinicalRequest)
        Assertions.assertFalse(checkedEnum)
    }
    @Test
    fun `should return all backgrounds with a specific id`() {
        val randomID = UUID.randomUUID()
        val clinicalBackgroundTest = ClinicalBackground(
            UUID.randomUUID(), randomID, ClinicalType.DISEASE, "Alzheimer",
            created_at = "2021-03-03T09:55:00"
        )
        val testList = mutableListOf<ClinicalBackground>()
        actualList.add(clinicalBackgroundTest)
        testList.add(clinicalBackgroundTest)
        val clinicalBackgrounds = medicalRecordRepository.findAllWithPersonId(randomID)
        Assertions.assertEquals(testList, clinicalBackgrounds)
    }
    @Test
    fun `when getAll with wrong id should return empty`() {
        val randomID = UUID.randomUUID()
        val clinicalBackgroundTest = ClinicalBackground(
            UUID.randomUUID(), randomID, ClinicalType.DISEASE, "Alzheimer",
            created_at = "2021-03-03T09:55:00"
        )
        val emptyList = mutableListOf<ClinicalBackground>()
        actualList.add(clinicalBackgroundTest)
        val clinicalBackgrounds = medicalRecordRepository.findAllWithPersonId(UUID.randomUUID())
        Assertions.assertEquals(clinicalBackgrounds, emptyList)
    }
}