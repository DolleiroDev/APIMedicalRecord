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

	private val actualList = mutableListOf<ClinicalBackground>()
	val medicalRecordRepository = MedicalRecordRepository(actualList)
	@Autowired
	lateinit var mockMvc: MockMvc
	@Test
	fun create() {
		val randomUUID = UUID.randomUUID()
		val clinicalRequestBackground = listOf(
			ClinicalRequest(type = ClinicalType.DISEASE, value = "Diabetes", created_at = "2021-03-03T09:55:00"),
			ClinicalRequest(type = ClinicalType.VACCINE, value = "BCG", created_at = "2021-03-03T09:55:00")
		)
		val content = ObjectMapper().writeValueAsString(clinicalRequestBackground(
			clinical_backgrounds = clinicalRequestBackground))
		mockMvc.perform(MockMvcRequestBuilders.post("/$randomUUID/clinical_backgrounds")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(content))
			.andDo(MockMvcResultHandlers.print())
	}
	@Test
	fun get() {
		val randomUUID = UUID.randomUUID()
		medicalRecordRepository.save(
			ClinicalBackground(UUID.randomUUID(), randomUUID, ClinicalType.VACCINE, "BCG",
				created_at = "2021-03-03T09:55:00")
		)
		mockMvc.perform(MockMvcRequestBuilders.get("/$randomUUID/clinical_backgrounds"))
			.andExpect(MockMvcResultMatchers.status().isOk)
	}

}
