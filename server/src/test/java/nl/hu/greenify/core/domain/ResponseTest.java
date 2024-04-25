package nl.hu.greenify.core.domain;

import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.PhaseName;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {

    private Factor factor;

    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "title", "red", "description", new ArrayList<>());
        this.factor = new Factor(1L, "title", 1, new ArrayList<>());
        category.addFactor(factor);
    }

    @ParameterizedTest
    @MethodSource("provideResponseScoreExamples")
    @DisplayName("Test response score calculation")
    void testResponseScoreCalculation(FacilitatingFactor facilitatingFactor, Priority priority, double expectedScore) {
        // Given:
        Subfactor subfactor = new Subfactor(1L, "title", 1, true);
        factor.addSubfactor(subfactor);
        Response response = new Response(subfactor);

        // When:
        response.setFacilitatingFactor(facilitatingFactor);
        response.setPriority(priority);
        double actualScore = response.getScore();

        // Then:
        assertEquals(expectedScore, actualScore);
    }

    static Stream<Arguments> provideResponseScoreExamples() {
        return Stream.of(
                // Structure: facilitatingFactor, priority, expectedScore
                // Top priority
                Arguments.of(FacilitatingFactor.PENDING, Priority.TOP_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.I_DONT_KNOW, Priority.TOP_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_AGREE, Priority.TOP_PRIORITY, 10.0),
                Arguments.of(FacilitatingFactor.AGREE, Priority.TOP_PRIORITY, 8.0),
                Arguments.of(FacilitatingFactor.NEUTRAL, Priority.TOP_PRIORITY, 6.0),
                Arguments.of(FacilitatingFactor.DISAGREE, Priority.TOP_PRIORITY, 4.0),
                Arguments.of(FacilitatingFactor.TOTALLY_DISAGREE, Priority.TOP_PRIORITY, 2.0),

                // Priority
                Arguments.of(FacilitatingFactor.PENDING, Priority.PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.I_DONT_KNOW, Priority.PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_AGREE, Priority.PRIORITY, 8.35),
                Arguments.of(FacilitatingFactor.AGREE, Priority.PRIORITY, 6.68),
                Arguments.of(FacilitatingFactor.NEUTRAL, Priority.PRIORITY, 5.01),
                Arguments.of(FacilitatingFactor.DISAGREE, Priority.PRIORITY, 3.34),
                Arguments.of(FacilitatingFactor.TOTALLY_DISAGREE, Priority.PRIORITY, 1.67),

                // Little priority
                Arguments.of(FacilitatingFactor.PENDING, Priority.LITTLE_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.I_DONT_KNOW, Priority.LITTLE_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_AGREE, Priority.LITTLE_PRIORITY, 6.65),
                Arguments.of(FacilitatingFactor.AGREE, Priority.LITTLE_PRIORITY, 5.32),
                Arguments.of(FacilitatingFactor.NEUTRAL, Priority.LITTLE_PRIORITY, 3.99),
                Arguments.of(FacilitatingFactor.DISAGREE, Priority.LITTLE_PRIORITY, 2.66),
                Arguments.of(FacilitatingFactor.TOTALLY_DISAGREE, Priority.LITTLE_PRIORITY, 1.33),

                // No priority
                Arguments.of(FacilitatingFactor.PENDING, Priority.NO_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.I_DONT_KNOW, Priority.NO_PRIORITY, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_AGREE, Priority.NO_PRIORITY, 5.0),
                Arguments.of(FacilitatingFactor.AGREE, Priority.NO_PRIORITY, 4.0),
                Arguments.of(FacilitatingFactor.NEUTRAL, Priority.NO_PRIORITY, 3.0),
                Arguments.of(FacilitatingFactor.DISAGREE, Priority.NO_PRIORITY, 2.0),
                Arguments.of(FacilitatingFactor.TOTALLY_DISAGREE, Priority.NO_PRIORITY, 1.0),

                // Pending
                Arguments.of(FacilitatingFactor.PENDING, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.I_DONT_KNOW, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_AGREE, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.AGREE, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.NEUTRAL, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.DISAGREE, Priority.PENDING, 0.0),
                Arguments.of(FacilitatingFactor.TOTALLY_DISAGREE, Priority.PENDING, 0.0)
        );
    }

    @Test
    @DisplayName("Supporting subfactor should not affect score")
    void testSubfactorWithSupportingFactor() {
        // Given:
        Subfactor subfactor = new Subfactor(1L, "title", 1, true);
        factor.addSubfactor(subfactor);
        Response response = new Response(subfactor);

        // When:
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        double actualScore = response.getScore();

        // Then:
        assertEquals(10.0, actualScore);
    }

    @Test
    @DisplayName("Non-supporting subfactor should invert facilitating factor score")
    void testSubfactorWithNonSupportingFactor() {
        // Given:
        Subfactor subfactor = new Subfactor(1L, "title", 1, false);
        factor.addSubfactor(subfactor);
        Response response = new Response(subfactor);

        // When:
        response.setFacilitatingFactor(FacilitatingFactor.TOTALLY_AGREE);
        response.setPriority(Priority.TOP_PRIORITY);
        double actualScore = response.getScore();

        // Then:
        assertEquals(2.0, actualScore);
    }
}
