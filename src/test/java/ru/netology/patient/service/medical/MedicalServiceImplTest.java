package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class MedicalServiceImplTest {

    @Mock
    private PatientInfoRepository patientInfoRepository;

    @Mock
    private SendAlertService alertService;

    @InjectMocks
    private MedicalServiceImpl medicalService;

    @Test
    void testCheckBloodPressure_checkMessage() {

//готовим PatientInfoRepository
        String patientId = "123e4567-e89b-12d3-a456-426655440009";
        BloodPressure bloodPressure = new BloodPressure(120, 80);
        PatientInfo patientInfo = new PatientInfo("123e4567-e89b-12d3-a456-426655440009", "Иван", "Петров",
                LocalDate.of(1980, 11, 26), new HealthInfo(new BigDecimal("36.65"), bloodPressure));
//поведение для getById
        Mockito.doReturn(patientInfo).when(patientInfoRepository).getById(patientId);

//готовим SendAlertService
        String message = String.format("Warning, patient with id: %s, need help", "123e4567-e89b-12d3-a456-426655440009");
//поведение для send
        Mockito.doNothing().when(alertService).send(message);

//actual
        BloodPressure bloodPressure2 = new BloodPressure(160, 80);
        medicalService.checkBloodPressure(patientId, bloodPressure2);

//assert
        Assertions.assertDoesNotThrow(() -> patientInfoRepository.getById(patientId));
        Mockito.verify(patientInfoRepository, Mockito.times(2)).getById(patientId);

        Assertions.assertDoesNotThrow(() -> alertService.send(message));
        Mockito.verify(alertService, Mockito.times(2)).send(message);

    }

    @Test
    void testCheckTemperature_checkMessage() {

//готовим PatientInfoRepository
        String patientId = "123e4567-e89b-12d3-a456-426655440009";
        BigDecimal temp = new BigDecimal("36.6");
        BloodPressure bloodPressure = new BloodPressure(200, 80);
        PatientInfo patientInfo = new PatientInfo("123e4567-e89b-12d3-a456-426655440009", "Иван", "Петров",
                LocalDate.of(1980, 11, 26), new HealthInfo(temp, bloodPressure));
//поведение для getById
        Mockito.doReturn(patientInfo).when(patientInfoRepository).getById(patientId);

//готовим SendAlertService
        String message = String.format("Warning, patient with id: %s, need help", "123e4567-e89b-12d3-a456-426655440009");
//поведение для send
        Mockito.doNothing().when(alertService).send(message);


//actual
        BigDecimal temp2 = new BigDecimal("39.6");
        medicalService.checkTemperature(patientId, temp2);

//assert
        Assertions.assertDoesNotThrow(() -> patientInfoRepository.getById(patientId));
        Mockito.verify(patientInfoRepository, Mockito.times(2)).getById(patientId);

        Assertions.assertDoesNotThrow(() -> alertService.send(message));
        Mockito.verify(alertService, Mockito.times(2)).send(message);

    }

}
