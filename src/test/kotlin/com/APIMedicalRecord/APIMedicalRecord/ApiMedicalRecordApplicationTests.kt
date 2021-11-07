package com.APIMedicalRecord.APIMedicalRecord

import com.APIMedicalRecord.APIMedicalRecord.models.ClinicalBackground
import com.APIMedicalRecord.APIMedicalRecord.repositories.MedicalRecordRepository
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalRequest
import com.APIMedicalRecord.APIMedicalRecord.transport.ClinicalType
import com.APIMedicalRecord.APIMedicalRecord.transport.clinicalRequestBackground
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ApiMedicalRecordApplicationTests{

	@Autowired
	lateinit var mockMvc: MockMvc
	@Autowired
	lateinit var medicalRecordRepository: MedicalRecordRepository
	@Test
	fun create() {
		val randomUUID = UUID.randomUUID()
		val clinicalRequestBackground = listOf<ClinicalRequest>(
			ClinicalRequest(type = ClinicalType.DISEASE, value = "Diabetes", created_at = "2021-03-03T09:55:00")
		)
		val clinicalrequest = clinicalRequestBackground.first()
		val content = ObjectMapper().writeValueAsString(clinicalRequestBackground(
			clinical_backgrounds = clinicalRequestBackground))
		mockMvc.perform(MockMvcRequestBuilders.post("/$randomUUID/clinical_backgrounds")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(content))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andDo(MockMvcResultHandlers.print())
		val clinicalBackground = ClinicalBackground(
			UUID.randomUUID(), randomUUID, clinicalrequest.type, value = clinicalrequest.value,
			clinicalrequest.created_at)
		medicalRecordRepository.save(clinicalBackground = clinicalBackground
		)
		Assertions.assertEquals(clinicalBackground, medicalRecordRepository.findByPersonId(randomUUID))
	}
	@Test
	fun get() {

	}
	@Test
	fun checkEnum() {
		val clinicalRequest = ClinicalRequest(
			ClinicalType.DISEASE, "Alzheimer", created_at = "2021-03-03T09:55:00"
		)
		Assertions.assertTrue(medicalRecordRepository.checkEnum(clinicalRequest))
	}
}