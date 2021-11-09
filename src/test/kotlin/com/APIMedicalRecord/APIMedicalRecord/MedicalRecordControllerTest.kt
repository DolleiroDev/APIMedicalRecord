package com.APIMedicalRecord.APIMedicalRecord

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalType
import com.APIMedicalRecord.APIMedicalRecord.transport.clinicalRequestBackground
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
class MedicalRecordControllerTest {
    private val actualList = mutableListOf<ClinicalBackground>()
    val medicalRecordRepository = MedicalRecordRepository(actualList)
    @Autowired
    lateinit var mockMvc: MockMvc
    @Test
    fun `should create a clinical background`() {
        val personId = UUID.randomUUID()
        val clinicalBackgroundsRequest = listOf(
            ClinicalRequest(ClinicalType.DISEASE, "Alzheimer", "2021-03-03T09:55:00 ")
        )
        val json = ObjectMapper().writeValueAsString(clinicalRequestBackground(
            clinical_backgrounds = clinicalBackgroundsRequest))
        mockMvc.post("/$personId/clinical_backgrounds") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isOk() }

            }
    }
    @Test
    fun `should return bad request with wrong url`() {
        val clinicalBackgroundsRequest = listOf(
            ClinicalRequest(ClinicalType.DISEASE, "Alzheimer", "2021-03-03T09:55:00 ")
        )
        val json = ObjectMapper().writeValueAsString(clinicalRequestBackground(
            clinical_backgrounds = clinicalBackgroundsRequest))
        mockMvc.post("/URL ERRADISSIMA") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isNotFound() }
            }
    }
    @Test
    fun `should return all clinical backgrounds with a specific id`() {
        val personId = UUID.randomUUID()
        medicalRecordRepository.save(
            ClinicalBackground(
            id = UUID.randomUUID(), person_id = personId, ClinicalType.DISEASE, "Alzheimer",
                created_at = "2021-03-03T09:55:00 "
        ))
        println(medicalRecordRepository.findAllWithPersonId(personId))
        mockMvc.get("/$personId/clinical_backgrounds")
            .andDo {
                print()
            }
    }

}