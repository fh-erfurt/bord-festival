package de.bord.festival.database;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TimeDisorderException;
import de.bord.festival.models.LineUp;
import de.bord.festival.repository.LineUpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LineUpJPATest {
    @Autowired
    LineUpRepository lineUpRepository;
    HelpClasses helper;
    LineUp lineUp;
    @BeforeEach
    void initialize() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        this.helper = new HelpClasses();
        this.lineUp = helper.getLineUp(LocalDate.of(2020, 10,10), LocalDate.of(2020, 10,11));
    }
    @Test
    void should_save_lineUp(){
        LineUp lineUpDatabase= lineUpRepository.save(lineUp);

        assertEquals(LocalDate.of(2020, 10,10), lineUpDatabase.getStartDate());
        assertEquals(LocalDate.of(2020, 10,11), lineUpDatabase.getEndDate());
        assertEquals(0, lineUpDatabase.getNumberOfBands());


    }

}
