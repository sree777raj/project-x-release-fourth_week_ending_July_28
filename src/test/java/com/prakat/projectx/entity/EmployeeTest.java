package com.prakat.projectx.entity;
import com.prakat.projectx.enums.EmployeesStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class EmployeeTest {private Employee employee;
    @Mock
    private Band band;
    @Mock
    private List<Skill> Skills;
    @Mock
    private WorkLocation workLocation;
    @Mock
    private Audit audit;


    @BeforeEach
    public void setup() {
        employee = new Employee();
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        employee.setId(id);
        Assertions.assertEquals(id, employee.getId());
    }

    @Test
    public void testGetSetFirstName() {
        String firstName = "Binod";
        employee.setFirstName(firstName);
        Assertions.assertEquals(firstName, employee.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        String lastName = "kumar";
        employee.setLastName(lastName);
        Assertions.assertEquals(lastName, employee.getLastName());
    }

    @Test
    public void testGetSetEmpId(){
        String empId = "Emp001";
        employee.setEmpId(empId);
        Assertions.assertEquals(empId, employee.getEmpId());
    }

    @Test
    public void testStatusEnumValue() {
        EmployeesStatus expectedStatus = EmployeesStatus.ACTIVE;
        employee.setStatus(expectedStatus);
        EmployeesStatus actualStatus = employee.getStatus();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testGetSetGender() {
        // Set a gender
        Gender gender = new Gender();
        employee.setGender(gender);

        // Verify if the gender is properly set
        Assertions.assertEquals(gender, employee.getGender());
    }

    @Test
    public void testGetSetEmail() {
        String email = "binodkumar@prakat.in";
        employee.setEmail(email);
        Assertions.assertEquals(email, employee.getEmail());
    }

    @Test
    public void testGetSetPhoneNumber() {
        String phoneNumber = "9123161653";
        employee.setPhoneNumber(phoneNumber);
        Assertions.assertEquals(phoneNumber, employee.getPhoneNumber());
    }
    @Test
    public void testGetSetBandId() {
        employee.setBandId(band);
        Assertions.assertEquals(band, employee.getBandId());
    }

    @Test
    public void testGetSetSkillId() {
        employee.setSkillId(Skills);
        Assertions.assertEquals(Skills, employee.getSkillId());
    }

    @Test
    public void testGetSetLocationId() {
        employee.setLocationId(workLocation);
        Assertions.assertEquals(workLocation, employee.getLocationId());
    }

    @Test
    void testSetAudit() {
        employee.setAudit(audit);
        Assertions.assertEquals(audit, employee.getAudit());
    }

    @Test
    void testAuditIdColumnMapping() {
        Assertions.assertNull(employee.getAudit());
        employee.setAudit(audit);
        Assertions.assertEquals(audit, employee.getAudit());

    }

    @Test
    void testCtc(){
        employee.setCtc("100000");
        Assertions.assertEquals("100000",employee.getCtc());
    }

    @Test
    public void testGetEmploymentType() {
        Employee employee = new Employee();
        EmploymentType expectedEmploymentType = new EmploymentType();

        employee.setEmploymentType(expectedEmploymentType);
        EmploymentType actualEmploymentType = employee.getEmploymentType();

        Assertions.assertEquals(expectedEmploymentType, actualEmploymentType);
    }

    @Test
    public void testGetManager() {
        Employee employee = new Employee();
        Employee expectedManager = new Employee();

        employee.setManager(expectedManager);
        Employee actualManager = employee.getManager();

        Assertions.assertEquals(expectedManager, actualManager);
    }


    @Test
    public void testGetDeleted() {
        Boolean expectedStatus = true;
        Employee employee = new Employee();
        employee.setDeleted(expectedStatus);
        Boolean actualStatus = employee.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }




    @Test
    public void testDobColumnMappingAndFormat() throws NoSuchFieldException {
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
        employee.setDob(dateOfBirth);
        String expectedDateFormat = "1990-05-15";
        String actualDateFormat = employee.getDob().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(expectedDateFormat, actualDateFormat);
    }

    @Test
    public void testJoiningDateColumnMappingAndFormat() {
        LocalDate joiningDate = LocalDate.of(2022, 7, 1);
        employee.setJoiningDate(joiningDate);
        String expectedDateFormat = "2022-07-01";
        String actualDateFormat = employee.getJoiningDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(expectedDateFormat, actualDateFormat);
    }
    @Test
    public void testSubordinatesOneToManyRelationship() {
        // Create a manager
        Employee manager = new Employee();
        manager.setSubordinates(new ArrayList<>());

        // Create subordinates
        Employee subordinate1 = new Employee();
        Employee subordinate2 = new Employee();

        // Add subordinates to the manager
        manager.getSubordinates().add(subordinate1);
        manager.getSubordinates().add(subordinate2);

        // Verify that the subordinates are added to the manager
        Assertions.assertTrue(manager.getSubordinates().contains(subordinate1));
        Assertions.assertTrue(manager.getSubordinates().contains(subordinate2));


    }

    @Test
    void testGetTimesheets() {
        // Arrange
        List<Timesheet> expectedTimesheets = new ArrayList<>();
        Timesheet timesheet1 = new Timesheet();
        Timesheet timesheet2 = new Timesheet();
        expectedTimesheets.add(timesheet1);
        expectedTimesheets.add(timesheet2);

        // Set the timesheets for the employee instance
        employee.setTimesheets(expectedTimesheets);

        // Act
        List<Timesheet> actualTimesheets = employee.getTimesheets();

        // Assert
        Assertions.assertEquals(expectedTimesheets, actualTimesheets);
    }

}